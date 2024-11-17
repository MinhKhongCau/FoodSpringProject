package com.myproject.SpringStarter.Security;

import javax.management.relation.Role;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.myproject.SpringStarter.Until.Constants.Privillage;

@Configuration
@EnableWebSecurity
public class WebSecurity {
    private static final String[] WHILELIST = {
        "/",
        "/home",
        "/register",
        "/about/**",
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
                .requestMatchers(WHILELIST).permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/post/**").authenticated()
                .requestMatchers("/editor/**").hasAnyRole("ADMIN","EDITOR")
                .requestMatchers("/admin/**").hasAnyAuthority(Privillage.ACCESS_ADMIN_PANEL.getName())
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
        http.csrf(csrf -> csrf .disable());
        http.headers(h -> h.frameOptions(c -> c.disable()));

        return http.build();
    }

    @Bean
    static PasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

}
