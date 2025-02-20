package com.Employee.Management.services;

import com.Employee.Management.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.Employee.Management.models.*;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setName(employeeDetails.getName());
            employee.setEmail(employeeDetails.getEmail());
            employee.setDesignation(employeeDetails.getDesignation());
            employee.setDepartment(employeeDetails.getDepartment());
            employee.setPhone(employeeDetails.getPhone());
            return employeeRepository.save(employee);
        }
        return null;
    }

    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true; 
        }
        return false;
    }

    public Page<Employee> getAllEmployees(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return employeeRepository.findAll(PageRequest.of(page, size, Sort.by(direction, sortParams[0])));
    }

    public List<Employee> searchEmployees(String query) {
        return employeeRepository.searchEmployees(query);
    }
}