package com.seidesamuel.filmes_api.exceptions;

public class FilmeInvalidoException extends RuntimeException {
    public FilmeInvalidoException(String mensagem) {
        super(mensagem);
    }
}
