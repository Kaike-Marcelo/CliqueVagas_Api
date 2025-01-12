package com.pi.clique_vagas_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.pi.clique_vagas_api.model.users.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserDetails findByEmail(String email);
}
