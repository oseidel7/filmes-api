package com.seidesamuel.filmes_api.service;

import com.seidesamuel.filmes_api.dto.CategoriaRequestDTO;
import com.seidesamuel.filmes_api.dto.CategoriaResponseDTO;
import com.seidesamuel.filmes_api.dto.FilmeResumoDTO;
import com.seidesamuel.filmes_api.exceptions.CategoriaNaoEncontradaException;
import com.seidesamuel.filmes_api.model.Categoria;
import com.seidesamuel.filmes_api.model.Filme;
import com.seidesamuel.filmes_api.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {

        this.categoriaRepository = categoriaRepository;
    }

    private Categoria converterParaEntidade(CategoriaRequestDTO dto) {

        Categoria categoria = new Categoria();

        categoria.setNome(dto.getNome());

        return categoria;
    }

    private CategoriaResponseDTO converterParaDTO(Categoria categoria) {

        CategoriaResponseDTO dto = new CategoriaResponseDTO();

        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());

        List<FilmeResumoDTO> filmesDTO = new ArrayList<>();

        for(Filme filme : categoria.getFilmes()) {

            FilmeResumoDTO filmeNovo = new FilmeResumoDTO();

            filmeNovo.setId(filme.getId());
            filmeNovo.setTitulo(filme.getTitulo());
            filmeNovo.setAno(filme.getAno());
            filmesDTO.add(filmeNovo);
        }
        dto.setFilmes(filmesDTO);

        return dto;
    }

    private Categoria buscarPorId(Long id) {

        return categoriaRepository.findById(id).orElseThrow(
            () -> new CategoriaNaoEncontradaException("Categoria não encontrada")
        );
    }
    public CategoriaResponseDTO adicionarCategoria(CategoriaRequestDTO dto) {

        Categoria categoria = converterParaEntidade(dto);

        Categoria categoriaNova = categoriaRepository.save(categoria);

        return converterParaDTO(categoriaNova);
    }

    public CategoriaResponseDTO listarPorId(Long id) {

        Categoria categoriaListada = buscarPorId(id);

        return converterParaDTO(categoriaListada);
    }

    public List<CategoriaResponseDTO> listarCategorias() {

        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaResponseDTO> dtos = new ArrayList<>();

        for (Categoria categoria : categorias) {

            dtos.add(converterParaDTO(categoria));
        }
        return dtos;
    }
}
