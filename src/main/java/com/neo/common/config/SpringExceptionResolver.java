package com.neo.common.config;

import com.neo.common.exception.ParamException;
import com.neo.common.exception.ShiroException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class SpringExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System error";

        // 这里我们要求项目中所有请求json数据，都使用.json结尾
        if (url.endsWith(".json")) {
            if (ex instanceof ShiroException || ex instanceof ParamException) {
                log.info("json exception, url:" + url);
                JsonData result = JsonData.error(ex.getMessage());
                mv = new ModelAndView(new MappingJackson2JsonView(), result.toMap());
            } else if(ex instanceof UnauthorizedException){
                mv = new ModelAndView(new MappingJackson2JsonView(),JsonData.error("没有权限").toMap());
            } else {
                log.error("unknown json exception, url:" + url, ex);
                JsonData result = JsonData.error(defaultMsg);
                mv = new ModelAndView(new MappingJackson2JsonView(), result.toMap());
            }
        } else if (url.endsWith(".page")){ // 这里我们要求项目中所有请求page页面，都使用.page结尾
            if(ex instanceof UnauthorizedException){
                mv = new ModelAndView("/error/403");
            }else{
                log.error("unknown page exception, url:" + url, ex);
                JsonData result = JsonData.error(defaultMsg);
                mv = new ModelAndView("/error/500", result.toMap());
            }
        } else {
            log.error("unknow exception, url:" + url, ex);
            JsonData result = JsonData.error(defaultMsg);
            mv = new ModelAndView(new MappingJackson2JsonView(), result.toMap());
        }
        return mv;
    }
}
