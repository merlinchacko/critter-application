package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.udacity.jdnd.course3.critter.model.PetType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "pet")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetTable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Enumerated(EnumType.STRING)
    private PetType type;
    private String name;
    @ManyToOne(targetEntity = CustomerTable.class)
    private CustomerTable customer;
    private LocalDate birthDate;
    private String notes;
}
