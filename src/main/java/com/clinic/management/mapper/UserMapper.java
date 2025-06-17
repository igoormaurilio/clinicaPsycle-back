package com.clinic.management.mapper;

import com.clinic.management.domain.User;
import com.clinic.management.dto.UserUpdateDTO;

public class UserMapper
{
    public static void updateEntityFromDTO(UserUpdateDTO dto, User entity)
    {
        if (dto.email != null)
        {
            entity.setEmail(dto.email);
        }
        if (dto.nome != null)
        {
            entity.setNome(dto.nome);
        }
        if (dto.tipoUsuario != null)
        {
            entity.setTipoUsuario(dto.tipoUsuario);
        }
        if (dto.permissao != null)
        {
            entity.setPermissao(dto.permissao);
        }
    }


}
