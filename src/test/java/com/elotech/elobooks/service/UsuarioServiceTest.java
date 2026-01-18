package com.elotech.elobooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.elotech.elobooks.entity.Usuario;
import com.elotech.elobooks.exception.DuplicateResourceException;
import com.elotech.elobooks.exception.NotFoundException;
import com.elotech.elobooks.repository.EmprestimoRepository;
import com.elotech.elobooks.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private EmprestimoRepository emprestimoRepository;
    
    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(
                1L,
                "Nome Teste",
                "nome.teste@email.com",
                "47988887777",
                LocalDateTime.now()
        );
    }

    @Test
    void deveCriarUsuario() {
        when(repository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = service.create(usuario.getNome(), usuario.getEmail(), usuario.getTelefone());

        assertNotNull(resultado);
        assertEquals(usuario.getNome(), resultado.getNome());
        assertEquals(usuario.getEmail(), resultado.getEmail());
        assertEquals(usuario.getTelefone(), resultado.getTelefone());

        verify(repository).existsByEmail(usuario.getEmail());
        verify(repository).save(any(Usuario.class));
        verifyNoMoreInteractions(repository);
    }

    @Test 
    void deveLancarExceptionQuandoEmailJaExistir() {
        when(repository.existsByEmail(usuario.getEmail())).thenReturn(true);

        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> service.create(usuario.getNome(), usuario.getEmail(), usuario.getTelefone())
        );

        assertEquals("Email já cadastrado.", exception.getMessage());

        verify(repository).existsByEmail(usuario.getEmail());
        verifyNoMoreInteractions(repository);
    }

    @Test 
    void deveRetornarTodosUsuarios() {
        when(repository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> usuarios = service.findAll();

        assertEquals(1, usuarios.size());

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test 
    void deveEncontrarUsuarioPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = service.findById(1L);

        assertEquals(usuario.getId(), resultado.getId());

        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test 
    void deveRetornarNotFoundQuandoUsuarioNaoExistir() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.findById(1L)
        );
        
        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveAtualizarUsuario() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario usuarioAtualizado = service.update(1L, "Nome Teste", "nome.teste@email.com", "47988886666");

        assertEquals("Nome Teste", usuarioAtualizado.getNome());
        assertEquals("nome.teste@email.com", usuarioAtualizado.getEmail());
        assertEquals("47988886666", usuarioAtualizado.getTelefone());
        assertEquals(usuario.getDataCadastro(), usuarioAtualizado.getDataCadastro());

        verify(repository).findById(1L);
        verify(repository).save(any(Usuario.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveDeletarUsuario() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(emprestimoRepository).deleteAllByUsuarioId(1L);
        doNothing().when(repository).deleteById(1L);
        
        service.delete(1L);

        verify(repository).existsById(1L);
        verify(emprestimoRepository).deleteAllByUsuarioId(1L);
        verify(repository).deleteById(1L);
        verifyNoMoreInteractions(repository, emprestimoRepository);
    }

    @Test 
    void deveLancarExceptionQuandoDeletarUsuarioNaoExistente() {
        when(repository.existsById(1L)).thenReturn(false);
        
        assertThrows(
                NotFoundException.class,
                () -> service.delete(1L)
        );

        verify(repository).existsById(1L);
        verifyNoMoreInteractions(repository, emprestimoRepository);
    }
}
