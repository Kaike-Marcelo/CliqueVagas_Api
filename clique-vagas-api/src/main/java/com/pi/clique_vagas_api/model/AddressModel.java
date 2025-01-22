package com.pi.clique_vagas_api.model;

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
@Table(name = "address_users")
@DynamicUpdate
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Column(name = "cep", length = 9)
    @NotNull
    private String cep;

    @Column(name = "street", length = 150)
    @NotNull
    private String street;

    @Column(name = "number", length = 10)
    private String number;

    @Column(name = "neighborhood", length = 150)
    @NotNull
    private String neighborhood;

    @Column(name = "city", length = 150)
    @NotNull
    private String city;

    @Column(name = "state", length = 150)
    @NotNull
    private String state;

    @Column(name = "country", length = 150)
    @NotNull
    private String country;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;
}
