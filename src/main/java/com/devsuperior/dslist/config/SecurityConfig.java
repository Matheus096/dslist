package com.devsuperior.dslist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.devsuperior.dslist.security.CustomUserDetails;
import com.devsuperior.dslist.security.CustomUserDetailsService;
import com.devsuperior.dslist.security.jwt.JwtFilter;
import com.devsuperior.dslist.security.jwt.JwtUtil;
import com.devsuperior.dslist.services.CustomOAuth2UserService;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(withDefaults())
            // Desativa CSRF só pra facilitar teste local
            .csrf(csrf -> csrf.disable())
            // Libera o uso de frames (necessário pro H2 Console funcionar)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            // Define as rotas públicas e privadas
            .authorizeHttpRequests(requests -> requests
                // Libera acesso total ao H2 console
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                // Libera login e registro (auth)
                .requestMatchers("/auth/**").permitAll()
                // libera rota de teste para acessar games pelo postman
                .requestMatchers("/games", "/games/**").permitAll()
                // Libera acesso às rotas do RAWG para testes no postman
                .requestMatchers("/api/rawg/**").permitAll()
                // Exige autenticação no restante
                .anyRequest().authenticated()
            )
            // Desativa o formulário padrão
            .formLogin(form -> form.disable())
            // Desativa o popup básico
            .httpBasic(basic -> basic.disable())
            // Define o gerenciador de autenticação oauth2 do Google, que no caso é a classe CustomOAuth2UserService
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )

                .successHandler((request, response, authentication) -> {

                    // Pegado o usuário que o Spring acabou de autenticar/logar
                    DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
                    String email = principal.getAttribute("email");

                    // Log dos dados do Google só pra eu ver como vem hehehe
                    String name = principal.getAttribute("name");
                    String password = principal.getAttribute("password");
                    // System.out.println("NAME: " + name + ", PASSWORD: " + password + ", EMAIL: " + email);

                    CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);
                    String token = jwtUtil.generateToken(userDetails); 

                    // Redireciona para o Angular passando o token na URL
                    response.sendRedirect("http://localhost:4200/home?token=" + token);
                })
            );

            // Aqui é adicionado o filtro de JWT antes do filtro padrão de autenticação
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
