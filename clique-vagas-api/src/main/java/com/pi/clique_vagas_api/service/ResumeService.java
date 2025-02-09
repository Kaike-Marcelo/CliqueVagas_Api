package com.pi.clique_vagas_api.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.resources.dto.certificate.CertificateWithIdDto;
import com.pi.clique_vagas_api.resources.dto.resume.ResumeDto;
import com.pi.clique_vagas_api.resources.dto.skill.Skill_Intermediate_WithIdDto;
import com.pi.clique_vagas_api.service.skills.Skill_Intern_Service;
import com.pi.clique_vagas_api.service.users.typeUsers.InternService;
import com.pi.clique_vagas_api.utils.FileUtils;

@Service
public class ResumeService {

    @Autowired
    private InternService internService;

    @Autowired
    private Skill_Intern_Service skillService;

    @Autowired
    private CertificateService certificateService;

    public byte[] generateResumePdf(UserModel user) {
        InternModel intern = internService.getInternByUser(user);
        ResumeDto resumeData = prepareResumeData(intern);
        return createPdf(resumeData);
    }

    private ResumeDto prepareResumeData(InternModel intern) {

        List<Skill_Intermediate_WithIdDto> skills = skillService.getSkillsDtoByInternId(intern);

        List<CertificateWithIdDto> certificates = certificateService.getCertificatesByInternId(intern);

        byte[] profileImage = null;
        if (intern.getUserId().getUrlImageProfile() != null) {
            profileImage = FileUtils.loadFileFromPath(intern.getUserId().getUrlImageProfile());
        }

        return new ResumeDto(
                intern.getUserId().getFirstName() + " " + intern.getUserId().getLastName(),
                intern.getUserId().getEmail(),
                intern.getUserId().getPhone(),
                intern.getEducationalInstitution(),
                intern.getAreaOfInterest(),
                profileImage,
                skills,
                certificates);
    }

    private byte[] createPdf(ResumeDto resume) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                PDFont titleFont = PDType1Font.HELVETICA_BOLD;
                PDFont textFont = PDType1Font.HELVETICA;

                // Tamanho da fonte
                int nameFontSize = 20;
                int titleFontSize = 14;
                int textFontSize = 12;

                // Cores
                contentStream.setNonStrokingColor(java.awt.Color.BLACK); // Preto para texto

                // Adicionando nome completo em destaque
                contentStream.setFont(titleFont, nameFontSize);
                float y = 750;
                contentStream.beginText();
                contentStream.newLineAtOffset(50, y);
                contentStream.showText(resume.fullName());
                contentStream.endText();

                // Adicionando imagem ao lado do nome
                if (resume.profileImage() != null) {
                    PDImageXObject image = JPEGFactory.createFromByteArray(document, resume.profileImage());
                    contentStream.drawImage(image, 470, y - 80, 100, 100);
                }

                y -= 40; // Pular duas linhas

                // Adicionando email e telefone
                contentStream.setFont(textFont, textFontSize);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, y);
                contentStream.showText("Email: " + resume.email());
                y -= 20;
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Telefone: " + resume.phone());
                y -= 20;
                contentStream.endText();

                // Pular duas linhas antes do próximo título
                y -= 40;

                // Adicionando seção de Certificados
                contentStream.setNonStrokingColor(java.awt.Color.BLUE); // Azul para títulos
                contentStream.setFont(titleFont, titleFontSize);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, y);
                contentStream.showText("Certificados:");
                y -= 20;
                contentStream.endText();

                contentStream.setNonStrokingColor(java.awt.Color.BLACK); // Preto para texto
                contentStream.setFont(textFont, textFontSize);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, y);
                for (CertificateWithIdDto certificate : resume.certificates()) {
                    contentStream.showText(certificate.name() + " - " + certificate.creditHours());
                    y -= 15;
                    contentStream.newLineAtOffset(0, -15);
                }
                contentStream.endText();

                // Pular duas linhas antes do próximo título
                y -= 40;

                // Adicionando seção de Skills
                contentStream.setNonStrokingColor(java.awt.Color.BLUE); // Azul para títulos
                contentStream.setFont(titleFont, titleFontSize);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, y);
                contentStream.showText("Skills:");
                y -= 20;
                contentStream.endText();

                contentStream.setNonStrokingColor(java.awt.Color.BLACK); // Preto para texto
                contentStream.setFont(textFont, textFontSize);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, y);
                for (Skill_Intermediate_WithIdDto skill : resume.skills()) {
                    contentStream.showText(skill.idSkill().getName() + " - " + skill.proficiencyLevel());
                    y -= 15;
                    contentStream.newLineAtOffset(0, -15);
                }
                contentStream.endText();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
}