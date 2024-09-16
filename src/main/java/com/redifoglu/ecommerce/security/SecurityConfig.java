//package com.redifoglu.ecommerce.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        jdbcUserDetailsManager.setUsersByUsernameQuery(
//                "SELECT email, password FROM customer WHERE email=? UNION " +
//                        "SELECT phone, password FROM seller WHERE phone=? UNION " +
//                        "SELECT username, password FROM admin WHERE username=?"
//        );
//
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
//                "SELECT email, role FROM customer WHERE email=? UNION " +
//                        "SELECT phone, role FROM seller WHERE phone=? UNION " +
//                        "SELECT username, role FROM admin WHERE username=?"
//        );
//
//        return jdbcUserDetailsManager;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return new ProviderManager(provider);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/auth/**").permitAll();
//                    auth.requestMatchers("/api/admin/**").hasRole("ADMIN");
//                    auth.requestMatchers("/api/seller/**").hasRole("SELLER");
//                    auth.requestMatchers("/api/customer/**").hasRole("CUSTOMER");
//                    auth.anyRequest().authenticated();
//                })
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
////                .oauth2Login(Customizer.withDefaults())
//                .build();
//    }
//}
