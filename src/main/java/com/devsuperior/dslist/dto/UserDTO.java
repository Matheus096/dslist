package com.devsuperior.dslist.dto;

import com.devsuperior.dslist.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String username;
    private String password;

    public UserDTO(User usuario) {
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
    }
}