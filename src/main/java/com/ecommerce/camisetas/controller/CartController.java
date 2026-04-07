package com.ecommerce.camisetas.controller;

import com.ecommerce.camisetas.model.dto.AgregarItemRequestDto;
import com.ecommerce.camisetas.model.dto.CarritoDto;
import com.ecommerce.camisetas.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CarritoDto> obtenerCarritoActivo(@org.springframework.security.core.annotation.AuthenticationPrincipal com.ecommerce.camisetas.model.entity.Usuario usuario) {
        return ResponseEntity.ok(cartService.obtenerCarritoActivo(usuario.getIdUsuario()));
    }

    @PostMapping("/items")
    public ResponseEntity<CarritoDto> agregarItem(
            @org.springframework.security.core.annotation.AuthenticationPrincipal com.ecommerce.camisetas.model.entity.Usuario usuario,
            @RequestBody @Validated AgregarItemRequestDto request) {
        return ResponseEntity.ok(cartService.agregarAlCarrito(usuario.getIdUsuario(), request));
    }

    @DeleteMapping("/items/{idItem}")
    public ResponseEntity<Void> eliminarItem(
            @org.springframework.security.core.annotation.AuthenticationPrincipal com.ecommerce.camisetas.model.entity.Usuario usuario,
            @PathVariable Long idItem) {
        cartService.eliminarItem(usuario.getIdUsuario(), idItem);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/items/{idItem}")
    public ResponseEntity<CarritoDto> modificarCantidad(
            @org.springframework.security.core.annotation.AuthenticationPrincipal com.ecommerce.camisetas.model.entity.Usuario usuario,
            @PathVariable Long idItem,
            @RequestParam Integer cantidad) {
        return ResponseEntity.ok(cartService.modificarCantidadItem(usuario.getIdUsuario(), idItem, cantidad));
    }
}
