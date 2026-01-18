package com.elotech.elobooks.VO.emprestimo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateEmprestimoRequest(
		@NotNull(message = "Id do usuário é obrigatório")
		@Positive(message = "Id do usuário deve ser um número positivo")
        Long idUsuario,
        
        @NotNull(message = "Id do livro é obrigatório")
        @Positive(message = "Id do livro deve ser um número positivo")
        Long idLivro
) {}
