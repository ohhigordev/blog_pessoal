package com.OhhigorDev.meublog.blog_pessoal.Repository;

import com.OhhigorDev.meublog.blog_pessoal.Controller.DTO.Autor.AutorResponseDTO;
import com.OhhigorDev.meublog.blog_pessoal.Model.Autor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class AutorServiceTest {

    @Mock
    private AutorRepository repository;

    @InjectMocks
    private AutorServiceTest autorService;

    @Test
    void deveCriarAutorComSucesso(){
        //Given (Dado)
        Autor autorParaSalvar = new Autor();
        autorParaSalvar.setNome("Higor");
        autorParaSalvar.setEmail("higor123@gmail.com");

        Autor autorSalvo = new Autor();
        autorSalvo.setId(UUID.randomUUID());
        autorSalvo.setNome("Higor");
        autorSalvo.setEmail("higor123@gmail.com");
        autorSalvo.setDataCadastro(LocalDateTime.now());

        // Quando o autorRepository.save() for chamado, retorne o autorSalvo
        when(repository.save(any(Autor.class))).thenReturn(autorSalvo);

        // When = Quando
        AutorResponseDTO resultado = autorService.criarAutor(autorSalvo);

        //Then = Então
        assertNotNull(resultado);
        assertEquals(autorSalvo.getId(), resultado.id());
        assertEquals("Higor", resultado.nome());
        assertNotNull(resultado.dataCadastro());

        //Verifique se o método save foi chamado uma vez no repositório
        verify(repository, times(1)).save(any(Autor.class));
    }
}
