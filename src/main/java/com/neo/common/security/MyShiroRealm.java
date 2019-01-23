package com.neo.common.security;

import com.neo.common.config.Global;
import com.neo.modules.entity.SysMenu;
import com.neo.modules.entity.SysRole;
import com.neo.modules.entity.SysUser;
import com.neo.modules.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private SysUserService sysUserService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("权限配置");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser userInfo  = (SysUser)principals.getPrimaryPrincipal();
        for(SysRole role:userInfo.getRoles()){
            authorizationInfo.addRole(role.getRole());
            for(SysMenu menu:role.getMenus()){
                if(StringUtils.isNotBlank(menu.getPermission())){
                    authorizationInfo.addStringPermission(menu.getPermission());
                }
            }
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser sysUser = sysUserService.findByUsername(username);
        if(sysUser == null){
            return null;
        }
        if (Global.NO.equals(sysUser.getState())){
            throw new DisabledAccountException();
        }
        return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), getName());
    }
}