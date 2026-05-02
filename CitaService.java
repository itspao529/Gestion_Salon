package com.salon.citas.service;

import com.salon.citas.model.Cita;
import com.salon.citas.model.Cliente;
import com.salon.citas.model.Servicio;
import com.salon.citas.repository.CitaRepository;
import com.salon.citas.repository.ClienteRepository;
import com.salon.citas.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    // Agendar una nueva cita
    public Cita agendarCita(Long clienteId, Long servicioId, Cita cita) {
        // Validar que el cliente existe
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));

        // Validar que el servicio existe
        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + servicioId));

        // Validar que no exista ya una cita en el mismo horario
        boolean horarioOcupado = citaRepository.findAll().stream()
                .anyMatch(c -> c.getFechaHora().equals(cita.getFechaHora()));

        if (horarioOcupado) {
            throw new RuntimeException("Ya existe una cita programada para ese horario.");
        }

        cita.setCliente(cliente);
        cita.setServicio(servicio);

        return citaRepository.save(cita);
    }

    // Obtener todas las citas
    public List<Cita> obtenerTodas() {
        return citaRepository.findAll();
    }

    // Obtener una cita por ID
    public Optional<Cita> obtenerPorId(Long id) {
        return citaRepository.findById(id);
    }

    // Obtener historial de citas de un cliente
    public List<Cita> obtenerCitasPorCliente(Long clienteId) {
        return citaRepository.findAll().stream()
                .filter(c -> c.getCliente().getId().equals(clienteId))
                .toList();
    }

    // Modificar una cita existente
    public Cita modificarCita(Long id, Long clienteId, Long servicioId, Cita citaActualizada) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));

        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + servicioId));

        cita.setFechaHora(citaActualizada.getFechaHora());
        cita.setCliente(cliente);
        cita.setServicio(servicio);

        return citaRepository.save(cita);
    }

    // Cancelar (eliminar) una cita
    public void cancelarCita(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        citaRepository.deleteById(id);
    }
}
