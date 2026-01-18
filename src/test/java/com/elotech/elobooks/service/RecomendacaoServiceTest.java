package com.elotech.elobooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.elotech.elobooks.entity.Emprestimo;
import com.elotech.elobooks.entity.Livro;
import com.elotech.elobooks.entity.Usuario;
import com.elotech.elobooks.enums.Categoria;
import com.elotech.elobooks.enums.StatusEmprestimo;
import com.elotech.elobooks.repository.EmprestimoRepository;
import com.elotech.elobooks.repository.LivroRepository;
import com.elotech.elobooks.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class RecomendacaoServiceTest {

    @Mock
    private EmprestimoRepository repository;
    
    @Mock
    private UsuarioRepository usuarioRepository;
    
    @Mock
    private LivroRepository livroRepository;

    @Mock
    private UsuarioService usuarioService;
    
    @Mock
    private LivroService livroService;
    
    @Mock
    private EmprestimoService emprestimoService;
    
    @InjectMocks
    private RecomendacaoService service;

    private Emprestimo emprestimo;
    
    private Usuario usuario;
    
    private Livro livro;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(
                1L,
                "Nome Teste",
                "nome.teste@email.com",
                "47988887777",
                LocalDateTime.now()
        );
        
        livro = new Livro(
                1L,
                "Nome Livro Teste",
                "Autor Teste",
                "1234",
                LocalDateTime.now(),
                Categoria.TERROR
        );
        
    	emprestimo = new Emprestimo(
                1L,
                usuario,
                livro,
                LocalDateTime.now(),
                LocalDateTime.now(),
                StatusEmprestimo.EMPRESTADO
        );
    }

    @Test
    void deveBuscarListaDeRecomendacoes() {
    	when(emprestimoService.findAllByUsuarioId(usuario.getId())).thenReturn(List.of(emprestimo));
    	List<Long> idsLivrosEmprestados = List.of(livro.getId());
    	
    	when(livroService.findAllByIds(idsLivrosEmprestados)).thenReturn(List.of(livro));
    	List<Categoria> categorias = List.of(livro.getCategoria());
    	
    	when(livroService.findAllByCategoria(categorias, idsLivrosEmprestados)).thenReturn(List.of(livro));

        List<Livro> resultado = service.findAllByUsuarioId(usuario.getId());

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(livro.getId(), resultado.get(0).getId());

        verify(emprestimoService).findAllByUsuarioId(usuario.getId());
        verify(livroService).findAllByIds(idsLivrosEmprestados);
        verify(livroService).findAllByCategoria(categorias, idsLivrosEmprestados);
      
        verifyNoMoreInteractions(emprestimoService);
        verifyNoMoreInteractions(livroService);
        verifyNoMoreInteractions(repository);
    }

}
