package com.pi.clique_vagas_api.repository.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;

@Repository
public interface InternRepository extends JpaRepository<InternModel, Long> {

    Optional<InternModel> findByUserId(UserModel user);
}
