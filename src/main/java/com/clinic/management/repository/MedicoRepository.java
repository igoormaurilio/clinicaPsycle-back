package com.clinic.management.repository;

import com.clinic.management.domain.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long>
{
    boolean existsByEmail(String email);
}

