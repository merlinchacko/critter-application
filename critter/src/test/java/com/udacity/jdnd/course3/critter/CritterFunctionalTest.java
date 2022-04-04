package com.udacity.jdnd.course3.critter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.controller.UserController;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.EmployeeRequest;
import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import com.udacity.jdnd.course3.critter.controller.PetController;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.PetType;
import com.udacity.jdnd.course3.critter.controller.ScheduleController;
import com.udacity.jdnd.course3.critter.model.Schedule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is a set of functional tests to validate the basic capabilities desired for this application.
 * Students will need to configure the application to run these tests by adding application.properties file
 * to the test/resources directory that specifies the datasource. It can run using an in-memory H2 instance
 * and should not try to re-use the same datasource used by the rest of the app.
 *
 * These tests should all pass once the project is complete.
 */
@Transactional
@SpringBootTest(classes = CritterApplication.class)
public class CritterFunctionalTest {

    @Autowired
    private UserController userController;

    @Autowired
    private PetController petController;

    @Autowired
    private ScheduleController scheduleController;

    @Test
    public void testCreateCustomer(){
        Customer customer = createCustomerDTO();
        Customer newCustomer = userController.saveCustomer(customer);
        Customer retrievedCustomer = userController.getAllCustomers().get(0);
        Assertions.assertEquals(newCustomer.getName(), customer.getName());
        Assertions.assertEquals(newCustomer.getId(), retrievedCustomer.getId());
        Assertions.assertTrue(retrievedCustomer.getId() > 0);
    }

    @Test
    public void testCreateEmployee(){
        Employee employee = createEmployeeDTO();
        Employee newEmployee = userController.saveEmployee(employee);
        Employee retrievedEmployee = userController.getEmployee(newEmployee.getId());
        Assertions.assertEquals(employee.getSkills(), newEmployee.getSkills());
        Assertions.assertEquals(newEmployee.getId(), retrievedEmployee.getId());
        Assertions.assertTrue(retrievedEmployee.getId() > 0);
    }

    @Test
    public void testAddPetsToCustomer() {
        Customer customer = createCustomerDTO();
        Customer newCustomer = userController.saveCustomer(customer);

        Pet pet = createPetDTO();
        pet.setOwnerId(newCustomer.getId());
        Pet newPet = petController.savePet(pet);

        //make sure pet contains customer id
        Pet retrievedPet = petController.getPet(newPet.getId());
        Assertions.assertEquals(retrievedPet.getId(), newPet.getId());
        Assertions.assertEquals(retrievedPet.getOwnerId(), newCustomer.getId());

        //make sure you can retrieve pets by owner
        List<Pet> pets = petController.getPetsByOwner(newCustomer.getId());
        Assertions.assertEquals(newPet.getId(), pets.get(0).getId());
        Assertions.assertEquals(newPet.getName(), pets.get(0).getName());

        //check to make sure customer now also contains pet
        Customer retrievedCustomer = userController.getAllCustomers().get(0);
        Assertions.assertTrue(retrievedCustomer.getPetIds() != null && retrievedCustomer.getPetIds().size() > 0);
        Assertions.assertEquals(retrievedCustomer.getPetIds().get(0), retrievedPet.getId());
    }

    @Test
    public void testFindPetsByOwner() {
        Customer customer = createCustomerDTO();
        Customer newCustomer = userController.saveCustomer(customer);

        Pet pet = createPetDTO();
        pet.setOwnerId(newCustomer.getId());
        Pet newPet = petController.savePet(pet);
        pet.setType(PetType.DOG);
        pet.setName("DogName");
        Pet newPet2 = petController.savePet(pet);

        List<Pet> pets = petController.getPetsByOwner(newCustomer.getId());
        Assertions.assertEquals(pets.size(), 2);
        Assertions.assertEquals(pets.get(0).getOwnerId(), newCustomer.getId());
        Assertions.assertEquals(pets.get(0).getId(), newPet.getId());
    }

    @Test
    public void testFindOwnerByPet() {
        Customer customer = createCustomerDTO();
        Customer newCustomer = userController.saveCustomer(customer);

        Pet pet = createPetDTO();
        pet.setOwnerId(newCustomer.getId());
        Pet newPet = petController.savePet(pet);

        Customer owner = userController.getOwnerByPet(newPet.getId());
        Assertions.assertEquals(owner.getId(), newCustomer.getId());
        Assertions.assertEquals(owner.getPetIds().get(0), newPet.getId());
    }

