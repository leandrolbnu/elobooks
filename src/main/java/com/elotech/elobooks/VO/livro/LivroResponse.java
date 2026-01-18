package com.elotech.elobooks.VO.livro;

import java.time.LocalDateTime;

import com.elotech.elobooks.enums.Categoria;

public record LivroResponse(
        Long id,
        String titulo,
        String autor,
        Integer isbn,
        LocalDateTime dataPublicacao,
        Categoria categoria
) {}
