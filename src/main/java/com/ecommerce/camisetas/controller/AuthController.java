package com.ecommerce.camisetas.controller;

import com.ecommerce.camisetas.model.dto.LoginRequestDto;
import com.ecommerce.camisetas.model.dto.LoginResponseDto;
import com.ecommerce.camisetas.model.dto.RegistroRequestDto;
import com.ecommerce.camisetas.model.dto.UsuarioDto;
import com.ecommerce.camisetas.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDto> registrar(@RequestBody @Validated RegistroRequestDto request) {
        return new ResponseEntity<>(authService.registrar(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
