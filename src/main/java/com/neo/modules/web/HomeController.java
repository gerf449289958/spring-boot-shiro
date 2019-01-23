package com.neo.modules.web;

import com.alibaba.fastjson.JSONObject;
import com.neo.modules.service.SysMenuService;
import com.neo.modules.vo.MenuVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class HomeController {

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping({"/","/index.page"})
    public ModelAndView index(){
        List<MenuVO> menuVOList = sysMenuService.menuTree();
        ModelAndView mv = new ModelAndView("/index");
        String jsonString = JSONObject.toJSONString(menuVOList);
        mv.addObject("menuList",menuVOList);
        mv.addObject("menuJson",jsonString);
        return mv;
    }

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, Map<String, Object> map){
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        String msg = null;
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception) || IncorrectCredentialsException.class.getName().equals(exception)) {
                log.info("账号或密码不正确");
                msg = "账号或密码不正确";
            } else if(DisabledAccountException.class.getName().equals(exception)){
                log.info("该已帐号禁止登录");
                msg = "该已帐号禁止登录";
            } else {
                log.info("else -- > {}", exception);
                msg = "账号异常";
            }
        }
        ModelAndView mv = new ModelAndView("/login");
        map.put("msg", msg);
        return mv;
    }
}