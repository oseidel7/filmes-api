package com.seidesamuel.filmes_api.repository;

import com.seidesamuel.filmes_api.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}