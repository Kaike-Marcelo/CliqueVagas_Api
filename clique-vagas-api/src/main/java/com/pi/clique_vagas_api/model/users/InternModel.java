package com.pi.clique_vagas_api.model.users;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "interns")
@DynamicUpdate
public class InternModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIntern;

    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "fk_user"))
    private Long idUser;

    @Column(name = "date_of_birth", length = 10, nullable = false)
    private String dateOfBirth;

    @Column(name = "sex", length = 15, nullable = false)
    private String sex;

    @Column(name = "educational_institution", length = 100, nullable = false)
    private String educationalInstitution;

    @Column(name = "area_of_interest", length = 100, nullable = false)
    private String areaOfInterest;

    @Column(name = "date_of_entry", length = 10, nullable = false)
    private String dateOfEntry;

    @Column(name = "date_of_completion", length = 10, nullable = false)
    private String dateOfCompletion;
}
