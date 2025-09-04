package com.OhhigorDev.meublog.blog_pessoal.repository;



import com.OhhigorDev.meublog.blog_pessoal.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {


}
