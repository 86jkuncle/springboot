package org.lybaobei.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.RequestHandler;
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

    /**
     * 定义分隔符
     */
    private static final String SPLITOR = ";";
    @Bean
    public Docket adminApiConfig(){
        
        Docket adminApi = new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.lybaobei.api"))
//                .paths(PathSelectors.regex("/admin/.*"))
            .paths(PathSelectors.any())
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


    /**
     * @author luoyu
     * @description 重写basePackage方法，使能够实现多包访问
     * @param basePackage 所有包路径
     * @return Predicate<RequestHandler>
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).map(handlerPackage(basePackage)::apply).orElse(true);
    }

    /**
     * @author luoyu
     * @description 重写basePackage方法，使能够实现多包访问
     * @param basePackage 所有包路径
     * @return Function<Class<?>, Boolean>
     */
    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(SPLITOR)) {
                assert input != null;
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * @author luoyu
     * @description 重写basePackage方法，使能够实现多包访问
     * @param input
     * @return Optional<? extends Class<?>>
     */
    private static Optional<Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }

    
}

