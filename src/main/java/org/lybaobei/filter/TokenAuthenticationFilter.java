package org.lybaobei.filter;


import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.lybaobei.enumpkg.ResultCodeEnum;
import org.lybaobei.utils.JWTUtil;
import org.lybaobei.utils.ResponseUtil;
import org.lybaobei.vo.Result;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author nommpp
 * @date 2024/5/5 0005
 */

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    public TokenAuthenticationFilter() {
    }

    private static final Set<String> allUrls =
            Collections.unmodifiableSet(Sets.newHashSet("/admin/system/user/logout","/admin/system/user/login","/swagger-resources","/favicon.ico","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html"));


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         if(allUrls.contains(request.getRequestURI())
            || request.getRequestURI().startsWith("/v2")
            || request.getRequestURI().startsWith("/swagger-resources")){
            filterChain.doFilter(request,response);
        }else{
            UsernamePasswordAuthenticationToken authenticationToken =
                    getAuthentication(request);
            if(null != authenticationToken){
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request,response);
            }else{
                ResponseUtil.out(response, Result.fail(ResultCodeEnum.UNAUTHORIZED));
            }
        }
        
        

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(!StringUtils.isEmpty(token)){
            String userName = JWTUtil.getUserName(token);
            if(!StringUtils.isEmpty(userName)){
                return new UsernamePasswordAuthenticationToken(userName,null, Collections.emptyList());
            }
        }
        return null;
    }
}
