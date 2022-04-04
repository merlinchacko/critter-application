package com.udacity.jdnd.course3.critter.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.udacity.jdnd.course3.critter.entity.CustomerTable;
import com.udacity.jdnd.course3.critter.entity.PetTable;
import com.udacity.jdnd.course3.critter.model.Customer;


public final class CustomerMapper
{
    private CustomerMapper()
    {
        throw new IllegalStateException("Mapper class");
    }

    public static Customer mapToModel(CustomerTable customerTable)
    {
        return Customer.builder()
            .id(customerTable.getId())
            .name(customerTable.getName())
            .notes(customerTable.getNotes())
            .phoneNumber(customerTable.getPhoneNumber())
            .petIds(customerTable.getPets() != null ? customerTable.getPets().stream().map(PetTable::getId).collect(Collectors.toList()) : null)
            .build();
    }

    public static CustomerTable mapToEntity(Customer customer)
    {
        return CustomerTable.builder()
            .name(customer.getName())
            .notes(customer.getNotes())
            .phoneNumber(customer.getPhoneNumber())
            .build();
    }

    public static List<Customer> mapToList(List<CustomerTable> customerTables)
    {
        List<Customer> customers = new ArrayList<>();
        for (CustomerTable customerTable : customerTables)
        {
            customers.add(mapToModel(customerTable));
        }

        return customers;
    }

}
