package com.seidesamuel.filmes_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class FilmeRequestDTO {

    @NotBlank(message = "Título não pode ser vazio")
    private String titulo;

    @NotBlank(message = "Diretor não pode ser vazio")
    private String diretor;

    @NotNull(message = "Categoria é obrigatória")
    private Long categoriaId;

    @NotNull(message = "Ano é obrigatório")
    @Min(value = 1888, message = "Ano do filme é inválido")
    private Integer ano;

    @NotNull(message = "Atores são obrigatórios")
    private List<Long> atoresIds;

    public FilmeRequestDTO() {

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
        return this.categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Integer getAno() {
        return this.ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public List<Long> getAtoresIds() {
        return this.atoresIds;
    }

    public void setAtoresIds(List<Long> atoresIds) {
        this.atoresIds = atoresIds;
    }
}