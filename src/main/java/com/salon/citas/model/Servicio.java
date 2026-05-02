package com.salon.citas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreServicio;
    private Double precio;
}