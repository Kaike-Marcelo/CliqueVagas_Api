package com.pi.clique_vagas_api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.CertificateModel;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;
import com.pi.clique_vagas_api.repository.CertificateRepository;
import com.pi.clique_vagas_api.resources.dto.certificate.CertificateDto;
import com.pi.clique_vagas_api.resources.dto.certificate.CertificateWithIdDto;
import com.pi.clique_vagas_api.utils.DateUtils;
import com.pi.clique_vagas_api.utils.FileUtils;

import jakarta.transaction.Transactional;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    private static final String CERTIFICATE_DIR = "files/intern/certificates/";

    @Transactional
    public CertificateModel save(CertificateDto certificateDto, InternModel intern) {

        String url = null;
        try {
            if (certificateDto == null)
                throw new IllegalArgumentException("Certificate cannot be null");

            if (certificateDto.getCreditHours() < 0)
                throw new IllegalArgumentException("Credit hours cannot be negative");

            url = saveFile(certificateDto.getFile(), intern.getId());

            var certificate = new CertificateModel(
                    null,
                    intern,
                    certificateDto.getName(),
                    certificateDto.getDescription(),
                    certificateDto.getInstitution(),
                    certificateDto.getIssuanceDate(),
                    certificateDto.getCreditHours(),
                    url,
                    DateUtils.nowInZone(),
                    null);

            return certificateRepository.save(certificate);

        } catch (Exception e) {
            if (url != null) {
                try {
                    Files.deleteIfExists(Paths.get(url));
                } catch (IOException ex) {
                    throw new RuntimeException("Error deleting file after failure", ex);
                }
            }
            throw new RuntimeException("Error saving certificate", e);
        }
    }

    private String saveFile(MultipartFile file, Long userId) {
        Path filePath = null;
        try {
            String contentType = file.getContentType();

            if (!FileUtils.isValidContentTypeImageOrPdf(contentType))
                throw new IllegalArgumentException("File must be a PDF, PNG, JPG or JPEG");

            String nameFileOrigin = file.getOriginalFilename();
            String fileExtension = FileUtils.getFileExtension(nameFileOrigin);

            String fileName = ("Certificado_" + System.currentTimeMillis() + fileExtension);

            Path userDir = Paths.get(CERTIFICATE_DIR + userId);
            Files.createDirectories(userDir);
            filePath = userDir.resolve(fileName);
            Files.write(filePath, file.getBytes());

            return filePath.toString();

        } catch (IOException e) {
            if (filePath != null) {
                try {
                    Files.deleteIfExists(filePath);
                } catch (IOException ex) {
                    throw new RuntimeException("Error deleting file after failure", ex);
                }
            }
            throw new RuntimeException("Error creating directory or writing file", e);
        }
    }

    @Transactional
    public CertificateModel getCertificateById(Long certificateId) {
        return certificateRepository.findById(certificateId)
                .orElseThrow(() -> new EventNotFoundException("Certificate not found with ID: " + certificateId));
    }

    @Transactional
    public CertificateModel getCertificateByIdAndIntern(Long certificateId, InternModel intern) {
        return certificateRepository.findByIdAndInternId(certificateId, intern)
                .orElseThrow(() -> new EventNotFoundException(
                        "Certificate not found with ID: " + certificateId + " for this intern"));
    }

    @Transactional
    public List<CertificateWithIdDto> getCertificatesByInternId(InternModel intern) {
        List<CertificateWithIdDto> certificates = certificateRepository
                .findByInternId(intern)
                .stream()
                .map(certificate -> new CertificateWithIdDto(
                        certificate.getId(),
                        certificate.getName(),
                        certificate.getDescription(),
                        certificate.getInstitution(),
                        certificate.getIssuanceDate(),
                        certificate.getCreditHours(),
                        FileUtils.loadFileFromPath(certificate.getUrl())))
                .toList();
        return certificates;
    }

    @Transactional
    public void deleteCertificate(Long certificateId, InternModel intern) {
        try {
            var certificate = getCertificateByIdAndIntern(certificateId, intern);
            Files.deleteIfExists(Paths.get(certificate.getUrl()));
            certificateRepository.delete(certificate);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file", e);
        }
    }

}
