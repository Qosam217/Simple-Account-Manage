package com.demo.simple_account.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="biodatas")
@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Biodata extends BaseEntity{

    @Column(name = "fullname")
    @NotEmpty(message = "Fullname is required")
    @Size(min = 3, max = 50, message = "Fullname must between 3 - 50 character")
    private String fullname;

    @Column(name="dob")
    private LocalDate dob;

    @Column(name="mobile_phone")
    @Size(min=10, max=14, message="Mobile phone must between 10 - 14 character")
    private int mobilePhone;

    @OneToOne(mappedBy="biodata")
    @JsonManagedReference
    private User user;
}
