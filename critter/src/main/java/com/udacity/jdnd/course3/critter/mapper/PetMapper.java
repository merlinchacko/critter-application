package com.udacity.jdnd.course3.critter.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.udacity.jdnd.course3.critter.entity.PetTable;
import com.udacity.jdnd.course3.critter.model.Pet;


public final class PetMapper
{
    private PetMapper()
    {
        throw new IllegalStateException("Mapper class");
    }
    public static Pet mapToModel(PetTable petTable)
    {
        return Pet.builder()
            .id(petTable.getId())
            .name(petTable.getName())
            .birthDate(petTable.getBirthDate())
            .notes(petTable.getNotes())
            .type(petTable.getType())
            .ownerId(Optional.of(petTable.getCustomer()).get().getId())
            .build();
    }

    public static PetTable mapToEntity(Pet pet)
    {
        return PetTable.builder()
            .name(pet.getName())
            .birthDate(pet.getBirthDate())
            .notes(pet.getNotes())
            .type(pet.getType())
            .build();
    }

    public static List<Pet> mapToList(List<PetTable> petTables)
    {
        List<Pet> pets = new ArrayList<>();
        for (PetTable petTable : petTables)
        {
            pets.add(mapToModel(petTable));
        }
        return pets;
    }
}
