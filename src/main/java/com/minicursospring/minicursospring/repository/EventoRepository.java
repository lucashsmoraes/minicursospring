package com.minicursospring.minicursospring.repository;

import com.minicursospring.minicursospring.models.Evento;
import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, String> {

    Evento findByCodigo(long codigo);

}
