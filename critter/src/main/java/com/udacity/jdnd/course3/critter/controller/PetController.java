package com.udacity.jdnd.course3.critter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;

import lombok.AllArgsConstructor;


/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
@AllArgsConstructor
public class PetController
{
    private PetService petService;

    @PostMapping
    public Pet savePet(@RequestBody Pet pet)
    {
        return petService.save(pet);
    }

    @GetMapping("/{petId}")
    public Pet getPet(@PathVariable long petId)
    {
        return petService.get(petId);
    }

    @GetMapping
    public List<Pet> getPets()
    {
        return petService.getAll();
    }

    @GetMapping("/owner/{ownerId}")
    public List<Pet> getPetsByOwner(@PathVariable long ownerId)
    {
        return petService.getPetsByOwner(ownerId);
    }
}
