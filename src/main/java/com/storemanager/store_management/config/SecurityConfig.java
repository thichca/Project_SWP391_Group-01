    package com.storemanager.store_management.config;

    import com.storemanager.store_management.service.CustomUserDetailsService;
    import com.storemanager.store_management.service.IService.UserService;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    public class SecurityConfig {

        @Bean
        public BCryptPasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider(UserService userService){
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setUserDetailsService(userService);
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            return daoAuthenticationProvider;
        }
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
            http.authorizeHttpRequests(
                    configurer->configurer
                            .requestMatchers("/register/**").permitAll()
                            .requestMatchers("/forget-password").permitAll()
                            .requestMatchers("/reset-password/**").permitAll()
                            .anyRequest().authenticated()
            ).formLogin(
                    form->form.loginPage("/login")
                            .loginProcessingUrl("/loginUser")
                            .defaultSuccessUrl("/admin", true)
                            .permitAll()
            ).logout(
                    logout->logout.permitAll()
            ).exceptionHandling(
                    configurer->configurer.accessDeniedPage("/showPage403")
            );

            return http.build();
        }


    }
