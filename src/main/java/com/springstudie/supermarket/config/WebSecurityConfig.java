package com.springstudie.supermarket.config;

import com.springstudie.supermarket.services.UserDetailsServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig{
    public final UserDetailsServices userDetailsServices;

    public WebSecurityConfig(UserDetailsServices userDetailsServices) {
        this.userDetailsServices = userDetailsServices;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/img/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/user/register").permitAll()
                .anyRequest().authenticated();

        http
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .permitAll()
                        .defaultSuccessUrl("/")
                );

        http
                .logout((logout) -> logout
                        .logoutUrl("/user/logout")
                        .logoutRequestMatcher(
                                new AntPathRequestMatcher("/user/logout", "GET")
                        )
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
