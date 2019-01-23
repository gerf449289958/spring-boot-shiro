package com.neo.modules.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neo.common.config.Global;
import com.neo.common.config.JsonData;
import com.neo.common.utils.WebValueUtil;
import com.neo.modules.entity.SysRole;
import com.neo.modules.param.RoleParam;
import com.neo.modules.service.SysMenuService;
import com.neo.modules.service.SysRoleService;
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
import java.util.stream.Collectors;

/**
 * @Auther: gerf
 * @Date: 2018/12/28 14:15
 * @Description: 角色
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysMenuService sysMenuService;


    @RequestMapping("/save.json")
    @RequiresPermissions("sysRole:add")
    public JsonData saveUser(RoleParam param) {
        roleService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @RequiresPermissions("sysRole:edit")
    public JsonData update(RoleParam param) {
        roleService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/delete.json")
    @RequiresPermissions("sysRole:del")
    public JsonData delete(@RequestParam("id") BigInteger id) {
        roleService.delete(id);
        return JsonData.success();
    }

    @RequestMapping("/stop.json")
    @RequiresPermissions("sysRole:stop")
    public JsonData stop(@RequestParam("id") BigInteger id) {
        roleService.stop(id);
        return JsonData.success();
    }

    @RequestMapping("/star.json")
    @RequiresPermissions("sysRole:star")
    public JsonData star(@RequestParam("id") BigInteger id) {
        roleService.star(id);
        return JsonData.success();
    }

    @RequestMapping("/list.page")
    @RequiresPermissions("sysRole:list")
    public ModelAndView list(HttpServletRequest request, RoleParam param) {
        //获取请求参数
        String pageIndex = WebValueUtil.getWebValue(request, "pageIndex");
        //参数封装
        int pageNo = 0;
        if (StringUtils.isNotBlank(pageIndex)) {
            pageNo = Integer.parseInt(pageIndex);
        }
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(pageNo, Global.DEFAULT_PAGESIZE, sort);
        Page<SysRole> page = roleService.findPage(pageable, param);
        ModelAndView mv = new ModelAndView("/sys/role/list");
        mv.addObject("param", JSONObject.toJSONString(param));
        mv.addObject("page",page);
        return mv;
    }

    @RequestMapping("/add.page")
    @RequiresPermissions("sysRole:add")
    public ModelAndView add() {
        return new ModelAndView("/sys/role/form");
    }

    @RequestMapping("/edit.page")
    @RequiresPermissions("sysRole:edit")
    public ModelAndView edit(@RequestParam("id") BigInteger id) {
        ModelAndView mv = new ModelAndView("/sys/role/form");
        SysRole sysRole = roleService.findOne(id);
        mv.addObject("sysRole",sysRole);
        return mv;
    }

//    @RequestMapping("/setUser.page")
//    @RequiresPermissions("sysRole:setUser")
//    public ModelAndView setUser(@RequestParam("id") BigInteger id) {
//        ModelAndView mv = new ModelAndView("/sys/role/formUser");
//        SysRole sysRole = roleService.findOne(id);
//        List<SysUser> sysUserList = sysRole.getUsers();
//        if(CollectionUtils.isNotEmpty(sysUserList)){
//            mv.addObject("jsonList",JSONObject.toJSONString(sysUserList));
//        }
//        return mv;
//    }

    @RequestMapping("/setMenu.page")
    @RequiresPermissions("sysRole:setMenu")
    public ModelAndView setMenu(@RequestParam("id") BigInteger id) {
        ModelAndView mv = new ModelAndView("/sys/role/formMenu");
        SysRole sysRole = roleService.findOne(id);
        mv.addObject("sysRole",sysRole);
        List<BigInteger> menuIds = sysRole.getMenus().stream().map(e -> e.getId()).collect(Collectors.toList());
        mv.addObject("menuIds",JSONObject.toJSONString(menuIds));
        String menuJson = sysMenuService.getAllListToJson();
        mv.addObject("jsonList",menuJson);
        return mv;
    }

    @RequestMapping(value = "/saveMenu.json")
    @RequiresPermissions("sysRole:setMenu")
    public JsonData saveMenu(@RequestParam("id") BigInteger id, @RequestParam("menuIds") String menuIds) {
        List<BigInteger> ids = JSON.parseArray(menuIds).toJavaList(BigInteger.class);
        roleService.saveMenu(id, ids);
        return JsonData.success();
    }
}