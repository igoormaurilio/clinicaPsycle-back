package com.clinic.management.mapper;

import com.clinic.management.dto.PacienteUpdateDTO;
import com.clinic.management.domain.Paciente;

public class PacienteMapper
{
    public static void updateEntityFromDTO(PacienteUpdateDTO dto, Paciente entity)
    {
        if (dto.nome != null) {
            entity.setNome(dto.nome);
        }
        if (dto.email != null) {
            entity.setEmail(dto.email);
        }
        if (dto.telefone != null) {
            entity.setTelefone(dto.telefone);
        }
        if (dto.dataNascimento != null) {
            entity.setDataNascimento(dto.dataNascimento);
        }
    }
}


