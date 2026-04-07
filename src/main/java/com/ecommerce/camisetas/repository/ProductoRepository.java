package com.ecommerce.camisetas.repository;

import com.ecommerce.camisetas.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue();
    List<Producto> findByCategoriaIdCategoriaAndActivoTrue(Long idCategoria);
    List<Producto> findByClubIdClubAndActivoTrue(Long idClub);

    @org.springframework.data.jpa.repository.Query("SELECT p FROM Producto p WHERE p.activo = true " +
           "AND (:idCategoria IS NULL OR p.categoria.idCategoria = :idCategoria) " +
           "AND (:precioMin IS NULL OR p.precio >= :precioMin) " +
           "AND (:precioMax IS NULL OR p.precio <= :precioMax) " +
           "AND (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<Producto> findFiltrados(@org.springframework.data.repository.query.Param("idCategoria") Long idCategoria, 
                                 @org.springframework.data.repository.query.Param("precioMin") Double precioMin, 
                                 @org.springframework.data.repository.query.Param("precioMax") Double precioMax, 
                                 @org.springframework.data.repository.query.Param("nombre") String nombre);
}
