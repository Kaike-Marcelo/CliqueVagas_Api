package com.pi.clique_vagas_api.model.users;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pi.clique_vagas_api.resources.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "users")
@DynamicUpdate
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "first_name", length = 50)
    @NotNull
    private String firstName;

    @Column(name = "last_name", length = 100)
    @NotNull
    private String lastName;

    @Column(name = "url_image_profile", length = 255)
    private String urlImageProfile;

    @Column(name = "role", length = 50)
    @NotNull
    private UserRole role;

    @Column(name = "cpf", length = 14, unique = true)
    @NotNull
    private String cpf;

    @Column(name = "phone", length = 20)
    @NotNull
    private String phone;

    @Column(name = "email", length = 150, unique = true)
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        if (this.role == UserRole.COMPANY)
            return List.of(new SimpleGrantedAuthority("ROLE_COMPANY"), new SimpleGrantedAuthority("ROLE_USER"));
        if (this.role == UserRole.INTERN)
            return List.of(new SimpleGrantedAuthority("ROLE_INTERN"), new SimpleGrantedAuthority("ROLE_USER"));
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
