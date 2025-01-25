package com.pi.clique_vagas_api.resources.dto.certificate;

public record CertificateWithIdDto(
                Long id,
                String name,
                String description,
                String institution,
                String issuanceDate,
                Integer creditHours,
                byte[] file) {
}
