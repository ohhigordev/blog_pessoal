package com.OhhigorDev.meublog.blog_pessoal.Model.DTO.Comentario;

import java.util.UUID;

public record ComentarioRequestDTO(
        String conteudo,
        String autorComentario,
        UUID postId
) {}
