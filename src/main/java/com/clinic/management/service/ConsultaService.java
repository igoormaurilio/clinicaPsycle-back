package com.clinic.management.service;

import com.clinic.management.dto.ConsultaDTO;
import com.clinic.management.dto.ConsultaUpdateDTO;
import com.clinic.management.domain.Consulta;
import com.clinic.management.domain.Medico;
import com.clinic.management.domain.Paciente;
import com.clinic.management.mapper.ConsultaMapper;
import com.clinic.management.repository.ConsultaRepository;
import com.clinic.management.repository.MedicoRepository;
import com.clinic.management.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService
{

    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(ConsultaRepository repository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository)
    {
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public ConsultaDTO agendarConsulta(ConsultaDTO dto)
    {
        Medico medico = medicoRepository.findById(dto.getIdMedico()).orElseThrow(() -> new IllegalArgumentException("Médico não encontrado com o ID informado."));

        Paciente paciente = pacienteRepository.findById(dto.getIdPaciente()).orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com o ID informado."));

        Consulta consulta = ConsultaMapper.toEntity(dto);
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setStatus("AGENDADA");

        validarConsulta(consulta);

        LocalDateTime inicio = consulta.getDataConsulta();
        LocalDateTime fim = inicio.plusMinutes(15);
        consulta.setDataFim(fim);

        if (repository.existsByMedicoAndDataConsultaBetween(medico, inicio, fim.minusSeconds(1)))
        {
            throw new IllegalArgumentException("Médico já possui consulta nesse intervalo.");
        }

        if (repository.existsByPacienteIdAndDataConsultaBetween(paciente.getId(), inicio, fim.minusSeconds(1)))
        {
            throw new IllegalArgumentException("Paciente já possui consulta nesse intervalo.");
        }

        Consulta saved = repository.save(consulta);
        return ConsultaMapper.toDTO(saved);
    }

    @Transactional
    public ConsultaDTO atualizarConsulta(Long id, ConsultaUpdateDTO dto)
    {
        Consulta consulta = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com id: " + id));

        ConsultaMapper.updateEntityFromDTO(dto, consulta);

        validarConsulta(consulta);

        if (repository.existsByMedicoAndDataConsultaBetween(consulta.getMedico(), consulta.getDataConsulta(), consulta.getDataFim().minusSeconds(1)))
        {
            throw new IllegalArgumentException("Médico já possui consulta nesse intervalo.");
        }

        if (repository.existsByPacienteIdAndDataConsultaBetween(consulta.getPaciente().getId(), consulta.getDataConsulta(), consulta.getDataFim().minusSeconds(1)))
        {
            throw new IllegalArgumentException("Paciente já possui consulta nesse intervalo.");
        }

        Consulta atualizada = repository.save(consulta);
        return ConsultaMapper.toDTO(atualizada);
    }

    public ConsultaDTO buscarPorId(Long id)
    {
        Consulta consulta = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com id: " + id));
        return ConsultaMapper.toDTO(consulta);
    }

    public List<ConsultaDTO> listarTodas()
    {
        return repository.findAll().stream().map(ConsultaMapper::toDTO).collect(Collectors.toList());
    }

    public List<ConsultaDTO> listarPorMedico(Long medicoId)
    {
        return repository.findByMedicoId(medicoId).stream().map(ConsultaMapper::toDTO).collect(Collectors.toList());
    }

    public List<ConsultaDTO> listarPorEspecialidade(String especialidade)
    {
        return repository.findByMedicoEspecialidade(especialidade).stream().map(ConsultaMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public void cancelarConsulta(Long id)
    {
        Consulta consulta = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com id: " + id));repository.delete(consulta);
    }

    private void validarConsulta(Consulta consulta)
    {
        LocalDateTime inicio = consulta.getDataConsulta();

        if (inicio.isBefore(LocalDateTime.now().plusMinutes(1)))
        {
            throw new IllegalArgumentException("Não é permitido agendar consultas no passado ou no mesmo minuto.");
        }

        validarHorarioExpediente(inicio.toLocalTime());
        validarEspecialidade(consulta);
    }

    private void validarHorarioExpediente(LocalTime horario)
    {
        LocalTime inicioExpediente = LocalTime.of(8, 0);
        LocalTime fimExpediente = LocalTime.of(16, 45);

        if (horario.isBefore(inicioExpediente) || horario.isAfter(fimExpediente))
        {
            throw new IllegalArgumentException("Consultas só podem ser agendadas entre 08:00 e 16:45.");
        }
    }

    private void validarEspecialidade(Consulta consulta)
    {
        Medico medico = consulta.getMedico();
        if (!medico.getEspecialidade().equalsIgnoreCase(consulta.getEspecialidade()))
        {
            throw new IllegalArgumentException(String.format("O médico selecionado atende na especialidade %s, mas a consulta foi marcada para %s.", medico.getEspecialidade(), consulta.getEspecialidade()));
        }
    }
}
