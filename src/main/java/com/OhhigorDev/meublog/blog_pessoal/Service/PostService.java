package com.OhhigorDev.meublog.blog_pessoal.Service;

import com.OhhigorDev.meublog.blog_pessoal.Controller.DTO.Post.PostRequestDTO;
import com.OhhigorDev.meublog.blog_pessoal.Controller.DTO.Post.PostResponseDTO;
import com.OhhigorDev.meublog.blog_pessoal.Exception.ResourceNotFoundException;
import com.OhhigorDev.meublog.blog_pessoal.Model.Autor;
import com.OhhigorDev.meublog.blog_pessoal.Model.Post;
import com.OhhigorDev.meublog.blog_pessoal.Repository.AutorRepository;
import com.OhhigorDev.meublog.blog_pessoal.Repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final AutorRepository autorRepository;


    public PostService(PostRepository postRepository, AutorRepository autorRepository) {
        this.postRepository = postRepository;
        this.autorRepository = autorRepository;
    }

    @Transactional
    public PostResponseDTO criarPost(PostRequestDTO postRequestDTO){
        Autor autor = autorRepository.findById(postRequestDTO.autorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com ID: " + id));

        Post post = new Post();
        post.setTitulo(postRequestDTO.titulo());
        post.setConteudo(postRequestDTO.conteudo());
        post.setDataPublicacao(postRequestDTO.dataPublicacao());
        post.setAutor(autor);

        Post postSalvo = postRepository.save(post);
        return new PostResponseDTO(postSalvo);
    }

    @Transactional
    public List<PostResponseDTO> listarTodosPosts(){
        return postRepository.findAll().stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDTO encontrarPostPorId(UUID id){
        return postRepository.findById(id)
                .map(PostResponseDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com esse id: " + id));
    }

    @Transactional
    public PostResponseDTO atualizarPost(UUID id, PostRequestDTO postRequestDTO){
        return postRepository.findById(id)
                .map(postExistente -> {
                    postExistente.setTitulo(postRequestDTO.titulo());
                    postExistente.setConteudo(postRequestDTO.conteudo());
                    postExistente.setDataPublicacao(postRequestDTO.dataPublicacao());

                    //Opcional: permitir a mudança de autor
                    if (!postExistente.getAutor().getId().equals(postRequestDTO.autorId())) {
                        Autor novoAutor = autorRepository.findById(postRequestDTO.autorId())
                                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com o ID: " + postRequestDTO.autorId()));
                        postExistente.setAutor(novoAutor);
                    }

                    Post postAtualizado = postRepository.save(postExistente);
                    return new PostResponseDTO((postAtualizado));

                })
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com o ID: " + id));
    }

    public void deletarPost(UUID id){
        if(!postRepository.existsById(id)){
            throw new ResourceNotFoundException("Post não encontrado com o ID: " + id);
        }
        postRepository.deleteById(id);
    }

}
