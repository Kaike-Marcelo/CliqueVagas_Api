package com.pi.clique_vagas_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.clique_vagas_api.model.CertificateModel;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateModel, Long> {

    List<CertificateModel> findByInternId(InternModel intern);

    Optional<CertificateModel> findByIdAndInternId(Long id, InternModel intern);

}
