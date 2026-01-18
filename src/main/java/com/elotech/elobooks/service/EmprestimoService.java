package com.elotech.elobooks.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.elotech.elobooks.entity.Emprestimo;
import com.elotech.elobooks.entity.Livro;
import com.elotech.elobooks.entity.Usuario;
import com.elotech.elobooks.enums.StatusEmprestimo;
import com.elotech.elobooks.exception.NotFoundException;
import com.elotech.elobooks.repository.EmprestimoRepository;

@Service
public class EmprestimoService {

    private final EmprestimoRepository repository;
    
    private final LivroService livroService;
    
    private final UsuarioService usuarioService;

    public EmprestimoService(EmprestimoRepository repository, LivroService livroService, UsuarioService usuarioService) {
        this.repository = repository;
        this.livroService = livroService;
        this.usuarioService = usuarioService;        
    }

    public Emprestimo create(Long idUsuario, Long idLivro) {
        if (repository.existsByStatusAndLivroId(StatusEmprestimo.EMPRESTADO, idLivro)) {
            throw new IllegalArgumentException("Livro não disponível.");
        }
        
        Usuario usuario = usuarioService.findById(idUsuario);
        Livro livro = livroService.findById(idLivro);        
        Emprestimo emprestimo = new Emprestimo(usuario, livro, LocalDateTime.now(), StatusEmprestimo.EMPRESTADO);
        
        return repository.save(emprestimo);
    }
    
    public Emprestimo update(Long id, Long idUsuario, Long idLivro) {
    	Emprestimo emprestimo = findById(id);
    	Usuario usuario = usuarioService.findById(idUsuario);
        Livro livro = livroService.findById(idLivro);
    	Emprestimo emprestimoAtualizado = new Emprestimo(emprestimo.getId(), usuario, livro, LocalDateTime.now(), emprestimo.getDataEmprestimo(), StatusEmprestimo.DEVOLVIDO);

        return repository.save(emprestimoAtualizado);
    }

    public Emprestimo findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Empréstimo", id));
    }

    public List<Emprestimo> findAll() {
        return repository.findAll();
    }
    
    public List<Emprestimo> findAllByUsuarioId(Long idUsuario) {
        return repository.findAllByUsuarioId(idUsuario);
    }
}
