package com.discord.Dashboard.config;

import com.discord.Dashboard.entity.UserEntity;
import com.discord.Dashboard.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// This configuration class sets up security for the application, allowing all HTTP methods to all endpoints.
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure security settings for the application
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.POST,"/users/**","/api/**").permitAll() // Allow access to user registration)
//                        .anyRequest().authenticated()
//                )
//                .csrf(csrf -> csrf.disable());

        // Allow all HTTP methods to all endpoints

//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll() // Allow all HTTP methods to all endpoints
//                );
//
//        return http.build();
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/users/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthProvider());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPasswordHash())
                    .authorities("USER")
                    .build();
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(email -> userDetailsService().loadUserByUsername(email));
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
