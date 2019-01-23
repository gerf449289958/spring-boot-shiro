package com.neo.common.base;

import com.neo.common.config.Global;
import com.neo.common.utils.IpUtil;
import com.neo.common.utils.MD5Util;
import com.neo.modules.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;

/**
 * @Auther: gerf
 * @Date: 2018/12/28 11:05
 * @Description:
 */
@Slf4j
@Service
public class BaseService {
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    protected SysUser getUser() {
        //return null;
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected BigInteger getUserId() {
        BigInteger id = BigInteger.ONE;
        SysUser user = getUser();
        if(user != null){
            id = user.getId();
        }
        return id;
    }

    protected String getUsername() {
        String username = "admin";
        SysUser user = getUser();
        if(user != null){
            username = user.getUsername();
        }
        return username;
    }

    protected String getUserIp() {
        return IpUtil.getUserIP(getRequest());
    }

    protected String getPwd(){
        return MD5Util.encrypt(Global.DEFAULT_PASSWORD);
    }

}