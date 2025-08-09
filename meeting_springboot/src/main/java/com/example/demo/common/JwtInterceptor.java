package com.example.demo.common;
/**
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.entity.Role;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.RoleMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;

@RestController
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler){
        if (request.getRequestURI().contains("/login")) {
            return true;
        }
        if (request.getRequestURI().contains("/add")) {
            return true;
        }

        //放行OPTIONS请求
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }

        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }

        if (StrUtil.isBlank(token)) {
            throw new ServiceException("401", "111");
        }

        String userId;
        try{
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException("401", "112");
        }

        Role role = roleMapper.findAllById(Integer.valueOf(userId));
        if (role == null) {
            throw new ServiceException("401", "113");
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(role.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException("401", "114");
        }
        return true;
    }
}
**/