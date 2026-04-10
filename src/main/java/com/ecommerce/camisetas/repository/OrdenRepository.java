package com.ecommerce.camisetas.repository;

import com.ecommerce.camisetas.model.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByUsuarioIdUsuario(Long idUsuario);
    java.util.Optional<Orden> findByIdOrdenAndUsuarioIdUsuario(Long idOrden, Long idUsuario);
}
