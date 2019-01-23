package com.neo.common.exception;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;

@Slf4j
public class FreemarkerExceptionHandler implements TemplateExceptionHandler{
	
	@Override
	public void handleTemplateException(TemplateException te, Environment env, Writer out)throws TemplateException {
		log.error("页面出错，请联系管理员:"+te.getMessage());  
        String missingVariable = "undefined";  
        try {  
            String[] tmp = te.getMessageWithoutStackTop().split("\n");  
            if (tmp.length > 1) {
            	tmp = tmp[1].split(" ");  
            } 
            if (tmp.length > 1) {
            	missingVariable = tmp[1];  
            }
            out.write("页面参数：${ "+missingVariable+"}错误，请联系管理员");
        } catch (IOException e) {  
            throw new TemplateException("Failed to print error message. Cause:" + e, env);  
        }  
	}

}
