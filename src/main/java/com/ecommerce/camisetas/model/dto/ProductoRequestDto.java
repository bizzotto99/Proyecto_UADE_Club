package com.ecommerce.camisetas.model.dto;

import com.ecommerce.camisetas.model.enums.TipoProducto;
import lombok.Data;
import java.util.List;

@Data
public class ProductoRequestDto {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String temporada;
    private TipoProducto tipo;
    private String fotoUrl;
    private List<String> fotosUrls;
    private Long idClub;
    private Long idCategoria;
    private List<ProductoTalleDto> talles;
}
