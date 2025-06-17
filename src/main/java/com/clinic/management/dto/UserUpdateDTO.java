package com.clinic.management.dto;

import com.clinic.management.domain.enums.TipoPermissao;
import com.clinic.management.domain.enums.TipoUsuario;

public class UserUpdateDTO
{
    public String nome;
    public String email;
    public TipoUsuario tipoUsuario;
    public TipoPermissao permissao;
}
