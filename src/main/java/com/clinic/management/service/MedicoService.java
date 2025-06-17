package com.clinic.management.service;

import com.clinic.management.domain.Medico;
import com.clinic.management.dto.MedicoUpdateDTO;
import com.clinic.management.mapper.MedicoMapper;
import com.clinic.management.repository.MedicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService
{
    private final MedicoRepository repository;

    public MedicoService(MedicoRepository repository)
    {
        this.repository = repository;
    }

    public Medico cadastrarMedico(Medico medico)
    {
        if (repository.existsByEmail(medico.getEmail()))
        {
            throw new IllegalArgumentException("Já existe um médico com este email.");
        }
        return repository.save(medico);
    }

    public Medico buscarPorId(Long id)
    {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Médico não encontrado."));
    }

    public List<Medico> listarTodos()
    {
        return repository.findAll();
    }

    @Transactional
    public Medico atualizarMedico(Long id, MedicoUpdateDTO dto)
    {
        Medico medico = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Médico não encontrado com id: " + id));
        MedicoMapper.updateEntityFromDTO(dto, medico);
        return repository.save(medico);
    }

    public void deletarMedico(Long id)
    {
        Medico medico = buscarPorId(id);
        repository.delete(medico);
    }
}
