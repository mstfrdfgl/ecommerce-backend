package com.redifoglu.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;

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
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {

                    //HERKESE AÇIK
                    auth.requestMatchers("/welcome/**",
                            "/auth/register/customer",
                            "/seller/products").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/product/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/category/**").permitAll();

                    //SADECE ADMIN
                    auth.requestMatchers("/auth/register/admin",
                            "/auth/register/seller",
                            "/admin/**",
                            "/customer/customers",
                            "/seller/{id}/**",
                            "seller/phone",
                            "/order/update",
                            "/order/orders",
                            "/order/{orderId}/confirm-delivery").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/category/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/category/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/category/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/customer",
                            "/customers").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/customer").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/product/**").hasAuthority("ADMIN");

                    //SADECE SELLER
                    auth.requestMatchers("/order/{orderId}/shipped-delivery");
                    auth.requestMatchers(HttpMethod.POST, "/product/**").hasAuthority("SELLER");
                    auth.requestMatchers(HttpMethod.PUT, "/product/**").hasAuthority("SELLER");
                    auth.requestMatchers(HttpMethod.DELETE, "/product/**").hasAuthority("SELLER");

                    //SADECE CUSTOMER
                    auth.requestMatchers("/cart",
                            "/address",
                            "customer/address/**",
                            "/customer/orders",
                            "/order/create",
                            "/order/my-orders",
                            "/order/{orderId}/cancel-delivery").hasAuthority("CUSTOMER");

                    auth.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
