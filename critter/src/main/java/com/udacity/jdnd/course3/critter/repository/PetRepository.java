package com.udacity.jdnd.course3.critter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.entity.PetTable;


@Repository
public interface PetRepository extends JpaRepository<PetTable,Long> {
    List<PetTable> findPetByCustomerId(Long customerId);
}
