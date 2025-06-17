package com.clinic.management.repository;

import com.clinic.management.domain.Consulta;
import com.clinic.management.domain.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>
{

    boolean existsByMedicoAndDataConsultaBetween(Medico medico, LocalDateTime inicio, LocalDateTime fim);

    boolean existsByPacienteIdAndDataConsultaBetween(Long pacienteId, LocalDateTime inicio, LocalDateTime fim);

    List<Consulta> findByMedicoId(Long medicoId);

    List<Consulta> findByMedicoEspecialidade(String especialidade);
}
