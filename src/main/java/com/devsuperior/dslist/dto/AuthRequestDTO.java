package com.devsuperior.dslist.dto;

import com.devsuperior.dslist.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {

    private String username;
    private String password;

    public AuthRequestDTO(User usuario) {
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
    }
}