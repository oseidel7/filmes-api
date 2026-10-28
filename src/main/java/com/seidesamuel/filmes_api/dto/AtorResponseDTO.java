package com.seidesamuel.filmes_api.dto;


import java.util.List;

public class AtorResponseDTO {

    private Long id;
    private String nome;
    private List<FilmeResumoDTO> filmes;

    public AtorResponseDTO() {

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<FilmeResumoDTO> getFilmes() {
        return this.filmes;
    }

    public void setFilmes(List<FilmeResumoDTO> filmes) {
        this.filmes = filmes;
    }

}