package com.guyongzhe.web_terminal.filter;

import com.guyongzhe.web_terminal.entity.AdminLogin;
import com.guyongzhe.web_terminal.utils.JwtUtil;
import com.guyongzhe.web_terminal.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);//没有token时放行
            return;
        }
        //解析token
        String id;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            id = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            returnJson(response);
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        AdminLogin adminLogin = redisCache.getCacheObject(id);
        if(Objects.isNull(adminLogin)){
            returnJson(response);
            throw new RuntimeException("登录过期");
        }
        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(adminLogin,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
    private void returnJson(HttpServletResponse response){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            String error = "token无效";
            writer.print(error);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
