package com.udacity.jdnd.course3.critter.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.EmployeeTable;
import com.udacity.jdnd.course3.critter.exception.IdNotFoundException;
import com.udacity.jdnd.course3.critter.mapper.EmployeeMapper;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.EmployeeRequest;
import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;

import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class EmployeeService
{
    private EmployeeRepository employeeRepository;

    public Employee save(Employee employee)
    {

        EmployeeTable employeeTable = EmployeeMapper.mapToEntity(employee);
        return EmployeeMapper.mapToModel(employeeRepository.save(employeeTable));
    }

    public Employee get(long employeeId)
    {

        Optional<EmployeeTable> optional = employeeRepository.findById(employeeId);

        optional.orElseThrow(() -> new IdNotFoundException(employeeId));

        return EmployeeMapper.mapToModel(optional.get());
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId)
    {

        Optional<EmployeeTable> optional = employeeRepository.findById(employeeId);

        optional.orElseThrow(() -> new IdNotFoundException(employeeId));

        EmployeeTable employeeTable = optional.get();
        employeeTable.setDaysAvailable(daysAvailable);
        employeeRepository.save(employeeTable);
    }

    public List<Employee> findEmployeesForService(EmployeeRequest employeeRequest)
    {

        Set<EmployeeSkill> employeeSkills = employeeRequest.getSkills();

        List<EmployeeTable> employeeTables = employeeRepository.findAllByDaysAvailable(employeeRequest.getDate().getDayOfWeek());
        employeeTables.removeIf(employeeTable -> !employeeTable.getSkills().containsAll(employeeSkills));

        return EmployeeMapper.mapToList(employeeTables);
    }
}
