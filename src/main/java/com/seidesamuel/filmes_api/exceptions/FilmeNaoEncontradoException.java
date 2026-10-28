package com.seidesamuel.filmes_api.exceptions;

public class FilmeNaoEncontradoException extends RuntimeException {
    public FilmeNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}