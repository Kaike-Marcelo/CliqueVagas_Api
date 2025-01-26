package com.pi.clique_vagas_api.model.users.typeUsers;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.pi.clique_vagas_api.model.users.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "companies")
@DynamicUpdate
public class CompanyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserModel userId;

    @Column(name = "company_name", length = 150, nullable = false)
    private String companyName;

    @Column(name = "cnpj", length = 18, nullable = false)
    private String cnpj;

    @Column(name = "telephone_responsible", length = 20, nullable = false)
    private String telephoneResponsible;

    @Column(name = "sector_of_operation", length = 100, nullable = false)
    private String sectorOfOperation;

    @Column(name = "website_link", length = 200, nullable = false)
    private String websiteLink;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

}
