package com.G153.InfratrackUserPortal.Configuration;

import com.G153.InfratrackUserPortal.security.JwtAuthenticationFilter;
import com.G153.InfratrackUserPortal.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Security configuration class for setting up Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider tokenProvider;

    /**
     * Constructor for SecurityConfig.
     *
     * @param tokenProvider the JWT token provider
     */
    public SecurityConfig(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity object
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use stateless session management
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, ex) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Token expired or invalid");
                        })) // Handle authentication exceptions
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login",
                                "/api/users/forget-password", "/api/users/verify-otp",
                                "/api/users/reset-password", "/api/admin/login", "/api/admin/register", "/swagger-ui/**",
                                "/v3/api-docs/**", "/swagger-ui.html").permitAll() // Allow public access to specific endpoints
                        .anyRequest().authenticated()) // Require authentication for all other requests
                .addFilterBefore(jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class); // Add JWT authentication filter
        return http.build();
    }

    /**
     * Creates a JwtAuthenticationFilter bean.
     *
     * @return the JwtAuthenticationFilter
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider);
    }

    /**
     * Creates a PasswordEncoder bean.
     *
     * @return the PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}