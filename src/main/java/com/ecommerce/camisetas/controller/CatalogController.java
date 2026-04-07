package com.ecommerce.camisetas.controller;

import com.ecommerce.camisetas.model.dto.*;
import com.ecommerce.camisetas.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDto>> obtenerProductos(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) String q) {
        return ResponseEntity.ok(catalogService.obtenerProductosActivos(categoriaId, precioMin, precioMax, q));
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDto> obtenerProductoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(catalogService.obtenerProducto(id));
    }

    @PostMapping("/productos")
    public ResponseEntity<ProductoDto> crearProducto(@RequestBody @Validated ProductoRequestDto request) {
        return new ResponseEntity<>(catalogService.crearProducto(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        catalogService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoDto> actualizarProducto(@PathVariable Long id, @RequestBody ProductoRequestDto request) {
        return ResponseEntity.ok(catalogService.actualizarProducto(id, request));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDto>> obtenerCategorias() {
        return ResponseEntity.ok(catalogService.obtenerCategorias());
    }

    @GetMapping("/clubes")
    public ResponseEntity<List<ClubDto>> obtenerClubes() {
        return ResponseEntity.ok(catalogService.obtenerClubes());
    }

    @PostMapping("/clubes")
    public ResponseEntity<ClubDto> crearClub(@RequestBody @Validated ClubRequestDto request) {
        return new ResponseEntity<>(catalogService.crearClub(request), HttpStatus.CREATED);
    }

    // -- Endpoints de Descuentos --

    @PostMapping("/productos/{productoId}/descuentos")
    public ResponseEntity<DescuentoDto> aplicarDescuento(
            @PathVariable Long productoId,
            @RequestBody @Validated DescuentoRequestDto request) {
        return new ResponseEntity<>(catalogService.aplicarDescuento(productoId, request), HttpStatus.CREATED);
    }

    @GetMapping("/productos/{productoId}/descuentos")
    public ResponseEntity<List<DescuentoDto>> obtenerDescuentos(@PathVariable Long productoId) {
        return ResponseEntity.ok(catalogService.obtenerDescuentosDeProducto(productoId));
    }

    @DeleteMapping("/descuentos/{id}")
    public ResponseEntity<Void> eliminarDescuento(@PathVariable Long id) {
        catalogService.eliminarDescuento(id);
        return ResponseEntity.noContent().build();
    }
}