    @Test
    public void testChangeEmployeeAvailability() {
        Employee employee = createEmployeeDTO();
        Employee emp1 = userController.saveEmployee(employee);
        Assertions.assertNull(emp1.getDaysAvailable());

        Set<DayOfWeek> availability = Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        userController.setAvailability(availability, emp1.getId());

        Employee emp2 = userController.getEmployee(emp1.getId());
        Assertions.assertEquals(availability, emp2.getDaysAvailable());
    }

    @Test
    public void testFindEmployeesByServiceAndTime() {
        Employee emp1 = createEmployeeDTO();
        Employee emp2 = createEmployeeDTO();
        Employee emp3 = createEmployeeDTO();

        emp1.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        emp2.setDaysAvailable(Sets.newHashSet(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        emp3.setDaysAvailable(Sets.newHashSet(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));

        emp1.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
        emp2.setSkills(Sets.newHashSet(EmployeeSkill.PETTING, EmployeeSkill.WALKING));
        emp3.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));

        Employee emp1n = userController.saveEmployee(emp1);
        Employee emp2n = userController.saveEmployee(emp2);
        Employee emp3n = userController.saveEmployee(emp3);

        //make a request that matches employee 1 or 2
        EmployeeRequest er1 = new EmployeeRequest();
        er1.setDate(LocalDate.of(2019, 12, 25)); //wednesday
        er1.setSkills(Sets.newHashSet(EmployeeSkill.PETTING));

        Set<Long> eIds1 = userController.findEmployeesForService(er1).stream().map(Employee::getId).collect(Collectors.toSet());
        Set<Long> eIds1expected = Sets.newHashSet(emp1n.getId(), emp2n.getId());
        Assertions.assertEquals(eIds1, eIds1expected);

        //make a request that matches only employee 3
        EmployeeRequest er2 = new EmployeeRequest();
        er2.setDate(LocalDate.of(2019, 12, 27)); //friday
        er2.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));

        Set<Long> eIds2 = userController.findEmployeesForService(er2).stream().map(Employee::getId).collect(Collectors.toSet());
        Set<Long> eIds2expected = Sets.newHashSet(emp3n.getId());
        Assertions.assertEquals(eIds2, eIds2expected);
    }

    @Test
    public void testSchedulePetsForServiceWithEmployee() {
        Employee employeeTemp = createEmployeeDTO();
        employeeTemp.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        Employee employee = userController.saveEmployee(employeeTemp);
        Customer customer = userController.saveCustomer(createCustomerDTO());
        Pet petTemp = createPetDTO();
        petTemp.setOwnerId(customer.getId());
        Pet pet = petController.savePet(petTemp);

        LocalDate date = LocalDate.of(2019, 12, 25);
        List<Long> petList = Lists.newArrayList(pet.getId());
        List<Long> employeeList = Lists.newArrayList(employee.getId());
        Set<EmployeeSkill> skillSet =  Sets.newHashSet(EmployeeSkill.PETTING);

        scheduleController.createSchedule(createScheduleDTO(petList, employeeList, date, skillSet));
        Schedule schedule = scheduleController.getAllSchedules().get(0);

        Assertions.assertEquals(schedule.getActivities(), skillSet);
        Assertions.assertEquals(schedule.getDate(), date);
        Assertions.assertEquals(schedule.getEmployeeIds(), employeeList);
        Assertions.assertEquals(schedule.getPetIds(), petList);
    }

    @Test
    public void testFindScheduleByEntities() {
        Schedule sched1 = populateSchedule(1, 2, LocalDate.of(2019, 12, 25), Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.WALKING));
        Schedule sched2 = populateSchedule(3, 1, LocalDate.of(2019, 12, 26), Sets.newHashSet(EmployeeSkill.PETTING));

        //add a third schedule that shares some employees and pets with the other schedules
        Schedule sched3 = new Schedule();
        sched3.setEmployeeIds(sched1.getEmployeeIds());
        sched3.setPetIds(sched2.getPetIds());
        sched3.setActivities(Sets.newHashSet(EmployeeSkill.SHAVING, EmployeeSkill.PETTING));
        sched3.setDate(LocalDate.of(2020, 3, 23));
        scheduleController.createSchedule(sched3);

        /*
            We now have 3 schedule entries. The third schedule entry has the same employees as the 1st schedule
            and the same pets/owners as the second schedule. So if we look up schedule entries for the employee from
            schedule 1, we should get both the first and third schedule as our result.
         */

        //EmployeeTable 1 in is both schedule 1 and 3
        List<Schedule> scheds1e = scheduleController.getScheduleForEmployee(sched1.getEmployeeIds().get(0));
        compareSchedules(sched1, scheds1e.get(0));
        compareSchedules(sched3, scheds1e.get(1));

        //EmployeeTable 2 is only in schedule 2
        List<Schedule> scheds2e = scheduleController.getScheduleForEmployee(sched2.getEmployeeIds().get(0));
        compareSchedules(sched2, scheds2e.get(0));

        //PetTable 1 is only in schedule 1
        List<Schedule> scheds1p = scheduleController.getScheduleForPet(sched1.getPetIds().get(0));
        compareSchedules(sched1, scheds1p.get(0));

        //PetTable from schedule 2 is in both schedules 2 and 3
        List<Schedule> scheds2p = scheduleController.getScheduleForPet(sched2.getPetIds().get(0));
        compareSchedules(sched2, scheds2p.get(0));
        compareSchedules(sched3, scheds2p.get(1));

        //Owner of the first pet will only be in schedule 1
        List<Schedule> scheds1c = scheduleController.getScheduleForCustomer(userController.getOwnerByPet(sched1.getPetIds().get(0)).getId());
        compareSchedules(sched1, scheds1c.get(0));

        //Owner of pet from schedule 2 will be in both schedules 2 and 3
        List<Schedule> scheds2c = scheduleController.getScheduleForCustomer(userController.getOwnerByPet(sched2.getPetIds().get(0)).getId());
        compareSchedules(sched2, scheds2c.get(0));
        compareSchedules(sched3, scheds2c.get(1));
    }


    private static Employee createEmployeeDTO() {
        Employee employee = new Employee();
        employee.setName("TestEmployee");
        employee.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
        return employee;
    }
    private static Customer createCustomerDTO() {
        Customer customer = new Customer();
        customer.setName("TestEmployee");
        customer.setPhoneNumber("123-456-789");
        return customer;
    }

    private static Pet createPetDTO() {
        Pet pet = new Pet();
        pet.setName("TestPet");
        pet.setType(PetType.CAT);
        return pet;
    }

    private static EmployeeRequest createEmployeeRequestDTO() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setDate(LocalDate.of(2019, 12, 25));
        employeeRequest.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.WALKING));
        return employeeRequest;
    }

    private static Schedule createScheduleDTO(List<Long> petIds, List<Long> employeeIds, LocalDate date, Set<EmployeeSkill> activities) {
        Schedule schedule = new Schedule();
        schedule.setPetIds(petIds);
        schedule.setEmployeeIds(employeeIds);
        schedule.setDate(date);
        schedule.setActivities(activities);
        return schedule;
    }

    private Schedule populateSchedule(int numEmployees, int numPets, LocalDate date, Set<EmployeeSkill> activities) {
        List<Long> employeeIds = IntStream.range(0, numEmployees)
                .mapToObj(i -> createEmployeeDTO())
                .map(e -> {
                    e.setSkills(activities);
                    e.setDaysAvailable(Sets.newHashSet(date.getDayOfWeek()));
                    return userController.saveEmployee(e).getId();
                }).collect(Collectors.toList());
        Customer cust = userController.saveCustomer(createCustomerDTO());
        List<Long> petIds = IntStream.range(0, numPets)
                .mapToObj(i -> createPetDTO())
                .map(p -> {
                    p.setOwnerId(cust.getId());
                    return petController.savePet(p).getId();
                }).collect(Collectors.toList());
        return scheduleController.createSchedule(createScheduleDTO(petIds, employeeIds, date, activities));
    }

    private static void compareSchedules(Schedule sched1, Schedule sched2) {
        Assertions.assertEquals(sched1.getPetIds(), sched2.getPetIds());
        Assertions.assertEquals(sched1.getActivities(), sched2.getActivities());
        Assertions.assertEquals(sched1.getEmployeeIds(), sched2.getEmployeeIds());
        Assertions.assertEquals(sched1.getDate(), sched2.getDate());
    }

}
