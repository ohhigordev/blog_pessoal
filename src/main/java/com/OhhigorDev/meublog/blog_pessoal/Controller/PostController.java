package com.OhhigorDev.meublog.blog_pessoal.Controller;


import com.OhhigorDev.meublog.blog_pessoal.Exception.ResourceNotFoundException;
import com.OhhigorDev.meublog.blog_pessoal.Model.DTO.Post.PostRequestDTO;
import com.OhhigorDev.meublog.blog_pessoal.Model.DTO.Post.PostResponseDTO;
import com.OhhigorDev.meublog.blog_pessoal.Service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping
    public ResponseEntity<PostResponseDTO> criarPost(@Valid @RequestBody PostRequestDTO postRequestDTO){
        try{
            PostResponseDTO novoPost = postService.criarPost(postRequestDTO);
            return new ResponseEntity<>(novoPost, HttpStatus.CREATED);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> listarTodosPost(){
        List<PostResponseDTO> posts = postService.listarTodosPosts();
        return ResponseEntity.ok(posts);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> encontrarPostPorId(@PathVariable UUID id){
        try{
            PostResponseDTO post = postService.encontrarPostPorId(id);
            return ResponseEntity.ok(post);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> atualizarPost(@Valid @PathVariable UUID id,@RequestBody PostRequestDTO postRequestDTO ){
        try{
            PostResponseDTO postAtualizado = postService.atualizarPost(id, postRequestDTO);
            return ResponseEntity.ok(postAtualizado);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPost(@PathVariable UUID id){
        try{
            postService.deletarPost(id);
            return ResponseEntity.noContent().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
