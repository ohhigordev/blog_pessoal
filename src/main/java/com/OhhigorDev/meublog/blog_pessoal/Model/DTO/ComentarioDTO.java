package com.OhhigorDev.meublog.blog_pessoal.Model.DTO;

import com.OhhigorDev.meublog.blog_pessoal.Model.Comentario;

import java.time.LocalDateTime;
import java.util.UUID;

public record ComentarioDTO(
        UUID id,
        String conteudo,
        String autorComentario,
        LocalDateTime dataComentario,
        UUID postId
) {
    public ComentarioDTO(Comentario comentario){
        this(
                comentario.getId(),
                comentario.getConteudo(),
                comentario.getAutorComentario(),
                comentario.getDataComentario(),
                comentario.getPost().getId()
        );
    }
}
