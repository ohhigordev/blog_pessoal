package com.OhhigorDev.meublog.blog_pessoal.Model.DTO.Post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record PostRequestDTO(
   @NotBlank(message = "O título é obrigatório")
   @Size(min = 5, max = 255, message = "O título deve ter entre 5 e 255 caracteres")
   String titulo,

   @NotBlank(message = "O conteúdo é obrigatório")
   String conteudo,

   @NotNull(message = "A data de publicação é obrigatória")
   LocalDate dataPublicacao,

   @NotNull(message = "O ID do autor é obrigatória")
   UUID autorId
) {}
