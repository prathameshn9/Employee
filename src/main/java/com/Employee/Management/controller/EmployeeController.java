package com.Employee.Management.controller;

import com.Employee.Management.exceptions.GlobalExceptionHandler;
import com.Employee.Management.services.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.Employee.Management.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.List;

@Tag(name = "Employee API", description = "Endpoints for managing employees")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Create a new employee")
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @Operation(summary = "Get employee by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Employee not found with ID: " + id);
        }
        return ResponseEntity.ok(employee);
    }

    @Operation(summary = "Update an employee")
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        if (updatedEmployee == null) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Cannot update. Employee not found with ID: " + id);
        }
        return ResponseEntity.ok(updatedEmployee);
    }

    @Operation(summary = "Delete an employee")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (!deleted) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Cannot delete. Employee not found with ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all employees (Paginated)")
    @GetMapping
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String sort) {
        Page<Employee> employees = employeeService.getAllEmployees(page, size, sort);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam String query) {
        List<Employee> employees = employeeService.searchEmployees(query);
        if (employees.isEmpty()) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("No employees found matching query: " + query);
        }
        return ResponseEntity.ok(employees);
    }
}
