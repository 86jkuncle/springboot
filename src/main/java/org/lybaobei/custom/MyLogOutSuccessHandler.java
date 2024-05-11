package org.lybaobei.custom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.lybaobei.enumpkg.ResultCodeEnum;
import org.lybaobei.utils.ResponseUtil;
import org.lybaobei.vo.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
* @Description java类的作用
* @Author mtxst
* @Date 2024/5/12 2:10 $
*/
@Component
public class MyLogOutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication)
        throws IOException, ServletException {

        ResponseUtil.out(response, Result.success(ResultCodeEnum.USER_LOGOUT_SUCCESS));

//        (request,response,authentication) -> {
//            Map<String,Object> map = new HashMap<String,Object>();
//            map.put("code",200);
//            map.put("message","退出成功");
//            map.put("data",authentication);
//            response.setContentType("application/json;charset=utf-8");
//            PrintWriter out = response.getWriter();
//            out.write(objectMapper.writeValueAsString(map));
//            out.flush();
//            out.close();
//        }
    }
}
