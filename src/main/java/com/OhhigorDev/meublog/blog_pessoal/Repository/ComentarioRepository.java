package com.OhhigorDev.meublog.blog_pessoal.Repository;

import com.OhhigorDev.meublog.blog_pessoal.Model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ComentarioRepository extends JpaRepository <Comentario, UUID >{

    // Exemplo de Query Method: encontrar todos os comentários de um post especifico
    List<Comentario> findByPostId(UUID postId);
}
