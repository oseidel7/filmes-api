package com.seidesamuel.filmes_api.dto;


import java.util.List;

public class FilmeResponseDTO {

    private Long id;
    private String titulo;
    private String diretor;
    private Long categoriaId;
    private String categoriaNome;
    private int ano;
    private List<AtorResponseDTO> atores;

    public FilmeResponseDTO() {

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return this.diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNome() {
        return this.categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public int getAno() {
        return this.ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public List<AtorResponseDTO> getAtores() {
        return this.atores;
    }

    public void setAtores(List<AtorResponseDTO> atores) {
        this.atores = atores;
    }
}