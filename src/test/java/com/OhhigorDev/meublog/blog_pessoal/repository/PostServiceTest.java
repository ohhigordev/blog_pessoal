package com.OhhigorDev.meublog.blog_pessoal.repository;


import com.OhhigorDev.meublog.blog_pessoal.Model.DTO.Post.PostRequestDTO;
import com.OhhigorDev.meublog.blog_pessoal.Model.DTO.Post.PostResponseDTO;
import com.OhhigorDev.meublog.blog_pessoal.Exception.ResourceNotFoundException;
import com.OhhigorDev.meublog.blog_pessoal.Model.Autor;
import com.OhhigorDev.meublog.blog_pessoal.Model.Post;
import com.OhhigorDev.meublog.blog_pessoal.Service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void deveCriarPostComSucesso(){
        //Given
        UUID autorId = UUID.randomUUID();
        Autor autor = new Autor();
        autor.setId(autorId);

        PostRequestDTO requestDTO = new PostRequestDTO("Titulo do Post","Conteudo do post de teste", LocalDate.of(2025,9,1), autorId);

        when(autorRepository.findById(autorId)).thenReturn(Optional.of(autor));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            post.setId(UUID.randomUUID());
            post.setAutor(autor);
            return post;
        });

        //When
        PostResponseDTO responseDTO = postService.criarPost(requestDTO);

        //Then
        assertNotNull(responseDTO.id());
        assertEquals("Titulo do Post", responseDTO.titulo());
        assertEquals("Conteudo do post de teste", responseDTO.conteudo());
        verify(autorRepository, times(1)).findById(autorId);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void deveLancarExcecaoAoCriarPostComAutorInexistente(){
        UUID autorId = UUID.randomUUID();
        PostRequestDTO requestDTO = new PostRequestDTO("Titulo ","Conteudo", LocalDate.of(2025,9,1), autorId);

        when(autorRepository.findById(autorId)).thenReturn(Optional.empty());

        //When & Then
        assertThrows(ResourceNotFoundException.class, () -> postService.criarPost(requestDTO));
        verify(autorRepository, times(1)).findById(autorId);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void deveListarTodosPostComSucesso(){
        Autor autor = new Autor();
        autor.setId(UUID.randomUUID());
        autor.setNome("Higor Dev");

        Post post1 = new Post();
        post1.setId(UUID.randomUUID());
        post1.setTitulo("Post 01");
        post1.setAutor(autor);

        Post post2 = new Post();
        post2.setId(UUID.randomUUID());
        post2.setTitulo("Post 02");
        post2.setAutor(autor);

        // Criando a listagem de post
        List<Post> posts = List.of(post1, post2);
        when(postRepository.findAll()).thenReturn(posts);

        //When
        List<PostResponseDTO> resultado = postService.listarTodosPosts();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Post 01", resultado.get(0).titulo());
        assertEquals("Post 02", resultado.get(1).titulo());
        verify(postRepository, times(1)).findAll();

    }

    @Test
    void deveEncontrarPostPorIdComSucesso(){
        UUID postId = UUID.randomUUID();
        Autor autor = new Autor();
        autor.setId(UUID.randomUUID());
        autor.setNome("Higor Dev");

        Post post = new Post();
        post.setId(postId);
        post.setTitulo("Post Encontrado");
        post.setAutor(autor);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        //When
        PostResponseDTO resultado = postService.encontrarPostPorId(postId);

        //Then
        assertNotNull(resultado);
        assertEquals(postId, resultado.id());
        assertEquals("Post Encontrado", resultado.titulo());
        assertEquals("Higor Dev", resultado.nomeAutor());
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void deveLancarExcecaoAoEncontrarPostInexistente(){
        //Given
        UUID id = UUID.randomUUID();
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        //When & Then
        assertThrows(ResourceNotFoundException.class, () -> postService.encontrarPostPorId(id));
        verify(postRepository, times(1)).findById(id);
    }

    @Test
    void deveAtualizarPostComSucesso(){
        //Given
        UUID postId = UUID.randomUUID();
        UUID autorIdExistente = UUID.randomUUID();
        Autor autorExistente = new Autor();
        autorExistente.setId(autorIdExistente);
        autorExistente.setNome("Autor original");

        Post postExistente = new Post();
        postExistente.setId(postId);
        postExistente.setTitulo("Titulo antigo");
        postExistente.setConteudo("Conteudo antigo");
        postExistente.setAutor(autorExistente);

        PostRequestDTO requestDTO = new PostRequestDTO("Titulo novo", "Conteudo novo", LocalDate.of(2025,9,2), autorIdExistente);

        when(postRepository.findById(postId)).thenReturn(Optional.of(postExistente));
        when(postRepository.save(any(Post.class))).thenReturn(postExistente);

        //When
        PostResponseDTO resultado = postService.atualizarPost(postId, requestDTO);

        //Then
        assertNotNull(resultado);
        assertEquals("Titulo novo", resultado.titulo());
        assertEquals("Conteudo novo", resultado.conteudo());
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository,times(1)).save(postExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarPostInexistente(){
        UUID id = UUID.randomUUID();
        PostRequestDTO requestDTO = new PostRequestDTO("Titulo novo", "Conteudo novo", LocalDate.now(), UUID.randomUUID());
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        //When & Then
        assertThrows(ResourceNotFoundException.class, () -> postService.atualizarPost(id, requestDTO));
        verify(postRepository, times(1)).findById(id);
        verify(postRepository,never()).save(any(Post.class));
    }

    @Test
    void deveDeletarPostComSucesso() {
        // Given
        UUID id = UUID.randomUUID();
        when(postRepository.existsById(id)).thenReturn(true);

        // When
        postService.deletarPost(id);

        // Then
        verify(postRepository, times(1)).existsById(id);
        verify(postRepository, times(1)).deleteById(id);
    }

    @Test
    void deveLancarExcecaoAoDeletarPostInexistente() {
        // Given
        UUID id = UUID.randomUUID();
        when(postRepository.existsById(id)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> postService.deletarPost(id));
        verify(postRepository, times(1)).existsById(id);
        verify(postRepository, never()).deleteById(id);
    }

}
