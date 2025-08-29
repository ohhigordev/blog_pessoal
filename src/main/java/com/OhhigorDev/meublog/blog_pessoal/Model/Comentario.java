package com.OhhigorDev.meublog.blog_pessoal.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comentario")
@Getter
@Setter
@ToString(exclude = {"post"})
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Lob
    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    @Column(name = "autor_comentario", nullable = false)
    private String autorComentario;

    @Column(name = "data_comentario", updatable = false)
    private LocalDateTime dataComentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
