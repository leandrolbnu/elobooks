package com.elotech.elobooks.VO.emprestimo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateEmprestimoRequest(
        @NotNull @Positive Long idUsuario,
        @NotNull @Positive Long idLivro
) {}
