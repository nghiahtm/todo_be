package com.nghia.todolist.secure;

import com.nghia.todolist.secure.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class APIConfig {
    private final AuthFilter authFilter;

    public APIConfig(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }
    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/auth/**"
    };

    // API chỉ cần user login (phải có token)
    private static final String[] USER_ENDPOINTS = {
            "/api/v1/users/**",
            "/api/v1/profile/**"
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(USER_ENDPOINTS).authenticated()
                        .anyRequest().denyAll()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
