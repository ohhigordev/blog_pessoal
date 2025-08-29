package com.OhhigorDev.meublog.blog_pessoal.Service;



import com.OhhigorDev.meublog.blog_pessoal.Controller.DTO.Autor.AutorRequestDTO;
import com.OhhigorDev.meublog.blog_pessoal.Controller.DTO.Autor.AutorResponseDTO;
import com.OhhigorDev.meublog.blog_pessoal.Exception.ResourceNotFoundException;
import com.OhhigorDev.meublog.blog_pessoal.Model.Autor;
import com.OhhigorDev.meublog.blog_pessoal.Repository.AutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Transactional
    public AutorResponseDTO criarAutor(AutorRequestDTO autorRequestDTO){
        Autor autor = new Autor();
        autor.setNome(autorRequestDTO.nome());
        autor.setEmail(autorRequestDTO.email());
        autor.setDataCadastro(LocalDateTime.now());
        Autor autorSalvo = autorRepository.save(autor);
        return new AutorResponseDTO(autorSalvo);
    }

    @Transactional
    public List<AutorResponseDTO> listarTodosAutores(){
        return autorRepository.findAll().stream()
                .map(AutorResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AutorResponseDTO encontrarAutorPorId(UUID id){
        return autorRepository.findById(id)
                .map(AutorResponseDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("O autor não pode ser encontrado"));
    }

    @Transactional
    public AutorResponseDTO atualizarAutor(UUID id, AutorRequestDTO autorRequestDTO){
        return autorRepository.findById(id)
                .map(autorExistente -> {
                    autorExistente.setNome(autorRequestDTO.nome());
                    autorExistente.setEmail(autorRequestDTO.email());
                    Autor autorAtualizado = autorRepository.save(autorExistente);
                    return new AutorResponseDTO(autorAtualizado);
                }).orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com o id: " + id));
    }

    public void deletarAutor(UUID id){
        if(!autorRepository.existsById(id)){
            throw new ResourceNotFoundException("Autor não encontrado como id: " + id);
        }
        autorRepository.deleteById(id);
    }



}
