package com.neo.modules.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neo.common.config.Global;
import com.neo.common.config.JsonData;
import com.neo.common.utils.WebValueUtil;
import com.neo.modules.entity.SysRole;
import com.neo.modules.entity.SysUser;
import com.neo.modules.param.UserParam;
import com.neo.modules.service.SysRoleService;
import com.neo.modules.service.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/list.page")
    @RequiresPermissions("sysUser:view")
    public ModelAndView list(HttpServletRequest request,UserParam param) {
        String pageIndex = WebValueUtil.getWebValue(request, "pageIndex");
        String username = WebValueUtil.getWebValue(request, "username");
        int pageNo = 0;
        if (StringUtils.isNotBlank(pageIndex)) {
            pageNo = Integer.parseInt(pageIndex);
        }
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(pageNo, Global.DEFAULT_PAGESIZE, sort);
        Page<SysUser> page = sysUserService.findPage(pageable, param);
        ModelAndView mv = new ModelAndView("/sys/user/list");
        mv.addObject("param", JSONObject.toJSONString(param));
        mv.addObject("page",page);
        return mv;
    }

    @RequestMapping("/save.json")
    @RequiresPermissions("sysUser:add")
    public JsonData save(UserParam param){
        sysUserService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @RequiresPermissions("sysUser:edit")
    public JsonData update(UserParam param) {
        sysUserService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/delete.json")
    @RequiresPermissions("sysUser:del")
    public JsonData delete(@RequestParam("id") BigInteger id){
        sysUserService.delete(id);
        return JsonData.success();
    }

    @RequestMapping("/add.page")
    @RequiresPermissions("sysUser:add")
    public ModelAndView add() {
        return new ModelAndView("/sys/user/form");
    }

    @RequestMapping("/edit.page")
    @RequiresPermissions("sysUser:edit")
    public ModelAndView edit(@RequestParam("id") BigInteger id) {
        ModelAndView mv = new ModelAndView("/sys/user/form");
        SysUser sysUser = sysUserService.findOne(id);
        mv.addObject("sysUser",sysUser);
        return mv;
    }

    @RequestMapping("/setRole.page")
    @RequiresPermissions("sysUser:setRole")
    public ModelAndView setUser(@RequestParam("id") BigInteger id) {
        ModelAndView mv = new ModelAndView("/sys/user/formRole");
        SysUser sysUser = sysUserService.findOne(id);
        mv.addObject("sysUser",sysUser);
        List<SysRole> allRoles = sysRoleService.getAll();
        mv.addObject("allRoles",allRoles);
        List<SysRole> sysRoleList = sysUser.getRoles();
        Set<BigInteger> collect = sysRoleList.stream().map(e -> e.getId()).collect(Collectors.toSet());
        if(CollectionUtils.isNotEmpty(collect)){
            mv.addObject("jsonList",JSONObject.toJSONString(collect));
        }
        return mv;
    }

    @RequestMapping(value = "/saveRole.json")
    @RequiresPermissions("sysUser:setRole")
    public JsonData saveRole(@RequestParam("id") BigInteger id, @RequestParam("roleIds") String roleIds) {
        List<BigInteger> roleIdList = JSON.parseArray(roleIds).toJavaList(BigInteger.class);
        sysUserService.saveRole(id, roleIdList);
        return JsonData.success();
    }
}