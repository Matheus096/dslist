package com.devsuperior.dslist.services;

import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.devsuperior.dslist.entities.User;
import com.devsuperior.dslist.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomOAuth2UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        // Aqui é onde pega os dados do Google
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        userRepository.findByEmail(email).orElseGet(() -> {
            User user = new User();
            user.setUsername(name);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            return userRepository.save(user);
        });

        return oAuth2User;
    }

}
