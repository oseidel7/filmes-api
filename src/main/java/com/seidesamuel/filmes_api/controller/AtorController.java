package com.seidesamuel.filmes_api.controller;

import com.seidesamuel.filmes_api.dto.AtorRequestDTO;
import com.seidesamuel.filmes_api.dto.AtorResponseDTO;
import com.seidesamuel.filmes_api.service.AtorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atores")
public class AtorController {

    private final AtorService atorService;

    public AtorController(AtorService atorService) {
        this.atorService = atorService;
    }

    @GetMapping
    public List<AtorResponseDTO> listarAtores() {
        return atorService.listarAtores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtorResponseDTO> buscarAtor(@PathVariable Long id) {
        AtorResponseDTO ator = atorService.listarPorId(id);

        return ResponseEntity.ok(ator);
    }

    @PostMapping
    public ResponseEntity<AtorResponseDTO> adicionarAtor(@Valid
                                                           @RequestBody AtorRequestDTO dto) {

        AtorResponseDTO novoAtor = atorService.adicionarAtor(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoAtor);
    }
}