package com.ecommerce.camisetas.model.dto;

import lombok.Data;

@Data
public class AgregarItemRequestDto {
    private Long idProducto;
    private Long idProdTalle; // Opcional, dependiendo si el producto tiene talles
    private Integer cantidad;
}
