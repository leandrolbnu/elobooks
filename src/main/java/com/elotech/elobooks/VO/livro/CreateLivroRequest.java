package com.elotech.elobooks.VO.livro;

import java.time.LocalDateTime;

import com.elotech.elobooks.enums.Categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateLivroRequest(
        @NotBlank String titulo,
        @NotBlank String autor,
        @NotNull Integer isbn, 
        @NotNull LocalDateTime dataPublicacao,
        @NotNull Categoria categoria
) {}


