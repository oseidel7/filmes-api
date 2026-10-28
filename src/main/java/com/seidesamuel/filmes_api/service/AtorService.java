package com.seidesamuel.filmes_api.service;


import com.seidesamuel.filmes_api.dto.AtorRequestDTO;
import com.seidesamuel.filmes_api.dto.AtorResponseDTO;
import com.seidesamuel.filmes_api.dto.FilmeResumoDTO;
import com.seidesamuel.filmes_api.exceptions.AtorNaoEncontradoException;
import com.seidesamuel.filmes_api.model.Ator;
import com.seidesamuel.filmes_api.model.Filme;
import com.seidesamuel.filmes_api.repository.AtorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AtorService {

    private final AtorRepository atorRepository;

    public AtorService(AtorRepository atorRepository) {

        this.atorRepository = atorRepository;
    }

    private Ator converterParaEntidade(AtorRequestDTO dto) {

        Ator ator = new Ator();

        ator.setNome(dto.getNome());

        return ator;
    }

    private AtorResponseDTO converterParaDTO(Ator ator) {

        AtorResponseDTO dto = new AtorResponseDTO();

        dto.setId(ator.getId());
        dto.setNome(ator.getNome());

        List<FilmeResumoDTO> filmesDTO = new ArrayList<>();

        for (Filme filme : ator.getFilmes()) {

            FilmeResumoDTO filmeNovo = new FilmeResumoDTO();
            filmeNovo.setId(filme.getId());
            filmeNovo.setTitulo(filme.getTitulo());
            filmeNovo.setAno(filme.getAno());
            filmesDTO.add(filmeNovo);
        }
        dto.setFilmes(filmesDTO);

        return dto;
    }

    private Ator buscarPorId(Long id) {

        return atorRepository.findById(id).orElseThrow(
                () -> new AtorNaoEncontradoException("Ator não encontrado")
        );
    }

    public AtorResponseDTO adicionarAtor(AtorRequestDTO dto) {

        Ator ator = converterParaEntidade(dto);

        Ator atorNovo = atorRepository.save(ator);

        return converterParaDTO(atorNovo);

    }

    public AtorResponseDTO listarPorId(Long id) {

        Ator ator = buscarPorId(id);

        return converterParaDTO(ator);
    }

    public List<AtorResponseDTO> listarAtores() {

        List<Ator> atores = atorRepository.findAll();
        List<AtorResponseDTO> dtos = new ArrayList<>();

        for (Ator ator : atores) {
            dtos.add(converterParaDTO(ator));
        }
        return dtos;
    }
}