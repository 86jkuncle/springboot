package org.lybaobei.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * knife4j配置
 * @author nommpp
 * @date 2024/5/2 0002
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
public class Knife4jConfig {
    
    @Bean
    public Docket adminApiConfig(){
        
        Docket adminApi = new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.lybaobei.api"))
                .paths(PathSelectors.regex("/admin/.*"))
                .build();
        return adminApi;
    }
    
    private ApiInfo adminApiInfo(){
        return new ApiInfoBuilder()
                .title("后台管理系统-API文档")
                .description("本文档描述了后台管理系统微服务接口定义")
                .version("1.0")
                .contact(new Contact("hntt","http://hntt.com","hntt@hntt.com"))
                .build();
        
    }
    
}

