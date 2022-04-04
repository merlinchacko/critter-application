package com.udacity.jdnd.course3.critter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.entity.EmployeeTable;
import com.udacity.jdnd.course3.critter.entity.PetTable;
import com.udacity.jdnd.course3.critter.entity.ScheduleTable;
import com.udacity.jdnd.course3.critter.mapper.ScheduleMapper;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class ScheduleService
{

    private ScheduleRepository scheduleRepository;
    private EmployeeRepository employeeRepository;
    private PetRepository petRepository;

    public Schedule save(Schedule schedule)
    {
        ScheduleTable scheduleTable = ScheduleMapper.mapToEntity(schedule);
        scheduleTable.setEmployees(schedule.getEmployeeIds().stream().map(employeeId -> employeeRepository.getOne(employeeId)).collect(Collectors.toList()));
        scheduleTable.setPets(schedule.getPetIds().stream().map(petId -> petRepository.getOne(petId)).collect(Collectors.toList()));

        return ScheduleMapper.mapToModel(scheduleRepository.save(scheduleTable));
    }

    public List<Schedule> getAll()
    {
        return ScheduleMapper.mapToList(scheduleRepository.findAll());
    }

    public List<Schedule> getScheduleForPet(long petId)
    {

        PetTable petTable = petRepository.getOne(petId);
        return ScheduleMapper.mapToList(scheduleRepository.findAllByPets(petTable));
    }

    public List<Schedule> getScheduleForEmployee(long employeeId)
    {
        EmployeeTable employeeTable = employeeRepository.getOne(employeeId);
        return ScheduleMapper.mapToList(scheduleRepository.findAllByEmployees(employeeTable));
    }

    public List<Schedule> getScheduleForCustomer(long customerId)
    {
        List<PetTable> pets = petRepository.findPetByCustomerId(customerId);
        return ScheduleMapper.mapToList(scheduleRepository.findAllByPetsIn(pets));
    }

}
