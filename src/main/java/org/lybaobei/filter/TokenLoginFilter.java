package org.lybaobei.filter;

import cn.hutool.core.stream.CollectorUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.util.Collections;
import javax.annotation.Resource;
import lombok.SneakyThrows;
import org.lybaobei.common.Constants;
import org.lybaobei.custom.CusotmUser;
import org.lybaobei.dto.LoginDTO;
import org.lybaobei.entity.SystemUser;
import org.lybaobei.enumpkg.ResultCodeEnum;
import org.lybaobei.utils.JWTUtil;
import org.lybaobei.utils.RedisUtil;
import org.lybaobei.utils.ResponseUtil;
import org.lybaobei.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nommpp
 * @date 2024/5/5 0005
 */
@Component
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {


    private RedisUtil redisUtil;


    private ObjectMapper objectMapper = new ObjectMapper();

    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisUtil redisUtil){
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(true);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/user/login","POST"));
        this.redisUtil = redisUtil;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginDTO loginDTO = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        return this.getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        CusotmUser cusotmUser = (CusotmUser) authResult.getPrincipal();
        SystemUser user = cusotmUser.getSystemUser();
        String userId = user.getUserId();

        if(!cusotmUser.getAuthorities().isEmpty()){
            redisUtil.set(Constants.RedisKey.USER_PERMISSION_KEY +userId
                ,objectMapper.writeValueAsString(cusotmUser.getAuthorities()));
        }

        String token = JWTUtil.createToken(userId, user.getUserName());
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("token",token);
        ResponseUtil.out(response, Result.success(resultMap));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        if(failed instanceof BadCredentialsException){
            ResponseUtil.out(response,Result.fail(ResultCodeEnum.USER_BADCREDENTIALS));
        }else if(failed instanceof LockedException){
            ResponseUtil.out(response,Result.fail(ResultCodeEnum.USER_LOCKED));
        }else if(failed instanceof UsernameNotFoundException){
            ResponseUtil.out(response,Result.fail(403,failed.getMessage()));
        }else{
            ResponseUtil.out(response,Result.fail(ResultCodeEnum.UNKNOWN_ERROR));
        }
    }
}
