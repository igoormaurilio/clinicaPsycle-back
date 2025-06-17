package com.clinic.management.service;

import com.clinic.management.dto.PacienteUpdateDTO;
import com.clinic.management.domain.Paciente;
import com.clinic.management.mapper.PacienteMapper;
import com.clinic.management.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository)
    {
        this.repository = repository;
    }

    public Paciente cadastrarPaciente(Paciente paciente)
    {
        if (repository.existsByEmail(paciente.getEmail()))
        {
            throw new IllegalArgumentException("Já existe um paciente com este email.");
        }
        return repository.save(paciente);
    }

    public Paciente buscarPorId(Long id)
    {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado."));
    }

    public List<Paciente> listarTodos()
    {
        return repository.findAll();
    }

    @Transactional
    public Paciente atualizarPaciente(Long id, PacienteUpdateDTO dto)
    {
        Paciente paciente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com id: " + id));

        PacienteMapper.updateEntityFromDTO(dto, paciente);

        return repository.save(paciente);
    }

    @Transactional
    public void deletarPaciente(Long id)
    {
        Paciente paciente = buscarPorId(id);
        repository.delete(paciente);
    }
}
