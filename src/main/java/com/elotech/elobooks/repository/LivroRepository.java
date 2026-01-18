package com.elotech.elobooks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elotech.elobooks.entity.Livro;
import com.elotech.elobooks.enums.Categoria;

public interface LivroRepository extends JpaRepository<Livro, Long> {
	
	boolean existsByIsbn(Integer isbn);
	
	List<Livro> findAllByIdIn(List<Long> idsLivrosEmprestados);
	
	List<Livro> findAllByCategoriaInAndIdNotIn(List<Categoria> categorias, List<Long> idsLivrosEmprestados);

}
