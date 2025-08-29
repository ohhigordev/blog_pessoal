package com.OhhigorDev.meublog.blog_pessoal.Controller.DTO.Comentario;

import com.OhhigorDev.meublog.blog_pessoal.Model.Comentario;

import java.time.LocalDateTime;
import java.util.UUID;

public record ComentarioReponseDTO(
        UUID id,
        String conteudo,
        String autorComentario,
        LocalDateTime dataComentario,
        UUID postId
) {
    public ComentarioReponseDTO(Comentario comentario){
        this(
                comentario.getId(),
                comentario.getConteudo(),
                comentario.getAutorComentario(),
                comentario.getDataComentario(),
                comentario.getPost().getId()
        );
    }
}
