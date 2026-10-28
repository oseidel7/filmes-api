package com.seidesamuel.filmes_api.exceptions;

public class FilmeJaExisteException extends RuntimeException {
    public FilmeJaExisteException(String mensagem) {
        super(mensagem);
    }
}
