package com.clinic.management.service;

import com.clinic.management.domain.User;
import com.clinic.management.dto.UserUpdateDTO;
import com.clinic.management.mapper.UserMapper;
import com.clinic.management.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder)
    {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User cadastrarUser(User user)
    {
        if (repository.existsByEmail(user.getEmail()))
        {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este e-mail.");
        }

        String senhaEncriptada = passwordEncoder.encode(user.getSenha());
        user.setSenha(senhaEncriptada);
        return repository.save(user);
    }

    public User buscarPorId(Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
    }

    public List<User> listarTodos()
    {
        return repository.findAll();
    }

    @Transactional
    public User atualizarUser(Long id, UserUpdateDTO dto)
    {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + id));

        UserMapper.updateEntityFromDTO(dto, user);
        return repository.save(user);
    }

    public void deletarUser(Long id)
    {
        User user = buscarPorId(id);
        repository.delete(user);
    }
}
