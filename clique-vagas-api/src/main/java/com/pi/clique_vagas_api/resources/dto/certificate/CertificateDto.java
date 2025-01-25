package com.pi.clique_vagas_api.resources.dto.certificate;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDto {
    private String name;
    private String description;
    private String institution;
    private String issuanceDate;
    private Integer creditHours;
    private MultipartFile file;

}
