package com.ecommerce.camisetas.controller;

import com.ecommerce.camisetas.model.dto.CheckoutRequestDto;
import com.ecommerce.camisetas.model.dto.OrdenDto;
import com.ecommerce.camisetas.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrdenDto> realizarCheckout(
            @org.springframework.security.core.annotation.AuthenticationPrincipal com.ecommerce.camisetas.model.entity.Usuario usuario,
            @RequestBody @Validated CheckoutRequestDto request) {
        return new ResponseEntity<>(orderService.checkout(usuario.getIdUsuario(), request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrdenDto>> obtenerMisOrdenes(@org.springframework.security.core.annotation.AuthenticationPrincipal com.ecommerce.camisetas.model.entity.Usuario usuario) {
        return ResponseEntity.ok(orderService.obtenerOrdenesDeUsuario(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenDto> obtenerOrden(
            @org.springframework.security.core.annotation.AuthenticationPrincipal com.ecommerce.camisetas.model.entity.Usuario usuario,
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.obtenerOrden(usuario, id));
    }
}
