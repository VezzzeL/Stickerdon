package com.stickerdon.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AdminConfiguration {
    @Bean
    public UserDetailsService userDetailsService(){
        return new AdminServiceConfig();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .requestMatchers("/js/**", "/css/*", "/data/*", "/img/*", "/scss/**", "/vendor/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
                AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(bCryptPasswordEncoder());
        return authenticationManagerBuilder.build();
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService())
//                .passwordEncoder(bCryptPasswordEncoder())
//                .and()
//                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authenticationManager(authenticationManager(http))
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
//                .requestMatchers("/*")
//                .permitAll()
//                .requestMatchers("/admin/*").hasAuthority("ADMIN")
                .and()
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/do-login")
//                                .defaultSuccessUrl("/index")
                                .successForwardUrl("/index")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout/")
                                .permitAll()
                );
        return http.build();
    }



}
