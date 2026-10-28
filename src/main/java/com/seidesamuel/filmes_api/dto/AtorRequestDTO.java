package com.seidesamuel.filmes_api.dto;

import jakarta.validation.constraints.NotBlank;

public class AtorRequestDTO {

    @NotBlank(message = "Nome do ator não pode ser vazio")
    private String nome;

    public AtorRequestDTO() {

    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}