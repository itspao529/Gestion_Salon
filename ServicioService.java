package com.salon.citas.service;

import com.salon.citas.model.Servicio;
import com.salon.citas.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    // Registrar un nuevo servicio
    public Servicio registrarServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    // Obtener todos los servicios
    public List<Servicio> obtenerTodos() {
        return servicioRepository.findAll();
    }

    // Obtener un servicio por ID
    public Optional<Servicio> obtenerPorId(Long id) {
        return servicioRepository.findById(id);
    }

    // Actualizar un servicio existente
    public Servicio actualizarServicio(Long id, Servicio servicioActualizado) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));

        servicio.setNombreServicio(servicioActualizado.getNombreServicio());
        servicio.setPrecio(servicioActualizado.getPrecio());

        return servicioRepository.save(servicio);
    }

    // Eliminar un servicio
    public void eliminarServicio(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new RuntimeException("Servicio no encontrado con ID: " + id);
        }
        servicioRepository.deleteById(id);
    }
}
