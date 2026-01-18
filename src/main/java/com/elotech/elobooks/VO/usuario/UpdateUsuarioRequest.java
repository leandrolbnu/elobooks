package com.elotech.elobooks.VO.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateUsuarioRequest(
        @NotBlank(message = "Nome é obrigatório") 
        String nome,
        
        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,
        
        @Pattern(regexp = "^(\\(?\\d{2}\\)?\\s?)?(9?\\d{4})-?\\d{4}$",
        		message = "Telefone inválido. Digite um telefone válido com DDD.") 
        @NotBlank(message = "Telefone é obrigatório")
        String telefone
) {}
