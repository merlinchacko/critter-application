package com.udacity.jdnd.course3.critter.model;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents a request to find available employees by skills. Does not map
 * to the database directly.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequest
{
    private Set<EmployeeSkill> skills;
    private LocalDate date;
}
