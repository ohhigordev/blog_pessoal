package com.OhhigorDev.meublog.blog_pessoal.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name= "post")
@Getter
@Setter
@ToString(exclude = {"autor"})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name="titulo", nullable = false)
    private String titulo;

    @Lob // Usado para armazenar textos longos, como de um post
    @Column(name="conteudo", nullable = false)
    private String conteudo;

    @Column(name="data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;
}
