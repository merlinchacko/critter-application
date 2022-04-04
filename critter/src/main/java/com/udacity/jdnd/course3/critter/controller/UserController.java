package com.udacity.jdnd.course3.critter.controller;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.EmployeeRequest;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;

import lombok.AllArgsConstructor;


/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers would be fine too, though that is
 * not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController
{

    private CustomerService customerService;
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public Customer saveCustomer(@RequestBody Customer customer)
    {
        return customerService.save(customer);
    }

    @GetMapping("/customer")
    public List<Customer> getAllCustomers()
    {
        return customerService.getAll();
    }

    @GetMapping("/customer/pet/{petId}")
    public Customer getOwnerByPet(@PathVariable long petId)
    {
        return customerService.getOwnerByPet(petId);
    }

    @PostMapping("/employee")
    public Employee saveEmployee(@RequestBody Employee employee)
    {
        return employeeService.save(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public Employee getEmployee(@PathVariable long employeeId)
    {
        return employeeService.get(employeeId);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId)
    {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<Employee> findEmployeesForService(@RequestBody EmployeeRequest employee)
    {
        return employeeService.findEmployeesForService(employee);
    }
}
