package com.ecommerce.camisetas.model.dto;

import lombok.Data;

@Data
public class RegistroRequestDto {
    private String username;
    private String email;
    private String password;
    private String nombre;
    private String apellido;
}
