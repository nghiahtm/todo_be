package com.nghia.todolist.secure.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nghia.todolist.dto.BaseResponseDto;
import com.nghia.todolist.entity.ErrorDetail;
import com.nghia.todolist.secure.jwt.JwtUtil;
import com.nghia.todolist.service.AuthUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtils;
    private final AuthUserDetailService authUserDetailService;
    public AuthFilter(JwtUtil jwtUtils,AuthUserDetailService authUserDetailService) {
        this.jwtUtils = jwtUtils;
        this.authUserDetailService = authUserDetailService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !(authHeader.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = authHeader.substring(7);
            String username = jwtUtils.extractUsername(token);

            if (username == null || !jwtUtils.validateToken(token)) {
                throw new RuntimeException("Invalid JWT Token");
            }
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = authUserDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        catch (ExpiredJwtException e) {
            setErrorResponse(HttpServletResponse.SC_FORBIDDEN, response, "Token expired");
            return;
        }
        catch (MalformedJwtException e) {
            setErrorResponse(HttpServletResponse.SC_FORBIDDEN, response, "Invalid token");
            return;
        }
        catch (Exception e) {
            setErrorResponse(HttpServletResponse.SC_FORBIDDEN, response, "Unauthorized request");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(int status, HttpServletResponse response, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        BaseResponseDto<Object> baseError = BaseResponseDto.error(
                status,
                message,
                List.of(new ErrorDetail(
                        message,status
                )),
                System.currentTimeMillis()
        );
        String json = new ObjectMapper().writeValueAsString(baseError);
        response.getWriter().write(json);
    }
}
