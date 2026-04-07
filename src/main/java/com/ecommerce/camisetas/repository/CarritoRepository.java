package com.ecommerce.camisetas.repository;

import com.ecommerce.camisetas.model.entity.Carrito;
import com.ecommerce.camisetas.model.enums.EstadoCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuarioIdUsuarioAndEstado(Long idUsuario, EstadoCarrito estado);
}
