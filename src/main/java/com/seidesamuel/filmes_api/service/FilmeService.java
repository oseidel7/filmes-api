package com.seidesamuel.filmes_api.service;

import com.seidesamuel.filmes_api.dto.AtorResponseDTO;
import com.seidesamuel.filmes_api.dto.FilmeRequestDTO;
import com.seidesamuel.filmes_api.dto.FilmeResponseDTO;
import com.seidesamuel.filmes_api.exceptions.*;
import com.seidesamuel.filmes_api.model.Ator;
import com.seidesamuel.filmes_api.model.Categoria;
import com.seidesamuel.filmes_api.model.Filme;
import com.seidesamuel.filmes_api.repository.AtorRepository;
import com.seidesamuel.filmes_api.repository.CategoriaRepository;
import com.seidesamuel.filmes_api.repository.FilmeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;


@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final CategoriaRepository categoriaRepository;
    private final AtorRepository atorRepository;

    public FilmeService(FilmeRepository filmeRepository,
                        CategoriaRepository categoriaRepository,
                        AtorRepository atorRepository) {

        this.filmeRepository = filmeRepository;
        this.categoriaRepository = categoriaRepository;
        this.atorRepository = atorRepository;

    }

    private Filme converterParaEntidade(FilmeRequestDTO dto) {

        Filme filme = new Filme();

        filme.setTitulo(dto.getTitulo());
        filme.setDiretor(dto.getDiretor());
        filme.setAno(dto.getAno());

        Categoria categoria = buscarCategoriaPorId(dto.getCategoriaId());
        filme.setCategoria(categoria);

        List<Ator> atores = buscarAtoresPorIds(dto.getAtoresIds());
        filme.setAtores(atores);

        return filme;
    }

    private FilmeResponseDTO converterParaDTO(Filme filme) {

        FilmeResponseDTO dto = new FilmeResponseDTO();

        dto.setId(filme.getId());
        dto.setTitulo(filme.getTitulo());
        dto.setDiretor(filme.getDiretor());
        dto.setAno(filme.getAno());

        dto.setCategoriaId(filme.getCategoria().getId());
        dto.setCategoriaNome(filme.getCategoria().getNome());

        List<AtorResponseDTO> atoresDTOS = new ArrayList<>();
        for (Ator ator : filme.getAtores()) {

            AtorResponseDTO atorDTO = new AtorResponseDTO();
            atorDTO.setId(ator.getId());
            atorDTO.setNome(ator.getNome());
            atoresDTOS.add(atorDTO);

        }
        dto.setAtores(atoresDTOS);
        return dto;

    }

    private Filme buscarPorId(Long id) {

        return filmeRepository.findById(id).orElseThrow(
            () -> new FilmeNaoEncontradoException("Filme não encontrado")
        );

    }

    private Categoria buscarCategoriaPorId(Long id) {

        return categoriaRepository.findById(id).orElseThrow(
            () -> new CategoriaNaoEncontradaException("Categoria não encontrada")
        );
    }

    private Ator buscarAtorPorId(Long id) {

        return atorRepository.findById(id).orElseThrow(
            () -> new AtorNaoEncontradoException("Ator não encontrado")
        );
    }

    private void validarAnoFilme(Filme filme) {

        if(filme.getAno() > Year.now().getValue()) {
            throw new FilmeInvalidoException("Ano do filme não pode ser futuro");
        }
    }

    private List<Ator> buscarAtoresPorIds(List<Long> ids) {

        List<Ator> atores = new ArrayList<>();

        for (Long id : ids) {

            Ator ator = buscarAtorPorId(id);
            atores.add(ator);
        }
        return atores;
    }

    @Transactional(readOnly = true)
    public List<FilmeResponseDTO> listarFilmes() {

       List<Filme> filmes = filmeRepository.findAll();
       List<FilmeResponseDTO> dtos = new ArrayList<>();

       for (Filme filme : filmes) {

           dtos.add(converterParaDTO(filme));
       }
       return dtos;
    }

    @Transactional(readOnly = true)
    public FilmeResponseDTO listarPorId(Long id) {

        Filme filmeListado = buscarPorId(id);

        return converterParaDTO(filmeListado);
    }

    @Transactional
    public FilmeResponseDTO adicionarFilme(FilmeRequestDTO dto) {

        Filme filme = converterParaEntidade(dto);

        validarAnoFilme(filme);

        if (filmeRepository.existsByTituloAndDiretor(filme.getTitulo(), filme.getDiretor())) {

            throw new FilmeJaExisteException("Filme já cadastrado");
        }
        Filme filmeSalvo = filmeRepository.save(filme);

        return converterParaDTO(filmeSalvo);
    }

    @Transactional
    public FilmeResponseDTO atualizarFilme(Long id, FilmeRequestDTO dto) {

        Filme filmeExistente = buscarPorId(id);

        Filme novosDados = converterParaEntidade(dto);

        validarAnoFilme(novosDados);

        if(filmeRepository.existsByTituloAndDiretorAndIdNot
                (novosDados.getTitulo(),
                 novosDados.getDiretor(),
                 id)) {

            throw new FilmeJaExisteException("Filme já cadastrado");
        }

        filmeExistente.setTitulo(novosDados.getTitulo());
        filmeExistente.setDiretor(novosDados.getDiretor());
        filmeExistente.setCategoria(novosDados.getCategoria());
        filmeExistente.setAno(novosDados.getAno());
        filmeExistente.setAtores(novosDados.getAtores());


        return converterParaDTO(filmeExistente);

    }

    @Transactional
    public void removerFilme(Long id) {

        Filme filme = buscarPorId(id);

        filmeRepository.delete(filme);

    }
}