package com.seidesamuel.filmes_api.controller;

import com.seidesamuel.filmes_api.dto.CategoriaRequestDTO;
import com.seidesamuel.filmes_api.dto.CategoriaResponseDTO;
import com.seidesamuel.filmes_api.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {

        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaResponseDTO> listarCategoria() {

        return categoriaService.listarCategorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarCategorias(@PathVariable Long id) {

        CategoriaResponseDTO categoria = categoriaService.listarPorId(id);

        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> adicionarCategoria(@Valid
                                                                   @RequestBody CategoriaRequestDTO dto) {

        CategoriaResponseDTO novaCategoria = categoriaService.adicionarCategoria(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }
}