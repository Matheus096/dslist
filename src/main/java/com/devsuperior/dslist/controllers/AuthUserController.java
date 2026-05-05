package com.devsuperior.dslist.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dslist.dto.AuthResponseUserDTO;
import com.devsuperior.dslist.dto.UserDTO;
import com.devsuperior.dslist.services.AuthUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthUserService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO request) {
        String message = authService.register(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseUserDTO> login(@RequestBody UserDTO request) {
        AuthResponseUserDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}