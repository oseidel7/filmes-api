package com.seidesamuel.filmes_api.service;

import com.seidesamuel.filmes_api.dto.FilmeRequestDTO;
import com.seidesamuel.filmes_api.dto.FilmeResponseDTO;
import com.seidesamuel.filmes_api.exceptions.FilmeInvalidoException;
import com.seidesamuel.filmes_api.exceptions.FilmeJaExisteException;
import com.seidesamuel.filmes_api.exceptions.FilmeNaoEncontradoException;
import com.seidesamuel.filmes_api.model.Ator;
import com.seidesamuel.filmes_api.model.Categoria;
import com.seidesamuel.filmes_api.model.Filme;
import com.seidesamuel.filmes_api.repository.AtorRepository;
import com.seidesamuel.filmes_api.repository.CategoriaRepository;
import com.seidesamuel.filmes_api.repository.FilmeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FilmeServiceTest {

    @Mock
    private FilmeRepository filmeRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private AtorRepository atorRepository;

    @InjectMocks
    private FilmeService filmeService;

    @Test
    void listarPorId_quandoFilmeExiste_retornaFilmeResponseDTO() {

        //Arrange
        Long id = 1L;

        Categoria categoria = new Categoria();
        categoria.setId(2L);
        categoria.setNome("Ficção Científica");

        Filme filme = new Filme();
        filme.setTitulo("Interestelar");
        filme.setDiretor("Christopher Nolan");
        filme.setAno(2014);
        filme.setId(7L);
        filme.setCategoria(categoria);

        when(filmeRepository.findById(id)).thenReturn(Optional.of(filme));

        //Act
        FilmeResponseDTO resultado = filmeService.listarPorId(id);

        //Assert
        assertEquals("Interestelar", resultado.getTitulo());
        assertEquals(filme.getDiretor(), resultado.getDiretor());
        assertEquals(filme.getAno(), resultado.getAno());
        assertEquals(filme.getId(), resultado.getId());
        assertEquals(categoria.getNome(), resultado.getCategoriaNome());
        assertEquals(categoria.getId(), resultado.getCategoriaId());
        assertEquals(0, resultado.getAtores().size());

        //Verify
        verify(filmeRepository).findById(id);
    }

    @Test
    void listarPorId_quandoFilmeNaoExiste_lancaFilmeNaoEncontradoException() {

        Long id = 1L;

        when(filmeRepository.findById(id)).thenReturn(Optional.empty());

        FilmeNaoEncontradoException excecao = assertThrows(FilmeNaoEncontradoException.class, () -> filmeService.listarPorId(id));

        assertEquals("Filme não encontrado", excecao.getMessage());

        verify(filmeRepository).findById(id);
    }

    @Test
    void adicionarFilme_quandoAnoFuturo_lancaFilmeInvalidoException() {

        //Arrange

        Long idCategoria = 1L;
        Long idAtor = 2L;

        Categoria categoria = new Categoria();
        when(categoriaRepository.findById(idCategoria)).thenReturn(Optional.of(categoria));

        Ator ator = new Ator();
        when(atorRepository.findById(idAtor)).thenReturn(Optional.of(ator));

        FilmeRequestDTO filmeDTO = new FilmeRequestDTO();
        filmeDTO.setTitulo("Interestelar");
        filmeDTO.setDiretor("Christopher Nolan");
        filmeDTO.setCategoriaId(idCategoria);
        filmeDTO.setAtoresId(List.of(idAtor));
        filmeDTO.setAno(Year.now().getValue() + 15);

        FilmeInvalidoException excecao = assertThrows(FilmeInvalidoException.class, () -> filmeService.adicionarFilme(filmeDTO));
        assertEquals("Ano do filme não pode ser futuro", excecao.getMessage());

        //Verify
        verify(filmeRepository, never()).save(any());
    }

    @Test
    void adicionarFilme_quandoFilmeDuplicado_lancaFilmeJaExisteException() {

        //Arrange

        Long idAtor = 1L;
        Long idCategoria = 2L;

        Categoria categoria = new Categoria();
        when(categoriaRepository.findById(idCategoria)).thenReturn(Optional.of(categoria));

        Ator ator = new Ator();
        when(atorRepository.findById(idAtor)).thenReturn(Optional.of(ator));

        FilmeRequestDTO filmeDTO = new FilmeRequestDTO();
        filmeDTO.setTitulo("Interestelar");
        filmeDTO.setDiretor("Christopher Nolan");
        filmeDTO.setCategoriaId(idCategoria);
        filmeDTO.setAtoresId(List.of(idAtor));
        filmeDTO.setAno(2013);
        when(filmeRepository.existsByTituloAndDiretor(filmeDTO.getTitulo(), filmeDTO.getDiretor())).thenReturn(true);

        FilmeJaExisteException excecao = assertThrows(FilmeJaExisteException.class, () -> filmeService.adicionarFilme(filmeDTO));
        assertEquals("Filme já cadastrado", excecao.getMessage());

        verify(filmeRepository, never()).save(any());
    }

    @Test
    void adicionarFilme_quandoDadosValidos_retornaFilmeResponseDTO() {

        //Arrange

        Long idAtor = 2L;
        Long idCategoria = 3L;

        Categoria categoria = new Categoria();
        categoria.setNome("Ficção Cientifica");
        categoria.setId(idCategoria);
        when(categoriaRepository.findById(idCategoria)).thenReturn(Optional.of(categoria));

        Ator ator = new Ator();
        ator.setNome("Matthew McConaughey");
        ator.setId(idAtor);
        when(atorRepository.findById(idAtor)).thenReturn(Optional.of(ator));

        Filme filme = new Filme();
        filme.setId(1L);
        filme.setTitulo("Interestelar");
        filme.setDiretor("Christopher Nolan");
        filme.setAno(2013);
        filme.setCategoria(categoria);
        filme.setAtores(List.of(ator));
        when(filmeRepository.existsByTituloAndDiretor(filme.getTitulo(), filme.getDiretor())).thenReturn(false);

        FilmeRequestDTO filmeDTO = new FilmeRequestDTO();
        filmeDTO.setTitulo(filme.getTitulo());
        filmeDTO.setDiretor(filme.getDiretor());
        filmeDTO.setAno(filme.getAno());
        filmeDTO.setAtoresId(List.of(idAtor));
        filmeDTO.setCategoriaId(idCategoria);
        when(filmeRepository.save(any(Filme.class))).thenReturn(filme);

        //Act
        FilmeResponseDTO resultado = filmeService.adicionarFilme(filmeDTO);

        //Assert
        assertEquals(1L, resultado.getId());
        assertEquals(filmeDTO.getAtoresIds().get(0), resultado.getAtores().get(0).getId());
        assertEquals(filmeDTO.getCategoriaId(), resultado.getCategoriaId());
        assertEquals("Interestelar", resultado.getTitulo());
        assertEquals(filmeDTO.getDiretor(), resultado.getDiretor());
        assertEquals(filmeDTO.getAno(), resultado.getAno());

        //verify
        ArgumentCaptor<Filme> captor = ArgumentCaptor.forClass(Filme.class);

        verify(filmeRepository).save(captor.capture());
        Filme capturado = captor.getValue();
        //asserts do capturado
        assertEquals(filmeDTO.getTitulo(), capturado.getTitulo());
        assertEquals(filmeDTO.getDiretor(), capturado.getDiretor());
        assertEquals(filmeDTO.getAno(), capturado.getAno());
        assertEquals(filmeDTO.getCategoriaId(), capturado.getCategoria().getId());
        assertEquals(filmeDTO.getAtoresIds().get(0), capturado.getAtores().get(0).getId());

        verify(filmeRepository).existsByTituloAndDiretor(filme.getTitulo(), filme.getDiretor());

    }
}