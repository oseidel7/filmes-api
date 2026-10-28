package com.seidesamuel.filmes_api.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDTO {

    @NotBlank(message = "Nome da categoria não pode ser vazio")
    private String nome;

    public CategoriaRequestDTO() {

    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}