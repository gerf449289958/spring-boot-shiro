package com.neo.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;


public class UpdateUtil extends BeanUtils{
	
	/**
	 * 
	 * @param src 新建
	 * @param target 数据库
	 * @throws BeansException
	 */
    public static void copyNonNullProperties(Object src,Object target) throws BeansException{
        BeanUtils.copyProperties(target,src,getNullProperties(src));
    }

    private static String[] getNullProperties(Object src){
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> emptyName = new HashSet<>();
        for(PropertyDescriptor p:pds){
            Object srcValue = srcBean.getPropertyValue(p.getName());
            if(srcValue != null) emptyName.add(p.getName());
        }
        String[] result = new String[emptyName.size()];
        return emptyName.toArray(result);
    }
}
