package com.elotech.elobooks.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.elotech.elobooks.entity.Emprestimo;
import com.elotech.elobooks.entity.Livro;
import com.elotech.elobooks.enums.Categoria;

@Service
public class RecomendacaoService {

    private final LivroService livroService;
    
    private final EmprestimoService emprestimoService;

    public RecomendacaoService(LivroService livroService, EmprestimoService emprestimoService) {
        this.livroService = livroService;
        this.emprestimoService = emprestimoService;
    }

    public List<Livro> findAllByUsuarioId(Long id){
    	List<Emprestimo> emprestimos = emprestimoService.findAllByUsuarioId(id);
    	
    	List<Long> idsLivrosEmprestados = emprestimos.stream()
    	        .map(e -> e.getLivro().getId())
    	        .distinct()
    	        .toList();

    	List<Livro> livrosJaEmprestados = livroService.findAllByIds(idsLivrosEmprestados);
    	
    	List<Categoria> categorias = livrosJaEmprestados.stream()
    	        .map(Livro::getCategoria)
    	        .distinct()
    	        .toList();
    	 	
    	List<Livro> livrosRecomendados = livroService.findAllByCategoria(categorias, idsLivrosEmprestados);
    	
    	return livrosRecomendados;
    }
}
