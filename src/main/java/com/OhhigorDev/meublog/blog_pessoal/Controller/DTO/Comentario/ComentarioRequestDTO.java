package com.OhhigorDev.meublog.blog_pessoal.Controller.DTO.Comentario;

import java.util.UUID;

public record ComentarioRequestDTO(
        String conteudo,
        String autorComentario,
        UUID postId
) {}
