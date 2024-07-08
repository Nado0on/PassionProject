package com.photo.shoot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/photo_shoot/**", "/look_book/**",
                                "/schedule/**", "/payment/**", "/upload/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT, "/photo_shoot/**", "/look_book/**",
                                "/schedule/**", "/payment/**", "/upload/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/photo_shoot/**", "/look_book/**",
                                "/schedule/**", "/payment/**", "/upload/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/photo_shoot/**", "/look_book/**",
                                "/schedule/**", "/payment/**", "/upload/**")
                        .permitAll()
                        .anyRequest().authenticated());
        httpSecurity.cors(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
