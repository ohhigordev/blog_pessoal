package com.OhhigorDev.meublog.blog_pessoal.Service;

import com.OhhigorDev.meublog.blog_pessoal.Model.DTO.Comentario.ComentarioReponseDTO;
import com.OhhigorDev.meublog.blog_pessoal.Model.DTO.Comentario.ComentarioRequestDTO;
import com.OhhigorDev.meublog.blog_pessoal.Exception.ResourceNotFoundException;
import com.OhhigorDev.meublog.blog_pessoal.Model.Comentario;
import com.OhhigorDev.meublog.blog_pessoal.Model.Post;
import com.OhhigorDev.meublog.blog_pessoal.repository.ComentarioRepository;
import com.OhhigorDev.meublog.blog_pessoal.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final PostRepository postRepository;

    // Criação de comentario
    @Transactional
    public ComentarioReponseDTO criarComentario(ComentarioRequestDTO comentarioRequestDTO){
        Post post = postRepository.findById(comentarioRequestDTO.postId())
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com o ID: " + comentarioRequestDTO.postId()));

        Comentario comentario = new Comentario();
        comentario.setConteudo(comentarioRequestDTO.conteudo());
        comentario.setAutorComentario(comentarioRequestDTO.autorComentario());
        comentario.setDataComentario(LocalDateTime.now());
        comentario.setPost(post);

        Comentario comentarioSalvo = comentarioRepository.save(comentario);
        return new ComentarioReponseDTO(comentarioSalvo);

    }

    @Transactional
    public List<ComentarioReponseDTO> listaComentariosPorPost(UUID postId){
        return comentarioRepository.findByPostId(postId).stream()
                .map(ComentarioReponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletarComentario(UUID id){
        if(!comentarioRepository.existsById(id)){
            throw new ResourceNotFoundException("Comentário não encontrado com o ID: " + id);
        }
        comentarioRepository.deleteById(id);
    }
}
