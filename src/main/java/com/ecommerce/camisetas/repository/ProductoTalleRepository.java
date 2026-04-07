package com.ecommerce.camisetas.repository;

import com.ecommerce.camisetas.model.entity.ProductoTalle;
import com.ecommerce.camisetas.model.enums.Talle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoTalleRepository extends JpaRepository<ProductoTalle, Long> {
    List<ProductoTalle> findByProductoIdProducto(Long idProducto);
    Optional<ProductoTalle> findByProductoIdProductoAndTalle(Long idProducto, Talle talle);
}
