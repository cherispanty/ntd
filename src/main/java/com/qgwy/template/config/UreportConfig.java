package com.qgwy.template.config;
 
import javax.servlet.Servlet;
 
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
 
import com.bstek.ureport.console.UReportServlet;
 
/**
 *  Ureport2 配置类
 */

@ImportResource("classpath:context.xml")
@EnableAutoConfiguration
@Configuration
public class UreportConfig {
	
	@Bean
	public ServletRegistrationBean<Servlet> buildUreportServlet(){
		return new ServletRegistrationBean<Servlet>(
				new UReportServlet(), "/ureport/*");
	}
	
}