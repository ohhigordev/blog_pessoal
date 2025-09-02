package com.OhhigorDev.meublog.blog_pessoal.Repository;

import com.OhhigorDev.meublog.blog_pessoal.Controller.DTO.Autor.AutorRequestDTO;
import com.OhhigorDev.meublog.blog_pessoal.Controller.DTO.Autor.AutorResponseDTO;
import com.OhhigorDev.meublog.blog_pessoal.Exception.ResourceNotFoundException;
import com.OhhigorDev.meublog.blog_pessoal.Model.Autor;
import com.OhhigorDev.meublog.blog_pessoal.Service.AutorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

public class AutorServiceTest {

    @Mock
    private AutorRepository repository;

    @InjectMocks
    private AutorService autorService;

    @Test
    void deveCriarAutorComSucesso() {
        // Given (Dado)
        // 1. DTO de entrada com os dados que o cliente enviaria.
        AutorRequestDTO requestDTO = new AutorRequestDTO("Higor", "higor@email.com");

        // 2. Criamos a entidade que o mock do repositório "simularia" o retorno.
        Autor autorSalvo = new Autor();
        autorSalvo.setId(UUID.randomUUID());
        autorSalvo.setNome(requestDTO.nome());
        autorSalvo.setEmail(requestDTO.email());
        autorSalvo.setDataCadastro(LocalDateTime.now());

        // 3. Configura o comportamento do mock do repositório.
        // Quando o método `save` for chamado com qualquer objeto Autor, ele deve retornar o `autorSalvo`.
        when(repository.save(any(Autor.class))).thenReturn(autorSalvo);

        // When (Quando)
        // Chamamos o método que queremos testar.
        AutorResponseDTO resultado = autorService.criarAutor(requestDTO);

        // Then (Então)
        // Verificamos se o resultado da chamada é o esperado.
        assertNotNull(resultado);
        assertNotNull(resultado.id());
        assertEquals(requestDTO.nome(), resultado.nome());
        assertEquals(requestDTO.email(), resultado.email());
        assertNotNull(resultado.dataCadastro());

        // Verificamos se o método `save` do repositório foi chamado exatamente uma vez.
        verify(repository, times(1)).save(any(Autor.class));
    }

    @Test
    void deveListarTodosAutoresComSucesso() {
        //Given
        Autor autor1 = new Autor();
        autor1.setId(UUID.randomUUID());
        autor1.setNome("Autor A");
        autor1.setEmail("autorA@gmail.com");

        Autor autor2 = new Autor();
        autor2.setId(UUID.randomUUID());
        autor2.setNome("Autor B");
        autor2.setEmail("autorB@gmail.com");

        // Vamos agora implementar a lista desse autores e sua chamada no repository
        List<Autor> autores = List.of(autor1, autor2);
        when(repository.findAll()).thenReturn(autores);

        //When
        List<AutorResponseDTO> resultado = autorService.listarTodosAutores();

        //Then
        assertNotNull(resultado);
        assertTrue(!resultado.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoTiverAutores(){
        //Given
        when(repository.findAll()).thenReturn(Collections.emptyList());

        //When
        List<AutorResponseDTO> resultado = autorService.listarTodosAutores();

        //Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveEncontrarAutorPorIdComSucesso(){
        UUID id = UUID.randomUUID();
        Autor autor = new Autor();
        autor.setId(id);
        autor.setNome("Higor");
        autor.setEmail("higor@gmail.com");

        when(repository.findById(id)).thenReturn(Optional.of(autor));

        //When
        AutorResponseDTO resultado = autorService.encontrarAutorPorId(id);

        //Then
        assertNotNull(resultado);
        assertEquals(id, resultado.id());
        assertEquals("Higor", resultado.nome());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void deveLancarExcecaoAoEncontrarAutorInexistente() {
        // Given
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> autorService.encontrarAutorPorId(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void  deveAtualizarAutorComSucesso(){
        //Given
        UUID id = UUID.randomUUID();
        Autor autorExistente = new Autor();
        autorExistente.setId(id);
        autorExistente.setNome("Nome Antigo");
        autorExistente.setEmail("antigo@email.com");

        AutorRequestDTO autorAtualizadoDto = new AutorRequestDTO("Nome novo", "novo@email.com");

        when(repository.findById(id)).thenReturn(Optional.of(autorExistente));
        when(repository.save(any(Autor.class))).thenAnswer(invocation -> {
            Autor autor = invocation.getArgument(0);
            return autor;
        });

        //When
        AutorResponseDTO resultado = autorService.atualizarAutor(id, autorAtualizadoDto);

        //Then
        assertNotNull(resultado);
        assertEquals("Nome novo", resultado.nome());
        assertEquals("novo@email.com", resultado.email());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(autorExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarAutorInexistente(){
        UUID id = UUID.randomUUID();
        AutorRequestDTO autorAtualizadoDto = new AutorRequestDTO("Nome novo", "novo@email.com");
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> autorService.atualizarAutor(id, autorAtualizadoDto));
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any(Autor.class));
    }

    @Test
    void deveDeletarAutorComSucesso() {
        // Given
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        // When
        autorService.deletarAutor(id);

        // Then
        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void deveLancarExcecaoAoDeletarAutorInexistente() {
        // Given
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> autorService.deletarAutor(id));
        verify(repository, times(1)).existsById(id);
        verify(repository, never()).deleteById(id);
    }
}