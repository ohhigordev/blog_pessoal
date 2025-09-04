package com.OhhigorDev.meublog.blog_pessoal.Model.DTO.Autor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AutorRequestDTO(
    UUID id,
   @NotBlank(message = "Campo Obrigatório")
   @Size(min= 4, max = 100, message = "Campo fora do tamanho padrão")
   String nome,
   @NotBlank(message = "Campo Obrigatório")
   @Email(message = "Formato de e-mail inválido")
   String email
) {}
