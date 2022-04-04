package com.udacity.jdnd.course3.critter.repository;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.entity.EmployeeTable;


@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeTable, Long>
{

    List<EmployeeTable> findAllByDaysAvailable(DayOfWeek dayOfWeek);
}
