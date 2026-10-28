package com.seidesamuel.filmes_api.controller;

import com.seidesamuel.filmes_api.dto.FilmeRequestDTO;
import com.seidesamuel.filmes_api.dto.FilmeResponseDTO;
import com.seidesamuel.filmes_api.service.FilmeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private final FilmeService filmeService;

    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @GetMapping
    public List<FilmeResponseDTO> listarFilmes() {

        return filmeService.listarFilmes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeResponseDTO> buscarFilme(@PathVariable Long id) {

        FilmeResponseDTO filme = filmeService.listarPorId(id);

        return ResponseEntity.ok(filme);

    }

    @PostMapping
    public ResponseEntity<FilmeResponseDTO> adicionarFilme(@Valid @RequestBody FilmeRequestDTO dto) {

        FilmeResponseDTO novoFilme = filmeService.adicionarFilme(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoFilme.getId())
                .toUri();

        return ResponseEntity.created(uri).body(novoFilme);

    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmeResponseDTO> atualizarFilme(@PathVariable Long id, @Valid @RequestBody FilmeRequestDTO dto) {

        FilmeResponseDTO filmeAtualizado = filmeService.atualizarFilme(id, dto);

        return ResponseEntity.ok(filmeAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFilme(@PathVariable Long id) {

        filmeService.removerFilme(id);

        return ResponseEntity.noContent().build();
    }
}
