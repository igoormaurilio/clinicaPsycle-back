package com.clinic.management.repository;

import com.clinic.management.domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long>
{

    boolean existsByEmail(String email);

    Optional<Paciente> findByEmail(String email);
}

