package com.OhhigorDev.meublog.blog_pessoal.Controller;

import com.OhhigorDev.meublog.blog_pessoal.Model.DTO.Autor.AutorRequestDTO;
import com.OhhigorDev.meublog.blog_pessoal.Model.DTO.Autor.AutorResponseDTO;
import com.OhhigorDev.meublog.blog_pessoal.Service.AutorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
@AllArgsConstructor
public class AutorController {

    private final AutorService autorService;

    //http://localhost:8080/autores
    @PostMapping
    public ResponseEntity<AutorResponseDTO> criarAutor(@RequestBody @Valid AutorRequestDTO autorRequestDTO){
        AutorResponseDTO novoAutor = autorService.criarAutor(autorRequestDTO);
        return new ResponseEntity<>(novoAutor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> listarTodosAutores(){
        List<AutorResponseDTO> autores = autorService.listarTodosAutores();
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> encontrarAutorPorId(@PathVariable UUID id){
        try{
            AutorResponseDTO autor = autorService.encontrarAutorPorId(id);
            return ResponseEntity.ok(autor);
        }catch (ResourceAccessException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> atualizarAutor(@Valid @PathVariable UUID id, @RequestBody AutorRequestDTO autorRequestDTO){
        try{
            AutorResponseDTO autorAtualizado = autorService.atualizarAutor(id, autorRequestDTO);
            return ResponseEntity.ok(autorAtualizado);
        }catch (ResourceAccessException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable UUID id){
        try{
            autorService.deletarAutor(id);
            return ResponseEntity.noContent().build();
        }catch (ResourceAccessException e){
            return ResponseEntity.notFound().build();
        }
    }
}
