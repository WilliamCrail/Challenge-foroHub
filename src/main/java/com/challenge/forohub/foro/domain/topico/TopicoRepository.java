package com.challenge.forohub.foro.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<Topico,Long> {
    boolean existsByTitulo(String titulo);
    boolean existsByMensaje(String mensaje);

    Page<Topico> findByCurso(Curso curso, Pageable paginacion);

    @Query("SELECT t FROM Topico t WHERE YEAR(t.fechaDeCreacion) = :anio")
    Page<Topico> findByYear(@Param("anio") int anio, Pageable pageable);
}
