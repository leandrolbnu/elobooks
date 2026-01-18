package com.elotech.elobooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import com.elotech.elobooks.exception.NotFoundException;
import com.elotech.elobooks.repository.EmprestimoRepository;
import com.elotech.elobooks.repository.LivroRepository;
import com.elotech.elobooks.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class EmprestimoServiceTest {

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
    
    @InjectMocks
    private EmprestimoService service;

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
    void deveCriarEmprestimo() {
        when(repository.existsByStatusAndLivroId(emprestimo.getStatus(), livro.getId())).thenReturn(false);
        when(usuarioService.findById(usuario.getId())).thenReturn(usuario);
        when(livroService.findById(livro.getId())).thenReturn(livro);
        when(repository.save(any(Emprestimo.class))).thenReturn(emprestimo);

        Emprestimo resultado = service.create(usuario.getId(), livro.getId());

        assertNotNull(resultado);
        assertEquals(emprestimo.getUsuario().getId(), resultado.getUsuario().getId());
        assertEquals(emprestimo.getLivro().getId(), resultado.getLivro().getId());
        assertEquals(StatusEmprestimo.EMPRESTADO, resultado.getStatus());

        verify(repository).existsByStatusAndLivroId(emprestimo.getStatus(), livro.getId());
        verify(usuarioService).findById(usuario.getId());
        verify(livroService).findById(livro.getId());
        verify(repository).save(any(Emprestimo.class));
       
        verifyNoMoreInteractions(usuarioService);
        verifyNoMoreInteractions(livroService);
        verifyNoMoreInteractions(repository);
    }
    
    @Test
    void deveAtualizarEmprestimo() {
        when(repository.findById(1L)).thenReturn(Optional.of(emprestimo));
        when(usuarioService.findById(usuario.getId())).thenReturn(usuario);
        when(livroService.findById(livro.getId())).thenReturn(livro);
        when(repository.save(any(Emprestimo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Emprestimo emprestimoAtualizado = service.update(1L, usuario.getId(), livro.getId());

        assertEquals(usuario.getId(), emprestimoAtualizado.getUsuario().getId());
        assertEquals(livro.getId(), emprestimoAtualizado.getLivro().getId());
        assertEquals(StatusEmprestimo.DEVOLVIDO, emprestimoAtualizado.getStatus());

        verify(repository).findById(1L);
        verify(usuarioService).findById(usuario.getId());
        verify(livroService).findById(livro.getId());
        verify(repository).save(any(Emprestimo.class));
        
        verifyNoMoreInteractions(usuarioService);
        verifyNoMoreInteractions(livroService);
        verifyNoMoreInteractions(repository);
    }

    @Test 
    void deveLancarExceptionQuandoLivroNaoEstiverDisponivel() {
        when(repository.existsByStatusAndLivroId(emprestimo.getStatus(), livro.getId())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.create(usuario.getId(), livro.getId())
        );

        assertEquals("Livro não disponível.", exception.getMessage());
       
        verify(repository).existsByStatusAndLivroId(emprestimo.getStatus(), livro.getId());
        
        verifyNoMoreInteractions(usuarioService);
        verifyNoMoreInteractions(livroService);
        verifyNoMoreInteractions(repository);
    }

    @Test 
    void deveRetornarTodosEmprestimos() {
        when(repository.findAll()).thenReturn(List.of(emprestimo));

        List<Emprestimo> emprestimos = service.findAll();

        assertEquals(1, emprestimos.size());
       
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test 
    void deveEncontrarEmprestimoPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(emprestimo));

        Emprestimo resultado = service.findById(1L);

        assertEquals(emprestimo.getId(), resultado.getId());
      
        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test 
    void deveRetornarNotFoundQuandoEmprestimoNaoExistir() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.findById(1L)
        );
        
        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }
    
    @Test
    void deveRetornarTodosEmprestimosPorUsuarioId() {
        when(repository.findAllByUsuarioId(usuario.getId())).thenReturn(List.of(emprestimo));

        List<Emprestimo> emprestimos = service.findAllByUsuarioId(usuario.getId());

        assertEquals(1, emprestimos.size());
       
        verify(repository).findAllByUsuarioId(usuario.getId());
        verifyNoMoreInteractions(repository);	
    }
    
    @Test
    void deveRetornarNotFoundQuandoNaoExistirEmprestimoPorUsuarioId() {
        when(repository.findAllByUsuarioId(usuario.getId())).thenReturn((List.of()));

        List<Emprestimo> resultado = service.findAllByUsuarioId(usuario.getId());

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(repository).findAllByUsuarioId(usuario.getId());
        verifyNoMoreInteractions(repository);	
    } 

}
