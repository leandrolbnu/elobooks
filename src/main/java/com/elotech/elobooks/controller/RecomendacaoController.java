package com.elotech.elobooks.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elotech.elobooks.VO.recomendacao.RecomendacaoResponse;
import com.elotech.elobooks.entity.Livro;
import com.elotech.elobooks.service.RecomendacaoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/recomendacao")
@Tag(name = "Recomendação", description = "Recomendação de livros")
public class RecomendacaoController {
	private final RecomendacaoService service;
	
	public RecomendacaoController(RecomendacaoService service) {
		this.service = service;
	}
	
	@GetMapping("/{id}")
	public RecomendacaoResponse findById(@PathVariable("id") Long id) {
		return toResponse(service.findAllById(id));	
	}

	private RecomendacaoResponse toResponse(List<Livro> livros) {
		return new RecomendacaoResponse(livros);
	}
	
}