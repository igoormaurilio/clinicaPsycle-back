package com.clinic.management.mapper;

import com.clinic.management.dto.MedicoUpdateDTO;
import com.clinic.management.domain.Medico;

public class MedicoMapper
{
    public static void updateEntityFromDTO(MedicoUpdateDTO dto, Medico entity)
    {
        if (dto.nome != null)
        {
            entity.setNome(dto.nome);
        }
        if (dto.email != null)
        {
            entity.setEmail(dto.email);
        }
        if (dto.telefone != null)
        {
            entity.setTelefone(dto.telefone);
        }
        if (dto.especialidade != null)
        {
            entity.setEspecialidade(dto.especialidade);
        }
        if (dto.rm != null)
        {
            entity.setRm(dto.rm);
        }
    }
}
