package com.personal.foodcost.config;

import com.personal.foodcost.filter.JwtAuthFilter;
import com.personal.foodcost.models.DTOs.MyUserDetailService;
import com.personal.foodcost.models.DTOs.ROLES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private MyUserDetailService userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/auth/login").permitAll();
                    registry.requestMatchers("/api/user/**").hasRole(ROLES.ADMIN.toString());

                    //restaurant permissions
                    registry
                            .requestMatchers(HttpMethod.POST, "/api/restaurant")
                            .hasRole(ROLES.OWNER.toString());
                    registry
                            .requestMatchers(HttpMethod.GET, "/api/restaurant/{id}")
                            .hasAnyRole(ROLES.ADMIN.toString(), ROLES.OWNER.toString());
                    registry
                            .requestMatchers(HttpMethod.PUT, "/api/restaurant/{id}")
                            .hasRole(ROLES.OWNER.toString());

                    registry.requestMatchers("/api/restaurant/**").hasRole(ROLES.ADMIN.toString());


                    //dish permissions
                    registry
                            .requestMatchers(HttpMethod.GET, "/api/dish/**")
                            .hasRole(ROLES.EMPLOYEE.toString());
                    registry.requestMatchers("/api/dish/**").hasAnyRole(ROLES.ADMIN.toString(), ROLES.OWNER.toString());

                    //ingredient permissions
                    registry.requestMatchers(HttpMethod.GET,"/api/ingredient/**")
                            .hasRole(ROLES.EMPLOYEE.toString());

                    registry.requestMatchers("/api/ingredient/**")
                            .hasAnyRole(ROLES.ADMIN.toString(), ROLES.OWNER.toString());

                    //raw material permissions
                    registry.requestMatchers(HttpMethod.GET,"/api/raw/**")
                            .hasRole(ROLES.EMPLOYEE.toString());
                    registry.requestMatchers("/api/raw/**")
                            .hasAnyRole(ROLES.ADMIN.toString(), ROLES.OWNER.toString());

                    registry.anyRequest().authenticated();

                })
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public UserDetailsService userDetailService() {
        return userDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }
}
