package com.demo.simple_account.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements UserDetails{

    @Column(name="username", length=100)
    @Size(min=2, max=100, message="Username must be between 2 - 100 character")
    @NotBlank(message="Username must be filled")
    private String username;

    @Column(name = "password", length=100)
    @NotBlank(message="Password must be filled")
    @Size(min=8, max=100, message="Password must be between 8 - 100 character")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z](?=.*[A-Z])(?=.*[A-Z])(?=.*[~!@#$%^&*()\\\\-_\\+=\\\\{\\\\}\\\n" + //
                        "\n" + //
                        "\\[\\\\]\n" + //
                        "\n" + //
                        "\\\\\\\\|;:'<>,./?]).*$",
        message="Password must contain at least one digit, one lowercase character, one uppercase character, and one special character"
    )
    private String password;

    @OneToOne
    @JoinColumn(name = "biodata_id", insertable = false, updatable = false)
    @JsonManagedReference
    private Biodata biodata;

    @Column(name = "biodata_id")
    private Long biodataId;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    @JsonManagedReference
    private Role role;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "email", length = 100)
    @NotBlank(message="Email must be filled")
    @Email(message="Email should be valid")
    private String email;

    @Column(name = "is_locked", columnDefinition = "boolean default false")
    private Boolean isLocked = false;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == null){
            return Collections.emptyList();
        }
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    
}
