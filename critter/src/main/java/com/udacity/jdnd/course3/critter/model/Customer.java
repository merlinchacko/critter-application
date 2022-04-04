package com.udacity.jdnd.course3.critter.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents the form that customer request and response data takes. Does not map
 * to the database directly.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer
{
    private long id;
    private String name;
    private String phoneNumber;
    private String notes;
    private List<Long> petIds;
}
