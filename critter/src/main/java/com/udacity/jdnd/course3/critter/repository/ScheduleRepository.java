package com.udacity.jdnd.course3.critter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.entity.EmployeeTable;
import com.udacity.jdnd.course3.critter.entity.PetTable;
import com.udacity.jdnd.course3.critter.entity.ScheduleTable;


@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleTable, Long>
{
    List<ScheduleTable> findAllByPets(PetTable pet);

    List<ScheduleTable> findAllByEmployees(EmployeeTable employee);

    List<ScheduleTable> findAllByPetsIn(List<PetTable> pets);
}
