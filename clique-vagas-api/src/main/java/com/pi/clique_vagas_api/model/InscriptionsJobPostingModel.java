package com.pi.clique_vagas_api.model;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.pi.clique_vagas_api.model.users.typeUsers.InternModel;

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
@Table(name = "inscriptions")
@DynamicUpdate
public class InscriptionsJobPostingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPostingModel jobPostingId;

    @ManyToOne
    @JoinColumn(name = "intern_id", nullable = false)
    private InternModel internId;

    // @Column(name = "status", length = 20, nullable = false)
    // private String status;

    @Column(name = "pontuation", nullable = false)
    private Integer pontuation;

    @CreationTimestamp
    private ZonedDateTime inscriptionDate;

    @UpdateTimestamp
    private ZonedDateTime updateAt;
}
