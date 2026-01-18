package com.elotech.elobooks.repository;

import com.elotech.elobooks.entity.Emprestimo;
import com.elotech.elobooks.enums.StatusEmprestimo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
	
	boolean existsByStatusAndLivroId(StatusEmprestimo status, Long idLivro);
	
	void deleteByLivroId(Long idLivro);
	
	void deleteAllByUsuarioId(Long idUsuario);
	
	List<Emprestimo> findAllByUsuarioId(Long idUsuario);

}
