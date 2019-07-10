package com.qgwy.template.aop.aspect;

import com.qgwy.template.annotation.DataSource;
import com.qgwy.template.constants.DataSourceNames;
import com.qgwy.template.util.DynamicDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import java.lang.reflect.*;
import org.aspectj.lang.reflect.*;
import org.springframework.stereotype.Component;

/**
 * 多数据源，切面处理类 处理带有注解的方法类
 */
@Component
@Aspect
public class DataSourceAspect implements Ordered {
 
	protected Logger logger = LoggerFactory.getLogger(getClass());
 
	@Pointcut("@annotation(com.qgwy.template.annotation.DataSource)")//注意：这里的xxxx代表的是上面public @interface DataSource这个注解DataSource的包名
	public void dataSourcePointCut() {
 
	}
 
	@Around("dataSourcePointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		DataSource ds = method.getAnnotation(DataSource.class);
		if (ds == null) {
			DynamicDataSource.setDataSource(DataSourceNames.FIRST);
			logger.debug("set datasource is " + DataSourceNames.FIRST);
		} else {
			DynamicDataSource.setDataSource(ds.name());
			logger.debug("set datasource is " + ds.name());
		}
		try {
			return point.proceed();
		} finally {
			DynamicDataSource.clearDataSource();
			logger.debug("clean datasource");
		}
	}
 
	@Override
	public int getOrder() {
		return 1;
	}
}