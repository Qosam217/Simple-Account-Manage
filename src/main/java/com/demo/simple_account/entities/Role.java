package com.demo.simple_account.entities;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="roles")
@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity{

    @Column(name="name", length=20)
    @NotNull(message="Role Name must filled")
    private String name;

    @OneToMany(mappedBy="role")
    @JsonBackReference
    private List<User> users;
}
