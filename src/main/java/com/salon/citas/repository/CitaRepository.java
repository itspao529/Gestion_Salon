package com.salon.citas.repository;
import com.salon.citas.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CitaRepository extends JpaRepository<Cita, Long> { }