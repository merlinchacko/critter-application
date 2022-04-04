package com.udacity.jdnd.course3.critter.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;

import lombok.AllArgsConstructor;


/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
@AllArgsConstructor
public class ScheduleController {

    private ScheduleService scheduleService;

    @PostMapping
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.save(schedule);
    }

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAll();
    }

    @GetMapping("/pet/{petId}")
    public List<Schedule> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.getScheduleForPet(petId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Schedule> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getScheduleForEmployee(employeeId);
    }

    @GetMapping("/customer/{customerId}")
    public List<Schedule> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getScheduleForCustomer(customerId);
    }
}
