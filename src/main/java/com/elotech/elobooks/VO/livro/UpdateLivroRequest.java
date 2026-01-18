package com.elotech.elobooks.VO.livro;

import java.time.LocalDateTime;

import com.elotech.elobooks.enums.Categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateLivroRequest(
		@NotBlank(message = "Título é obrigatório")
		String titulo,

		@NotBlank(message = "Autor é obrigatório")
		String autor,

		@NotNull(message = "ISBN é obrigatório")
		Integer isbn,

		@NotNull(message = "Data de publicação é obrigatória")
		LocalDateTime dataPublicacao,

		@NotNull(message = "Categoria é obrigatória")
		Categoria categoria
) {}
