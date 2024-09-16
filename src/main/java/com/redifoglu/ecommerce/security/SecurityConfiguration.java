package com.redifoglu.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {

                    //HERKESE AÇIK
                    auth.requestMatchers("/welcome/**",
                            "/auth/register/customer").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/product/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/category/**").permitAll();

                    //SADECE ADMIN
                    auth.requestMatchers("/auth/register/admin",
                            "/auth/register/seller",
                            "/admin/**",
                            "/customer/customers").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/category/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/category/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/category/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/customer",
                            "/customers").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/customer").hasAuthority("ADMIN");

                    //SADECE SELLER
                    auth.requestMatchers(HttpMethod.POST, "/product/**").hasAuthority("SELLER");
                    auth.requestMatchers(HttpMethod.PUT, "/product/**").hasAuthority("SELLER");
                    auth.requestMatchers(HttpMethod.DELETE, "/product/**").hasAuthority("SELLER");

                    //SADECE CUSTOMER
                    auth.requestMatchers("/cart",
                            "/order",
                            "/address",
                            "customer/address/**",
                            "/customer/orders").hasAuthority("CUSTOMER");

                    auth.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
