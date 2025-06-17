package com.clinic.management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.management.dto.ConsultaDTO;
import com.clinic.management.dto.ConsultaUpdateDTO;
import com.clinic.management.response.ApiResponse;
import com.clinic.management.service.ConsultaService;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin(origins = "https://clinica-psycle-front.vercel.app")
@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ConsultaDTO>> schedule(@RequestBody ConsultaDTO dto) {
        try {
            ConsultaDTO consulta = service.agendarConsulta(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(consulta, "Consulta agendada com sucesso."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(null, "Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, "Erro inesperado ao agendar a consulta: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ConsultaDTO>> getById(@PathVariable Long id) {
        try {
            ConsultaDTO consulta = service.buscarPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(consulta, "Consulta encontrada."));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, "Consulta não encontrada: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ConsultaDTO>>> getAll() {
        List<ConsultaDTO> consultas = service.listarTodas();

        if (consultas.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(consultas, "Nenhuma consulta encontrada."));
        }

        return ResponseEntity.ok(new ApiResponse<>(consultas, "Consultas encontradas com sucesso."));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<ConsultaDTO>>> getByDoctor(@PathVariable Long doctorId) {
        List<ConsultaDTO> consultas = service.listarPorMedico(doctorId);

        if (consultas.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(consultas, "Nenhuma consulta encontrada para este médico."));
        }

        return ResponseEntity.ok(new ApiResponse<>(consultas, "Consultas encontradas para o médico."));
    }

    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<ApiResponse<List<ConsultaDTO>>> getBySpecialty(@PathVariable String specialty) {
        List<ConsultaDTO> consultas = service.listarPorEspecialidade(specialty);

        if (consultas.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(consultas, "Nenhuma consulta encontrada para esta especialidade."));
        }

        return ResponseEntity.ok(new ApiResponse<>(consultas, "Consultas encontradas para a especialidade."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ConsultaDTO>> atualizarConsulta(@PathVariable Long id, @RequestBody ConsultaUpdateDTO dto) {
        try {
            ConsultaDTO atualizada = service.atualizarConsulta(id, dto);
            return ResponseEntity.ok(new ApiResponse<>(atualizada, "Consulta atualizada com sucesso."));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, "Consulta não encontrada: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(null, "Erro de validação: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, "Erro inesperado ao atualizar a consulta: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancel(@PathVariable Long id) {
        try {
            service.cancelarConsulta(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, "Consulta não encontrada: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, "Erro inesperado ao cancelar a consulta: " + e.getMessage()));
        }
    }
}
