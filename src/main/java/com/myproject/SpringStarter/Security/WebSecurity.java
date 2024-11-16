package com.myproject.SpringStarter.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {
    private static final String[] WHILELIST = {
        "/",
        "/home",
        "/register",
        "/db-console/**",
        "css/**",
        "fonts/**",
        "images/**",
        "js/**"
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request -> request
                .requestMatchers(WHILELIST)
                .permitAll()
                .anyRequest()
                .authenticated()
            
            )
            // Config when no permission page will be redirect login page '/login'
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/",true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll());

        return http.build();
    }

    @Bean
    static PasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}
