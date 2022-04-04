package com.udacity.jdnd.course3.critter.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.udacity.jdnd.course3.critter.entity.EmployeeTable;
import com.udacity.jdnd.course3.critter.entity.PetTable;
import com.udacity.jdnd.course3.critter.entity.ScheduleTable;
import com.udacity.jdnd.course3.critter.model.Schedule;


public final class ScheduleMapper
{
    private ScheduleMapper()
    {
        throw new IllegalStateException("Mapper class");
    }

    public static Schedule mapToModel(ScheduleTable scheduleTable)
    {
        return Schedule.builder()
            .id(scheduleTable.getId())
            .date(scheduleTable.getDate())
            .activities(scheduleTable.getActivities())
            .petIds(scheduleTable.getPets() != null ? scheduleTable.getPets().stream().map(PetTable::getId).collect(Collectors.toList()) : null)
            .employeeIds(scheduleTable.getEmployees() != null ?
                scheduleTable.getEmployees().stream().map(EmployeeTable::getId).collect(Collectors.toList()) :
                null)
            .build();
    }

    public static ScheduleTable mapToEntity(Schedule schedule)
    {
        return ScheduleTable.builder()
            .date(schedule.getDate())
            .activities(schedule.getActivities())
            .build();
    }

    public static List<Schedule> mapToList(List<ScheduleTable> scheduleTables)
    {
        List<Schedule> schedules = new ArrayList<>();
        for (ScheduleTable scheduleTable : scheduleTables)
        {
            schedules.add(mapToModel(scheduleTable));
        }
        return schedules;
    }
}
