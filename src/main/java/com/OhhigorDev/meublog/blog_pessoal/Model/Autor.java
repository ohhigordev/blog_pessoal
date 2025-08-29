package com.OhhigorDev.meublog.blog_pessoal.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="autor")
@Data
@ToString
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String nome;

    // unique: garante que o e-mail cadastrado seja único.
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // updatable: garante que a data de cadastro não seja alterada após sua criação.
    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro;
}
