package com.udacity.jdnd.course3.critter.mapper;

import java.util.ArrayList;
import java.util.List;

import com.udacity.jdnd.course3.critter.entity.EmployeeTable;
import com.udacity.jdnd.course3.critter.model.Employee;


public final class EmployeeMapper
{
    private EmployeeMapper()
    {
        throw new IllegalStateException("Mapper class");
    }

    public static Employee mapToModel(EmployeeTable employeeTable)
    {
        return Employee.builder()
            .id(employeeTable.getId())
            .name(employeeTable.getName())
            .daysAvailable(employeeTable.getDaysAvailable())
            .skills(employeeTable.getSkills())
            .build();
    }

    public static EmployeeTable mapToEntity(Employee employee)
    {
        return EmployeeTable.builder()
            .name(employee.getName())
            .daysAvailable(employee.getDaysAvailable())
            .skills(employee.getSkills())
            .build();
    }

    public static List<Employee> mapToList(List<EmployeeTable> employeeTables)
    {
        List<Employee> employees = new ArrayList<>();
        for (EmployeeTable employeeTable : employeeTables)
        {
            employees.add(mapToModel(employeeTable));
        }
        return employees;
    }
}
