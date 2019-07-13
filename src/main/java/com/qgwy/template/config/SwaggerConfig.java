package com.qgwy.template.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ^_^
 * Description: swagger配置

 */
@EnableSwagger2
@Configuration
//@ConditionalOnExpression("${radar.debug:true}")
public class SwaggerConfig {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //.groupName("矿池")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qgwy"))
                .paths(PathSelectors.regex("/*.*"))
                .build();
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder ()
                .title("swagger-api 文档")
                .description("乾古文娱内部系统")
                .version("1.0")
                .build();
    }


}
