package com.elotech.elobooks.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elotech.elobooks.VO.emprestimo.CreateEmprestimoRequest;
import com.elotech.elobooks.VO.emprestimo.EmprestimoResponse;
import com.elotech.elobooks.VO.emprestimo.UpdateEmprestimoRequest;
import com.elotech.elobooks.entity.Emprestimo;
import com.elotech.elobooks.service.EmprestimoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/emprestimos")
@Tag(name = "Empréstimos", description = "Gerenciamento de empréstimo de livros")
public class EmprestimoController {
	private final EmprestimoService service;

	public EmprestimoController(EmprestimoService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EmprestimoResponse create(@Valid @RequestBody CreateEmprestimoRequest request) {
		Emprestimo emprestimo = service.create(request.idUsuario(), request.idLivro());
		return toResponse(emprestimo);
	}

	@PutMapping("/{id}")
	public EmprestimoResponse update(@PathVariable("id") Long id, @Valid @RequestBody UpdateEmprestimoRequest request) {
		Emprestimo emprestimo = service.update(id, request.idUsuario(), request.idLivro());
		return toResponse(emprestimo);
	}

	@GetMapping
	public List<EmprestimoResponse> findAll() {
		return service.findAll().stream().map(this::toResponse).toList();
	}

	@GetMapping("/{id}")
	public EmprestimoResponse findById(@PathVariable("id") Long id) {
		return toResponse(service.findById(id));
	}

	private EmprestimoResponse toResponse(Emprestimo emprestimo) {
		return new EmprestimoResponse(emprestimo.getId(), emprestimo.getUsuario(), emprestimo.getLivro(), emprestimo.getDataEmprestimo(),
				emprestimo.getDataDevolucao(), emprestimo.getStatus());
	}
	
}
