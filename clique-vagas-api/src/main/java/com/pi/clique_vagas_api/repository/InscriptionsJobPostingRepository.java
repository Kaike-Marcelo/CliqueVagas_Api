package com.pi.clique_vagas_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.clique_vagas_api.model.InscriptionsJobPostingModel;

@Repository
public interface InscriptionsJobPostingRepository extends JpaRepository<InscriptionsJobPostingModel, Long> {

}
