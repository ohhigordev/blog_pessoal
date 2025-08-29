package com.OhhigorDev.meublog.blog_pessoal.Controller.DTO.Post;

import com.OhhigorDev.meublog.blog_pessoal.Model.Post;

import java.time.LocalDate;
import java.util.UUID;

public record PostResponseDTO(
        UUID id,
        String titulo,
        String conteudo,
        LocalDate dataPublicacao,
        String nomeAutor,
        UUID autorId
) {
    public PostResponseDTO(Post post){
        this(
                post.getId(),
                post.getTitulo(),
                post.getConteudo(),
                post.getDataPublicacao(),
                post.getAutor().getNome(),
                post.getAutor().getId()
        );
    }
}
