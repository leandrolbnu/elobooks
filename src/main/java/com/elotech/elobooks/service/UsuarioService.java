package com.elotech.elobooks.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.elotech.elobooks.entity.Usuario;
import com.elotech.elobooks.exception.DuplicateResourceException;
import com.elotech.elobooks.exception.NotFoundException;
import com.elotech.elobooks.repository.EmprestimoRepository;
import com.elotech.elobooks.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final EmprestimoRepository emprestimoRepository;

    public UsuarioService(UsuarioRepository repository, EmprestimoRepository emprestimoRepository) {
        this.repository = repository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public Usuario create(String nome, String email, String telefone) {
        if (repository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email já cadastrado.");
        }

        Usuario usuario = new Usuario(nome, email, telefone, LocalDateTime.now());
        return repository.save(usuario);
    }

    public Usuario update(Long id, String nome, String email, String telefone) {
        Usuario usuario = findById(id);
        Usuario usuarioAtualizado = new Usuario(usuario.getId(), nome, email, telefone, usuario.getDataCadastro());

        return repository.save(usuarioAtualizado);
    }
   
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Usuario findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Usuário", id));
    }
    
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Usuário", id);
        }
        
        emprestimoRepository.deleteAllByUsuarioId(id);
        repository.deleteById(id);
    }
    
}
