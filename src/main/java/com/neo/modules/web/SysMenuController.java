package com.neo.modules.web;

import com.neo.common.config.JsonData;
import com.neo.modules.entity.SysMenu;
import com.neo.modules.param.MenuParam;
import com.neo.modules.service.SysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;

/**
 * @Auther: gerf
 * @Date: 2018/12/19 15:31
 * @Description: 菜单
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService menuService;

    @RequestMapping("/save.json")
    @RequiresPermissions("sysMenu:add")
    public JsonData saveUser(MenuParam param) {
        menuService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @RequiresPermissions("sysMenu:edit")
    public JsonData update(MenuParam param) {
        menuService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/delete.json")
    @RequiresPermissions("sysMenu:del")
    public JsonData delete(@RequestParam("id") BigInteger id) {
        menuService.delete(id);
        return JsonData.success();
    }

    @RequestMapping("/list.page")
    @RequiresPermissions("sysMenu:view")
    public ModelAndView list() {
        String jsonList = menuService.getAllListToJson();
        ModelAndView mv = new ModelAndView("/sys/menu/list");
        mv.addObject("jsonList",jsonList);
        return mv;
    }

    @RequestMapping("/add.page")
    @RequiresPermissions("sysMenu:add")
    public ModelAndView add(@RequestParam("parentId") BigInteger parentId) {
        ModelAndView mv = new ModelAndView("/sys/menu/form");
        if(null != parentId && parentId.compareTo(BigInteger.ZERO) != 0){
            SysMenu parentMenu = menuService.findByParentId(parentId);
            mv.addObject("parentMenu",parentMenu);
        }
        return mv;
    }

    @RequestMapping("/edit.page")
    @RequiresPermissions("sysMenu:edit")
    public ModelAndView edit(@RequestParam("id") BigInteger id) {
        ModelAndView mv = new ModelAndView("/sys/menu/form");
        if(null != id){
            SysMenu menu = menuService.findOne(id);
            if(menu.getParentId().compareTo(BigInteger.ZERO) != 0) {
                SysMenu parentMenu = menuService.findByParentId(menu.getParentId());
                mv.addObject("parentMenu",parentMenu);
            }
            mv.addObject("menu",menu);
        }
        return mv;
    }
}