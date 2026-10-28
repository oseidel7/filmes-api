package com.seidesamuel.filmes_api.dto;

public class FilmeResumoDTO {

    private Long id;
    private String titulo;
    private int ano;

    public FilmeResumoDTO() {

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

    public int getAno() {
        return this.ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}