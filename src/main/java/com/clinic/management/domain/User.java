package com.clinic.management.domain;

import com.clinic.management.domain.enums.TipoPermissao;
import com.clinic.management.domain.enums.TipoUsuario;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipoUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPermissao permissao;


    public User()
    {

    }

    public User(String email, String senha, String nome, TipoUsuario tipoUsuario, TipoPermissao permissao)
    {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
        this.permissao = permissao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public TipoPermissao getPermissao() {
        return permissao;
    }

    public void setPermissao(TipoPermissao permissao) {
        this.permissao = permissao;
    }
}
