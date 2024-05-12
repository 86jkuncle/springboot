package org.lybaobei.config;

import org.lybaobei.custom.CustomMd5Password;
import org.lybaobei.custom.MyLogOutSuccessHandler;
import org.lybaobei.filter.CustomAuthenticationProvider;
import org.lybaobei.filter.TokenAuthenticationFilter;
import org.lybaobei.filter.TokenLoginFilter;
import org.lybaobei.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
* @Description java类的作用
* @Author mtxst
* @Date 2024/5/13 6:21 $
*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MyLogOutSuccessHandler logoutHandler;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)

            .cors(Customizer.withDefaults())
            .authorizeRequests(auth->{
                auth.antMatchers("/favicon.ico","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html")
                    .permitAll().anyRequest().authenticated();
            })
            .addFilterBefore(new TokenAuthenticationFilter(redisUtil), UsernamePasswordAuthenticationFilter.class)
            .addFilter(new TokenLoginFilter(authenticationManager()))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .logout().logoutUrl("/admin/system/user/logout")
            .logoutSuccessHandler(logoutHandler).permitAll();
        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(){
        return new ProviderManager(customAuthenticationProvider);
    }


}
