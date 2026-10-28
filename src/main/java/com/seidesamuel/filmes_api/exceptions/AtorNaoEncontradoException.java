package com.seidesamuel.filmes_api.exceptions;

public class AtorNaoEncontradoException extends RuntimeException {
    public AtorNaoEncontradoException(String message) {
        super(message);
    }
}
