package com.watermelon.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class MyBeanUtils {

    //获取属性值为null的属性名数组
    public static String[] getNullPropertiesName(Object source){
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        List<String> names = new ArrayList<String>();
        for (PropertyDescriptor pd : propertyDescriptors){
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName)==null){
                names.add(propertyName);
            }
        }
        return names.toArray(new String[names.size()]);
    }

}
