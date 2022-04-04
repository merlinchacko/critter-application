package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.CustomerTable;
import com.udacity.jdnd.course3.critter.entity.PetTable;
import com.udacity.jdnd.course3.critter.exception.IdNotFoundException;
import com.udacity.jdnd.course3.critter.mapper.PetMapper;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;

import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class PetService
{
    private PetRepository petRepository;
    private CustomerRepository customerRepository;

    public Pet save(final Pet pet)
    {
        PetTable petTable = PetMapper.mapToEntity(pet);
        CustomerTable customer = customerRepository.findById(pet.getOwnerId()).orElseThrow(() -> new IdNotFoundException(pet.getOwnerId()));
        petTable.setCustomer(customer);
        petTable = petRepository.save(petTable);

        customer.setPets(new ArrayList<>(Arrays.asList(petTable)));
        customerRepository.save(customer);
        return PetMapper.mapToModel(petTable);

    }

    public Pet get(final long petId)
    {
        PetTable petTable = petRepository.findById(petId).orElseThrow(() -> new IdNotFoundException(petId));

        return PetMapper.mapToModel(petTable);
    }

    public List<Pet> getAll()
    {
        return PetMapper.mapToList(petRepository.findAll());

    }

    public List<Pet> getPetsByOwner(final long ownerId)
    {
        return PetMapper.mapToList(petRepository.findPetByCustomerId(ownerId));
    }
}
