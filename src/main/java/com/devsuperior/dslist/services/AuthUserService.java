package com.devsuperior.dslist.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devsuperior.dslist.dto.AuthResponseUserDTO;
import com.devsuperior.dslist.dto.UserDTO;
import com.devsuperior.dslist.entities.User;
import com.devsuperior.dslist.repositories.UserRepository;
import com.devsuperior.dslist.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public String register(UserDTO request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return "Usuário registrado com sucesso!";
    }

    public AuthResponseUserDTO login(UserDTO request) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        // Retorna o DTO de resposta com token e username
        return new AuthResponseUserDTO(token, userDetails.getUsername());
    }

}