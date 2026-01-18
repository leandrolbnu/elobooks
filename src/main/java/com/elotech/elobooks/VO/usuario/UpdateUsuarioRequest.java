package com.elotech.elobooks.VO.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateUsuarioRequest(
        @NotBlank String nome,
        @Email @NotBlank String email,
        @Pattern(regexp = "^(\\(?\\d{2}\\)?\\s?)?(9?\\d{4})-?\\d{4}$\r\n") @NotBlank String telefone
) {}
