package com.storemanager.store_management.config;

import com.storemanager.store_management.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Cấu hình phân quyền truy cập
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login", "/register").permitAll()  // Cho phép truy cập trang login và register
                                .requestMatchers("/admin/**").hasRole("admin") // Chỉ admin có quyền truy cập các trang admin
                                .requestMatchers("/admin").hasRole("employee") // Chỉ user có quyền truy cập các trang user
                                .requestMatchers("/owner/**").hasRole("owner") // Chỉ user có quyền truy cập các trang user
                                .anyRequest().authenticated() // Các trang khác yêu cầu đăng nhập
                )
                .formLogin(form -> form
                        .loginPage("/login") // Trang login tùy chỉnh
                        .loginProcessingUrl("/login") // URL xử lý đăng nhập
                        .defaultSuccessUrl("/admin", true) // Trang sau khi đăng nhập thành công
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL để logout
                        .logoutSuccessUrl("/login?logout") // Trang sẽ chuyển đến sau khi logout thành công
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers("/static/**", "assets/**"));
    }
}
