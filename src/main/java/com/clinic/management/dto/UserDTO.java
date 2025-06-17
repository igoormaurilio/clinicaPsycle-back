package com.clinic.management.dto;

import com.clinic.management.domain.enums.TipoPermissao;
import com.clinic.management.domain.enums.TipoUsuario;

public class UserDTO
{
    private String nome;
    private String email;
    private TipoUsuario tipoUsuario;
    private TipoPermissao permissao;
}
