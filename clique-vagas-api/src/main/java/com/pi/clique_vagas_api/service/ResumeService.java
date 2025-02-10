package com.pi.clique_vagas_api.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;

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
import com.pi.clique_vagas_api.resources.dto.address.GetAddressDto;
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
    private AddressService addressService;

    @Autowired
    private Skill_Intern_Service skillService;

    @Autowired
    private CertificateService certificateService;

    public byte[] generateResumePdf(UserModel user) {
        InternModel intern = internService.getInternByUser(user);
        var address = addressService.getAddressDtoByUserId(user);
        ResumeDto resumeData = prepareResumeData(intern, address);
        return createPdf(resumeData);
    }

    private ResumeDto prepareResumeData(InternModel intern, GetAddressDto address) {

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
                intern.getUserId().getDescription(),
                intern.getEducationalInstitution(),
                intern.getAreaOfInterest(),
                intern.getYearOfEntry(),
                intern.getExpectedGraduationDate(),
                profileImage,
                address,
                skills,
                certificates);
    }

    private byte[] createPdf(ResumeDto resume) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Cores
            Color oceanBlue = new Color(31, 51, 100);
            Color white = new Color(255, 255, 255);
            Color black = new Color(0, 0, 0);

            // Fontes
            PDFont titleFont = PDType1Font.HELVETICA_BOLD;
            PDFont textFont = PDType1Font.HELVETICA;

            // Tamanhos de fonte
            int nameFontSize = 16;
            int titleFontSize = 14;
            int textFontSize = 12;

            float rectWidth = (page.getMediaBox().getWidth() / 3) + 50;
            float y = 750;

            var text = "";

            // Retângulo azul
            contentStream.setNonStrokingColor(oceanBlue);
            contentStream.addRect(0, 0, rectWidth, page.getMediaBox().getHeight());
            contentStream.fill();

            // Conteúdo do retângulo
            addImage(contentStream, resume.profileImage(), (rectWidth / 2) - 75, 600, 150, document);

            y -= 220;

            addTitle(contentStream, "ENDEREÇO:", 50, y, titleFont, titleFontSize, white);

            y -= 20;

            text = resume.address().street() + ", " + resume.address().number() + " - " +
                    resume.address().neighborhood() + ", " + resume.address().city() + " - " +
                    resume.address().state() + " - " + resume.address().cep();

            addText(contentStream, text, 50, y, textFont, textFontSize, white,
                    rectWidth - 100);
            y -= (text.split(" ").length * textFontSize) - 60;

            addTitle(contentStream, "TELEFONE:", 50, y, titleFont, titleFontSize, white);

            y -= 20;

            addText(contentStream, resume.phone(), 50, y, textFont, textFontSize, white, rectWidth - 100);
            y -= textFontSize + 25;

            addTitle(contentStream, "E-MAIL:", 50, y, titleFont, titleFontSize, white);
            y -= 20;
            addText(contentStream, resume.email(), 50, y, textFont, textFontSize, white, rectWidth - 100);
            y -= (resume.email().split(" ").length * textFontSize) + 50;

            addTitle(contentStream, "OBJETIVO", 50, y, titleFont, titleFontSize, white);
            y -= 20;
            if (resume.description() != null) {
                addText(contentStream, resume.description(), 50, y, textFont, textFontSize, white, rectWidth - 100);
                y -= (resume.description().split(" ").length * textFontSize) + 25;
            }

            // Conteúdo do lado direito
            y = 750;

            // Nome
            addTitle(contentStream, resume.fullName().toUpperCase(), 280, y - 40, titleFont, nameFontSize, oceanBlue);
            y -= 80;
            addTitle(contentStream, "HABILIDADES", 280, y, titleFont, titleFontSize, oceanBlue);
            y -= 20;
            for (Skill_Intermediate_WithIdDto skill : resume.skills()) {
                Object[] result = checkForNewPage(contentStream, document, y, rectWidth);
                contentStream = (PDPageContentStream) result[0];
                y = (float) result[1];
                addText(contentStream, "• " + skill.idSkill().getName() + " - " + skill.proficiencyLevel(), 280, y,
                        textFont, textFontSize, black, page.getMediaBox().getWidth() / 2);
                y -= 20;
            }

            y -= 20;

            addTitle(contentStream, "FORMAÇÃO", 280, y, titleFont, titleFontSize, oceanBlue);
            y -= 20;

            String formacaoText = resume.educationalInstitution().toUpperCase() + "\n" +
                    resume.yearOfEntry() + " - " + resume.expectedGraduationDate().toString().split("-")[0] + "\n" +
                    resume.areaOfInterest().toUpperCase();

            addText(contentStream, formacaoText, 280, y, textFont, textFontSize, black,
                    page.getMediaBox().getWidth() / 2);
            y -= (formacaoText.split("\n").length * textFontSize) + 40;

            addTitle(contentStream, "CURSOS E CERTIFICAÇÕES", 280, y, titleFont, titleFontSize, oceanBlue);
            y -= 20;

            for (CertificateWithIdDto certificate : resume.certificates()) {
                Object[] result = checkForNewPage(contentStream, document, y, rectWidth);
                contentStream = (PDPageContentStream) result[0];
                y = (float) result[1];

                String certificadoText = certificate.name().toUpperCase() + "\n" +
                        certificate.institution().toUpperCase() + "\n" +
                        "Emissão: " + certificate.issuanceDate() + " - Carga Horária: "
                        + certificate.creditHours() + " horas\n" +
                        certificate.description();

                addText(contentStream, certificadoText, 280, y, textFont, textFontSize, black,
                        page.getMediaBox().getWidth() / 2);
                y -= (certificadoText.split("\n").length * textFontSize) + 30;
            }

            contentStream.close();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

    private Object[] checkForNewPage(PDPageContentStream contentStream, PDDocument document, float y,
            float rectWidth) throws IOException {
        if (y < 50) {
            contentStream.close();
            PDPage newPage = new PDPage();
            document.addPage(newPage);
            contentStream = new PDPageContentStream(document, newPage);

            contentStream.setNonStrokingColor(new Color(31, 51, 100));
            contentStream.addRect(0, 0, rectWidth, newPage.getMediaBox().getHeight());
            contentStream.fill();

            y = 750;
        }
        return new Object[] { contentStream, y };
    }

    private void addTitle(PDPageContentStream contentStream, String title, float x, float y, PDFont font, int fontSize,
            Color color) throws IOException {
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(color);
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(title);
        contentStream.endText();
    }

    private void addText(PDPageContentStream contentStream, String text, float x, float y, PDFont font, int fontSize,
            Color color, float wrapWidth) throws IOException {
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(color);
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);

        String[] lines = text.split("\n");
        for (String line : lines) {
            List<String> wrappedLines = wrapText(line, wrapWidth, font, fontSize);
            for (String wrappedLine : wrappedLines) {
                contentStream.showText(wrappedLine);
                contentStream.newLineAtOffset(0, -fontSize - 5);
            }
        }

        contentStream.endText();
    }

    private void addImage(PDPageContentStream contentStream, byte[] imageBytes, float x, float y, float diameter,
            PDDocument document) throws IOException {
        if (imageBytes != null) {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            PDImageXObject profileImage = JPEGFactory.createFromImage(document, bufferedImage);

            contentStream.saveGraphicsState();
            float k = 0.552284749831f;
            float cx = x + diameter / 2;
            float cy = y + diameter / 2;
            float r = diameter / 2;

            contentStream.moveTo(cx + r, cy);
            contentStream.curveTo(cx + r, cy + k * r, cx + k * r, cy + r, cx, cy + r);
            contentStream.curveTo(cx - k * r, cy + r, cx - r, cy + k * r, cx - r, cy);
            contentStream.curveTo(cx - r, cy - k * r, cx - k * r, cy - r, cx, cy - r);
            contentStream.curveTo(cx + k * r, cy - r, cx + r, cy - k * r, cx + r, cy);

            contentStream.closePath();
            contentStream.clip();

            contentStream.drawImage(profileImage, x, y, diameter, diameter);
            contentStream.restoreGraphicsState();
        }
    }

    private List<String> wrapText(String text, float wrapWidth, PDFont font, int fontSize) throws IOException {
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        for (String word : text.split(" ")) {
            if (font.getStringWidth(currentLine.toString() + " " + word) / 1000 * fontSize > wrapWidth) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }
}