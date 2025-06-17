package com.clinic.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.management.domain.Consulta;
import com.clinic.management.domain.Medico;
import com.clinic.management.domain.Paciente;
import com.clinic.management.domain.User;
import com.clinic.management.repository.ConsultaRepository;
import com.clinic.management.repository.MedicoRepository;
import com.clinic.management.repository.PacienteRepository;
import com.clinic.management.repository.UserRepository;
import com.clinic.management.service.PdfGenerator;

@CrossOrigin(origins = "https://clinica-psycle-front.vercel.app")
@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PdfGenerator pdfGenerator;

    @GetMapping("/completo")
    public ResponseEntity<byte[]> gerarRelatorioCompleto() {
        List<Consulta> consultas = consultaRepository.findAll();
        List<Medico> medicos = medicoRepository.findAll();
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<User> usuarios = userRepository.findAll();

        byte[] pdf = pdfGenerator.gerarRelatorioCompleto(consultas, medicos, pacientes, usuarios);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename("relatorio_geral.pdf")
                .build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
