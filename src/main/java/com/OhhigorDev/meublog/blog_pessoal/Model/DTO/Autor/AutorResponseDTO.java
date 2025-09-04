package com.OhhigorDev.meublog.blog_pessoal.Model.DTO.Autor;

import com.OhhigorDev.meublog.blog_pessoal.Model.Autor;


import java.time.LocalDateTime;
import java.util.UUID;

public record AutorResponseDTO(
        // Adicionando apenas os dados que queremos da classe autor
         UUID id,
         String nome,
         String email,
         LocalDateTime dataCadastro

){
    public AutorResponseDTO(Autor autor){
        this(
                autor.getId(),
                autor.getNome(),
                autor.getEmail(),
                autor.getDataCadastro()
        );
    }
}

