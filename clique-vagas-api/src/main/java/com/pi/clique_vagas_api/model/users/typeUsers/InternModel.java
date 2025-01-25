package com.pi.clique_vagas_api.model.users.typeUsers;

import java.sql.Date;
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
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserModel userId;

    @Column(name = "date_of_birth", length = 10)
    @NotNull
    private Date dateOfBirth;

    @Column(name = "sex", length = 15)
    @NotNull
    private String sex;

    @Column(name = "educational_institution", length = 100)
    @NotNull
    private String educatinoalInstitution;

    @Column(name = "area_of_interest", length = 100)
    @NotNull
    private String areaOfInterest;

    @Column(name = "year_of_entry", length = 4)
    @NotNull
    private String yearOfEntry;

    @Column(name = "expected_graduation_date", length = 10)
    @NotNull
    private Date expectedGraduationDate;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

}
