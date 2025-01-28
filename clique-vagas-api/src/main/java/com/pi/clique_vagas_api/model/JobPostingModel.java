package com.pi.clique_vagas_api.model;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.pi.clique_vagas_api.model.users.typeUsers.CompanyModel;
import com.pi.clique_vagas_api.resources.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job_posting")
@DynamicUpdate
public class JobPostingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyModel company;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @Column(name = "job_posting_status", length = 20, nullable = false)
    private Status jobPostingStatus;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "application_deadline", nullable = false)
    private ZonedDateTime applicationDeadline;

    @CreationTimestamp
    private ZonedDateTime publicationDate;

    @UpdateTimestamp
    private ZonedDateTime updateAt;
}
