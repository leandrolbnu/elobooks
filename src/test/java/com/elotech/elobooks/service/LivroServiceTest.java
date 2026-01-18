package com.elotech.elobooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

import com.elotech.elobooks.entity.Livro;
import com.elotech.elobooks.enums.Categoria;
import com.elotech.elobooks.exception.DuplicateResourceException;
import com.elotech.elobooks.exception.NotFoundException;
import com.elotech.elobooks.repository.EmprestimoRepository;
import com.elotech.elobooks.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository repository;
    
    @Mock
    private EmprestimoRepository emprestimoRepository;
    
    @InjectMocks
    private LivroService service;

    private Livro livro;

    @BeforeEach
    void setUp() {
        livro = new Livro(
                1L,
                "Nome Livro Teste",
                "Autor Teste",
                1234,
                LocalDateTime.now(),
                Categoria.TERROR
        );
    }

    @Test
    void deveCriarLivro() {
        when(repository.existsByIsbn(livro.getIsbn())).thenReturn(false);
        when(repository.save(any(Livro.class))).thenReturn(livro);

        Livro resultado = service.create(livro.getTitulo(), livro.getAutor(), livro.getIsbn(), livro.getDataPublicacao(), livro.getCategoria());

        assertNotNull(resultado);
        assertEquals(livro.getTitulo(), resultado.getTitulo());
        assertEquals(livro.getAutor(), resultado.getAutor());
        assertEquals(livro.getIsbn(), resultado.getIsbn());
        assertEquals(livro.getDataPublicacao(), resultado.getDataPublicacao());
        assertEquals(livro.getCategoria(), resultado.getCategoria());

        verify(repository).existsByIsbn(livro.getIsbn());
        verify(repository).save(any(Livro.class));
        verifyNoMoreInteractions(repository);
    }

    @Test 
    void deveLancarExceptionQuandoIsbnJaExistir() {
        when(repository.existsByIsbn(livro.getIsbn())).thenReturn(true);

        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> service.create(livro.getTitulo(), livro.getAutor(), livro.getIsbn(), livro.getDataPublicacao(), livro.getCategoria())
        );

        assertEquals("Livro já cadastrado.", exception.getMessage());

        verify(repository).existsByIsbn(livro.getIsbn());
        verifyNoMoreInteractions(repository);
    }

    @Test 
    void deveRetornarTodosLivros() {
        when(repository.findAll()).thenReturn(List.of(livro));

        List<Livro> livros = service.findAll();

        assertEquals(1, livros.size());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }
    
    @Test
    void deveBuscarLivrosPorListaDeIds() {
        List<Long> idsLivrosEmprestados = List.of(1L, 2L, 3L);
        when(repository.findAllByIdIn(idsLivrosEmprestados)).thenReturn(List.of(livro));

        List<Livro> resultado = service.findAllByIds(idsLivrosEmprestados);

        assertNotNull(resultado);
        assertEquals(1L, resultado.size());
        assertEquals(livro.getId(), resultado.get(0).getId());

        verify(repository).findAllByIdIn(idsLivrosEmprestados);
        verifyNoMoreInteractions(repository);
    }
    
    @Test
    void deveBuscarLivrosPorCategoria() {
        List<Categoria> categorias = List.of(Categoria.FANTASIA,Categoria.TERROR);
        List<Long> idsLivrosEmprestados = List.of(1L, 2L, 3L);

        when(repository.findAllByCategoriaInAndIdNotIn(categorias, idsLivrosEmprestados)).thenReturn(List.of(livro));
        List<Livro> resultado = service.findAllByCategoria(categorias, idsLivrosEmprestados);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(livro.getId(), resultado.get(0).getId());
        assertTrue(categorias.contains(resultado.get(0).getCategoria()));

        verify(repository).findAllByCategoriaInAndIdNotIn(categorias, idsLivrosEmprestados);
        verifyNoMoreInteractions(repository);
    }


    @Test 
    void deveEncontrarLivroPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(livro));

        Livro resultado = service.findById(1L);

        assertEquals(livro.getId(), resultado.getId());

        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test 
    void deveRetornarNotFoundQuandoLivroNaoExistir() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.findById(1L)
        );
        
        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test 
    void deveAtualizarLivro() {
        when(repository.findById(1L)).thenReturn(Optional.of(livro));
        when(repository.save(any(Livro.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        LocalDateTime dataPublicacao = LocalDateTime.parse("2023-09-12T14:30:00");
        Livro livroAtualizado = service.update(1L, "Nome Livro Teste", "Autor Teste", 1234, dataPublicacao, Categoria.AVENTURA);

        assertEquals("Nome Livro Teste", livroAtualizado.getTitulo());
        assertEquals("Autor Teste", livroAtualizado.getAutor());
        assertEquals(1234, livroAtualizado.getIsbn());
        assertEquals(dataPublicacao, livroAtualizado.getDataPublicacao());
        assertEquals(Categoria.AVENTURA, livroAtualizado.getCategoria());

        verify(repository).findById(1L);
        verify(repository).save(any(Livro.class));
        verifyNoMoreInteractions(repository);
    }
    
    @Test
    void deveDeletarLivro() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(emprestimoRepository).deleteByLivroId(1L);
        doNothing().when(repository).deleteById(1L);
        
        service.delete(1L);

        verify(repository).existsById(1L);
        verify(emprestimoRepository).deleteByLivroId(1L);
        verify(repository).deleteById(1L);
        verifyNoMoreInteractions(repository, emprestimoRepository);
    }

    @Test 
    void deveLancarExceptionQuandoDeletarLivroNaoExistente() {
        when(repository.existsById(1L)).thenReturn(false);
        
        assertThrows(
                NotFoundException.class,
                () -> service.delete(1L)
        );

        verify(repository).existsById(1L);
        verifyNoMoreInteractions(repository, emprestimoRepository);
    }
}
