package com.shoppi.admin.security;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new ShoppiUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	
//	@Bean
//	SecurityFilterChain configureHttpSecurity(HttpSecurity http) throws Exception {
//		
//		http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
//		
//		return http.build();
//	}

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .authenticationProvider(authenticationProvider())
                   .build();
    }
	
    @Bean
    public SecurityFilterChain configureHttpSecurity(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()
            )
            .rememberMe(rememberMe -> rememberMe
                    .key("AbcDefgHijKlmnOpqrs_1234567890") // Key bí mật dùng để mã hóa token
                    .tokenValiditySeconds(7 * 24 * 60 * 60) // Token tồn tại trong 7 ngày
                )
			;

        return http.build();
    }
	

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
