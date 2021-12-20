package com.ceva.base.common.bean.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.ceva.base.common.bean.AjaxActionBean;

public class AjaxBeanProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		 System.out.println("AfterInitialization : " + beanName);  
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("BeforeInitialization : " + beanName);  
		AjaxActionBean bean1 = null;
		if(bean instanceof AjaxActionBean) {
			bean1 = (AjaxActionBean) bean;
		}
		System.out.println(bean1);
		
		return bean;
	}

}
