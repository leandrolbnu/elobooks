package com.elotech.elobooks.VO.usuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        LocalDateTime dataCadastro
) {}
