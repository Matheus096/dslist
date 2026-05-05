package com.devsuperior.dslist.dto;

import com.devsuperior.dslist.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "O nome de usuário não pode estar em branco")
    @Size(min = 3, max = 16)
    private String username;
    
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String password;

    @Email(message = "Formato de e-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;

    public UserDTO(User usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
        this.email = usuario.getEmail();
    }
}