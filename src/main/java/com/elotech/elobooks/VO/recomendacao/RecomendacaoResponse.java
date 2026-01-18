package com.elotech.elobooks.VO.recomendacao;

import java.util.List;

import com.elotech.elobooks.entity.Livro;

public record RecomendacaoResponse(
        List<Livro> livros
) {}
