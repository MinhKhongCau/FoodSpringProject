package com.myproject.SpringStarter.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.myproject.SpringStarter.Until.Constants.Privillage;
import com.myproject.SpringStarter.Until.Constants.Roles;

@Configuration
@EnableWebSecurity
public class WebSecurity {
    private static final String[] WHILELIST = {
        "/",
        "/home",
        "/register",
        "/about/**",
        "/db-console/**",
        "/login/**",
        "css/**",
        "fonts/**",
        "images/**",
        "js/**"
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request -> request
                .requestMatchers(WHILELIST).permitAll()
                .requestMatchers("/post/**").authenticated()
                .requestMatchers("/admin/**").hasRole(Roles.ADMIN.getRole())
                .requestMatchers("/editor/**").hasAnyRole(Roles.ADMIN.getRole(),Roles.EDITOR.getRole())
                .requestMatchers("/admin/**").hasAuthority(Privillage.ACCESS_ADMIN_PANEL.getName())
            )
            // Config when no permission page will be redirect login page '/login'
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home",true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .permitAll());
        http.csrf(csrf -> csrf .disable());
        http.headers(h -> h.frameOptions(c -> c.disable()));

        return http.build();
    }

    @Bean
    static PasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

}
