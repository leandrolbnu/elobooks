package com.elotech.elobooks.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.elotech.elobooks.entity.Livro;
import com.elotech.elobooks.enums.Categoria;
import com.elotech.elobooks.exception.DuplicateResourceException;
import com.elotech.elobooks.exception.NotFoundException;
import com.elotech.elobooks.repository.EmprestimoRepository;
import com.elotech.elobooks.repository.LivroRepository;

import jakarta.transaction.Transactional;

@Service
public class LivroService {

    private final LivroRepository repository;
    private final EmprestimoRepository emprestimoRepository;

    public LivroService(LivroRepository repository, EmprestimoRepository emprestimoRepository) {
        this.repository = repository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public Livro create(String titulo, String autor, Integer isbn, LocalDateTime dataPublicacao, Categoria categoria) {
        if (repository.existsByIsbn(isbn)) {
            throw new DuplicateResourceException("Livro já cadastrado.");
        }
        
        Livro livro = new Livro(titulo, autor, isbn, dataPublicacao, categoria);
        return repository.save(livro);
    }
    
    public Livro update(Long id, String titulo, String autor, Integer isbn, LocalDateTime dataPublicacao, Categoria categoria) {
    	Livro livro = findById(id);
    	Livro livroAtualizado = new Livro(livro.getId(), titulo, autor, isbn, dataPublicacao, categoria);

        return repository.save(livroAtualizado);
    }

    public List<Livro> findAll() {
        return repository.findAll();
    }
    
    public List<Livro> findAllByIds(List<Long> idsLivrosEmprestados) {
        return repository.findAllByIdIn(idsLivrosEmprestados);
    }
    
    public List<Livro> findAllByCategoria(List<Categoria> categorias, List<Long> idsLivrosEmprestados) {
        return repository.findAllByCategoriaInAndIdNotIn(categorias, idsLivrosEmprestados);
    }

    public Livro findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro", id));
    }
    
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Livro", id);
        }
        
        emprestimoRepository.deleteByLivroId(id);
        repository.deleteById(id);
    }

}
