package com.udacity.jdnd.course3.critter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.CustomerTable;
import com.udacity.jdnd.course3.critter.mapper.CustomerMapper;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;

import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class CustomerService
{

    private PetRepository petRepository;
    private CustomerRepository customerRepository;

    public Customer save(Customer customer)
    {
        CustomerTable customerTable = CustomerMapper.mapToEntity(customer);
        if(customer.getPetIds()!=null) {
            customerTable.setPets(customer.getPetIds().stream().map(petId -> petRepository.getOne(petId)).collect(Collectors.toList()));
        }
        return CustomerMapper.mapToModel(customerRepository.save(customerTable));
    }

    public List<Customer> getAll()
    {
        return CustomerMapper.mapToList(customerRepository.findAll());
    }

    public Customer getOwnerByPet(long petId)
    {
        return CustomerMapper.mapToModel(petRepository.getOne(petId).getCustomer());
    }
}
