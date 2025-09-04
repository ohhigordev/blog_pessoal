package com.OhhigorDev.meublog.blog_pessoal.repository;

import com.OhhigorDev.meublog.blog_pessoal.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository  extends JpaRepository<Post, UUID> {

    // Exemplo de Query Method: encontrar post por título
    List<Post> findByTituloContainingIgnoreCase(String titulo);

    // Exemplo de Query Method: encontrar um autor especifico
    List<Post> findByAutorId(UUID autor);
}
