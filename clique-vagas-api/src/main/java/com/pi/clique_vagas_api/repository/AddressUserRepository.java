package com.pi.clique_vagas_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.clique_vagas_api.model.AddressModel;
import com.pi.clique_vagas_api.model.users.UserModel;

@Repository
public interface AddressUserRepository extends JpaRepository<AddressModel, Long> {

    AddressModel findByUser(UserModel user);

}
