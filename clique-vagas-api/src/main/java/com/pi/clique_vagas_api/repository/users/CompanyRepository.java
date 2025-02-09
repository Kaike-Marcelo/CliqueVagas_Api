package com.pi.clique_vagas_api.repository.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.model.users.typeUsers.CompanyModel;

public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {

    Optional<CompanyModel> findByUserId(UserModel user);

    CompanyModel findByCnpj(String cnpj);

    Boolean deleteByUserId(UserModel user);

}
