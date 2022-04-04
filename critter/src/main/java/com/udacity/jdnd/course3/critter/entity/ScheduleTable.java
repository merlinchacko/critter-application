package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.udacity.jdnd.course3.critter.model.EmployeeSkill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "schedule")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany(targetEntity = EmployeeTable.class)
    private List<EmployeeTable> employees;
    @ManyToMany(targetEntity = PetTable.class)
    private List<PetTable> pets;
    private LocalDate date;
    @ElementCollection
    private Set<EmployeeSkill> activities;
}
