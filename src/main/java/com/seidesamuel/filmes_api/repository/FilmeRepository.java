package com.seidesamuel.filmes_api.repository;

import com.seidesamuel.filmes_api.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    boolean existsByTituloAndDiretor(String titulo, String diretor);
    boolean existsByTituloAndDiretorAndIdNot(String titulo, String diretor, Long id);
}