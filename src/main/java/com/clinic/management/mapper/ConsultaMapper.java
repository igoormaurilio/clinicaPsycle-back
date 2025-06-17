package com.clinic.management.mapper;

import com.clinic.management.dto.ConsultaDTO;
import com.clinic.management.dto.ConsultaUpdateDTO;
import com.clinic.management.domain.Consulta;

public class ConsultaMapper {

    public static ConsultaDTO toDTO(Consulta consulta) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setId(consulta.getId());
        dto.setDataConsulta(consulta.getDataConsulta());
        dto.setDataFim(consulta.getDataFim());

        if (consulta.getMedico() != null) {
            dto.setIdMedico(consulta.getMedico().getId());
            dto.setNomeMedico(consulta.getMedico().getNome()); // nome do médico
        }

        if (consulta.getPaciente() != null) {
            dto.setIdPaciente(consulta.getPaciente().getId());
            dto.setNomePaciente(consulta.getPaciente().getNome()); // nome do paciente
        }

        dto.setEspecialidade(consulta.getEspecialidade());
        dto.setStatus(consulta.getStatus());

        return dto;
    }

    public static Consulta toEntity(ConsultaDTO dto) {
        Consulta consulta = new Consulta();
        consulta.setDataConsulta(dto.getDataConsulta());
        consulta.setDataFim(dto.getDataFim());
        consulta.setEspecialidade(dto.getEspecialidade());
        consulta.setStatus(dto.getStatus());
        // Obs: médico e paciente devem ser setados separadamente, via service/repository
        return consulta;
    }

    public static void updateEntityFromDTO(ConsultaUpdateDTO dto, Consulta consulta) {
        if (dto.getDataConsulta() != null) {
            consulta.setDataConsulta(dto.getDataConsulta());
            consulta.setDataFim(dto.getDataConsulta().plusMinutes(15));
        }

        if (dto.getEspecialidade() != null) {
            consulta.setEspecialidade(dto.getEspecialidade());
        }

        if (dto.getStatus() != null) {
            consulta.setStatus(dto.getStatus());
        }
    }
}
