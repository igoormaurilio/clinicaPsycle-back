package com.clinic.management.service;

import com.clinic.management.domain.Consulta;
import com.clinic.management.domain.Medico;
import com.clinic.management.domain.Paciente;
import com.clinic.management.domain.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.Color;

@Service
public class PdfGenerator {

    private final Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD);
    private final Font subtituloFont = new Font(Font.HELVETICA, 14, Font.BOLD);
    private final Font textoFont = new Font(Font.HELVETICA, 12);
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] gerarRelatorioCompleto(List<Consulta> consultas, List<Medico> medicos,
                                         List<Paciente> pacientes, List<User> usuarios) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título principal
            Paragraph titulo = new Paragraph("Relatório Geral da Clínica", tituloFont);
            titulo.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(titulo);
            document.add(Chunk.NEWLINE);

            // Seção: Consultas
            adicionarTituloSecao(document, "Consultas");
            PdfPTable tabelaConsultas = new PdfPTable(6);
            tabelaConsultas.setWidthPercentage(100);
            tabelaConsultas.setWidths(new float[]{1, 3, 3, 3, 3, 2});
            adicionarCabecalhos(tabelaConsultas, "ID", "Início", "Fim", "Médico", "Paciente", "Status");
            for (Consulta c : consultas) {
                tabelaConsultas.addCell(String.valueOf(c.getId()));
                tabelaConsultas.addCell(dtf.format(c.getDataConsulta()));
                tabelaConsultas.addCell(dtf.format(c.getDataFim()));
                tabelaConsultas.addCell(c.getMedico().getNome());
                tabelaConsultas.addCell(c.getPaciente().getNome());
                tabelaConsultas.addCell(c.getStatus());
            }
            document.add(tabelaConsultas);
            document.add(Chunk.NEWLINE);

            // Seção: Médicos
            adicionarTituloSecao(document, "Médicos");
            PdfPTable tabelaMedicos = new PdfPTable(5);
            tabelaMedicos.setWidthPercentage(100);
            adicionarCabecalhos(tabelaMedicos, "ID", "Nome", "Especialidade", "Email", "Telefone");
            for (Medico m : medicos) {
                tabelaMedicos.addCell(String.valueOf(m.getId()));
                tabelaMedicos.addCell(m.getNome());
                tabelaMedicos.addCell(m.getEspecialidade());
                tabelaMedicos.addCell(m.getEmail());
                tabelaMedicos.addCell(m.getTelefone());
            }
            document.add(tabelaMedicos);
            document.add(Chunk.NEWLINE);

            // Seção: Pacientes
            adicionarTituloSecao(document, "Pacientes");
            PdfPTable tabelaPacientes = new PdfPTable(5);
            tabelaPacientes.setWidthPercentage(100);
            adicionarCabecalhos(tabelaPacientes, "ID", "Nome", "Email", "Telefone", "Data Nasc.");
            for (Paciente p : pacientes) {
                tabelaPacientes.addCell(String.valueOf(p.getId()));
                tabelaPacientes.addCell(p.getNome());
                tabelaPacientes.addCell(p.getEmail());
                tabelaPacientes.addCell(p.getTelefone());
                tabelaPacientes.addCell(p.getDataNascimento().toString());
            }
            document.add(tabelaPacientes);
            document.add(Chunk.NEWLINE);

            // Seção: Usuários
            adicionarTituloSecao(document, "Usuários do Sistema");
            PdfPTable tabelaUsuarios = new PdfPTable(5);
            tabelaUsuarios.setWidthPercentage(100);
            adicionarCabecalhos(tabelaUsuarios, "ID", "Nome", "Email", "Tipo", "Permissão");
            for (User u : usuarios) {
                tabelaUsuarios.addCell(String.valueOf(u.getId()));
                tabelaUsuarios.addCell(u.getNome());
                tabelaUsuarios.addCell(u.getEmail());
                tabelaUsuarios.addCell(u.getTipoUsuario().toString());
                tabelaUsuarios.addCell(u.getPermissao().toString());
            }
            document.add(tabelaUsuarios);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return out.toByteArray();
    }

    private void adicionarCabecalhos(PdfPTable tabela, String... cabecalhos) {
        for (String cabecalho : cabecalhos) {
            PdfPCell header = new PdfPCell(new Phrase("Coluna"));
            header.setBackgroundColor(Color.LIGHT_GRAY);
            header.setPhrase(new Phrase(cabecalho, textoFont));
            tabela.addCell(header);
        }
    }

    private void adicionarTituloSecao(Document doc, String titulo) throws DocumentException {
        Paragraph paragrafo = new Paragraph(titulo, subtituloFont);
        paragrafo.setSpacingBefore(10f);
        paragrafo.setSpacingAfter(10f);
        doc.add(paragrafo);
    }
}
