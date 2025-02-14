package com.pi.clique_vagas_api.model.jobPost;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.pi.clique_vagas_api.model.users.UserModel;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_posting_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private JobPostingModel jobPostingId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel userId;

    // @Column(name = "status", length = 20, nullable = false)
    // private String status;

    @Column(name = "pontuation", nullable = false)
    private Double pontuation;

    @CreationTimestamp
    private ZonedDateTime inscriptionDate;

    @UpdateTimestamp
    private ZonedDateTime updateAt;
}
