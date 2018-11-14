package com.minicursospring.minicursospring.repository;

import com.minicursospring.minicursospring.models.Convidado;
import com.minicursospring.minicursospring.models.Evento;
import org.springframework.data.repository.CrudRepository;

public interface ConvidadoRepository extends CrudRepository<Convidado, Long> {

    Iterable<Convidado> findByEvento(Evento evento);
}
