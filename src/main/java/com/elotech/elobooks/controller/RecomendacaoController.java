package com.elotech.elobooks.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elotech.elobooks.VO.livro.LivroResponse;
import com.elotech.elobooks.entity.Livro;
import com.elotech.elobooks.service.RecomendacaoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/recomendacoes")
@Tag(name = "Recomendações", description = "Recomendação de livros")
public class RecomendacaoController {
	private final RecomendacaoService service;
	
	public RecomendacaoController(RecomendacaoService service) {
		this.service = service;
	}
	
	@GetMapping("/{id}")
	public List<LivroResponse> findAllByUsuarioId(@PathVariable("id") Long id) {
		return service.findAllByUsuarioId(id).stream().map(this::toResponse).toList();
	}
	
	private LivroResponse toResponse(Livro livro) {
		return new LivroResponse(livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getIsbn(),
				livro.getDataPublicacao(), livro.getCategoria());
	}
}