package com.OhhigorDev.meublog.blog_pessoal.Controller.DTO.Post;

import java.time.LocalDate;
import java.util.UUID;

public record PostRequestDTO(
   String titulo,
   String conteudo,
   LocalDate dataPublicacao,
   UUID autorId
) {}
