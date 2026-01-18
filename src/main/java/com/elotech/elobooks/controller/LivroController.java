package com.elotech.elobooks.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elotech.elobooks.VO.livro.CreateLivroRequest;
import com.elotech.elobooks.VO.livro.LivroResponse;
import com.elotech.elobooks.VO.livro.UpdateLivroRequest;
import com.elotech.elobooks.entity.Livro;
import com.elotech.elobooks.service.LivroService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/livros")
@Tag(name = "Livros", description = "Gerenciamento de livros")
public class LivroController {
	private final LivroService service;

	public LivroController(LivroService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LivroResponse create(@Valid @RequestBody CreateLivroRequest request) {
		Livro livro = service.create(request.titulo(), request.autor(), request.isbn(), service.parseDate(request.dataPublicacao()), request.categoria());
		return toResponse(livro);
	}

	@PutMapping("/{id}")
	public LivroResponse update(@PathVariable("id") Long id, @Valid @RequestBody UpdateLivroRequest request) {
		Livro livro = service.update(id, request.titulo(), request.autor(), request.isbn(), service.parseDate(request.dataPublicacao()), request.categoria());
		return toResponse(livro);
	}

	@GetMapping
	public List<LivroResponse> findAll() {
		return service.findAll().stream().map(this::toResponse).toList();
	}

	@GetMapping("/{id}")
	public LivroResponse findById(@PathVariable("id") Long id) {
		return toResponse(service.findById(id));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		service.delete(id);
	}
	
	private LivroResponse toResponse(Livro livro) {
		return new LivroResponse(livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getIsbn(),
				livro.getDataPublicacao(), livro.getCategoria());
	}

}
