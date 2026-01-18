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

import com.elotech.elobooks.VO.usuario.CreateUsuarioRequest;
import com.elotech.elobooks.VO.usuario.UpdateUsuarioRequest;
import com.elotech.elobooks.VO.usuario.UsuarioResponse;
import com.elotech.elobooks.entity.Usuario;
import com.elotech.elobooks.service.UsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Gerenciamento de usuários")
public class UsuarioController {
	private final UsuarioService service;

	public UsuarioController(UsuarioService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioResponse create(@Valid @RequestBody CreateUsuarioRequest request) {
		Usuario usuario = service.create(request.nome(), request.email(), request.telefone());
		return toResponse(usuario);
	}

	@PutMapping("/{id}")
	public UsuarioResponse update(@PathVariable("id") Long id, @Valid @RequestBody UpdateUsuarioRequest request) {
		Usuario usuario = service.update(id, request.nome(), request.email(), request.telefone());
		return toResponse(usuario);
	}

	@GetMapping
	public List<UsuarioResponse> findAll() {
		return service.findAll().stream().map(this::toResponse).toList();
	}

	@GetMapping("/{id}")
	public UsuarioResponse findById(@PathVariable("id") Long id) {
		return toResponse(service.findById(id));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		service.delete(id);
	}

	private UsuarioResponse toResponse(Usuario usuario) {
		return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone(),
				usuario.getDataCadastro());
	}

}
