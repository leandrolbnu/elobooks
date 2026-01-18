package com.elotech.elobooks.VO.emprestimo;

import java.time.LocalDateTime;

import com.elotech.elobooks.entity.Livro;
import com.elotech.elobooks.entity.Usuario;
import com.elotech.elobooks.enums.StatusEmprestimo;

public record EmprestimoResponse(
        Long id,
        Usuario usuario,
        Livro livro,
        LocalDateTime dataEmprestimo,
        LocalDateTime dataDevolucao,
        StatusEmprestimo status
) {}
