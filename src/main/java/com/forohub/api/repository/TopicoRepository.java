package com.forohub.api.repository;

import com.forohub.api.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Método para validar duplicados
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);

    List<Topico> findTop10ByOrderByFechaCreacionAsc();

    @Query("SELECT t FROM Topico t WHERE t.curso = :curso AND YEAR(t.fechaCreacion) = :año")
    List<Topico> findByCursoAndYear(@Param("curso") String curso, @Param("año") int año);
}
