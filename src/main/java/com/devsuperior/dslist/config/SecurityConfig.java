package com.devsuperior.dslist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.devsuperior.dslist.security.CustomUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desativa CSRF só pra facilitar teste local
            .csrf(csrf -> csrf.disable())
            // Libera o uso de frames (necessário pro H2 Console funcionar)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            // Define as rotas públicas e privadas
            .authorizeHttpRequests(requests -> requests
                // Libera acesso total ao H2 console
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                // Libera login e registro
                .requestMatchers("/auth/**").permitAll()
                // Exige autenticação no restante
                .anyRequest().authenticated()
            )
            // Desativa o formulário padrão
            .formLogin(form -> form.disable())
            // Desativa o popup básico
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }
}
