package com.demo.simple_account.entities;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    @Column(name="dob")
    private LocalDate  dob;

    @Column(name="mobile_phone")
    @Size(min=10, max=14, message="Mobile phone must between 10 - 14 character")
    private int mobilePhone;

    @OneToOne(mappedBy="biodata")
    @JsonBackReference
    private User user;
}
