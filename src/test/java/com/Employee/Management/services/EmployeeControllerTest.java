package com.Employee.Management.services;

import com.Employee.Management.controller.EmployeeController;
import com.Employee.Management.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee() {
        
        Employee employee = new Employee(1L, "John Doe", "john.doe@example.com", "Manager", "Sales", "1234567890");
        employee.setName("John Doe");
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

        
        ResponseEntity<Employee> response = employeeController.createEmployee(employee);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        verify(employeeService, times(1)).createEmployee(any(Employee.class));
    }

    @Test
    void testGetEmployeeById() {
        
        Employee employee = new Employee(1L, "John Doe", "john.doe@example.com", "Manager", "Sales", "1234567890");
        employee.setId(1L);
        employee.setName("John Doe");
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        
        ResponseEntity<Employee> response = employeeController.getEmployeeById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("John Doe", response.getBody().getName());
        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    void testUpdateEmployee() {
        
        Employee employee = new Employee(1L, "John Doe", "john.doe@example.com", "Manager", "Sales", "1234567890");
        employee.setId(1L);
        employee.setName("John Doe Updated");
        when(employeeService.updateEmployee(1L, employee)).thenReturn(employee);

        
        ResponseEntity<Employee> response = employeeController.updateEmployee(1L, employee);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("John Doe Updated", response.getBody().getName());
        verify(employeeService, times(1)).updateEmployee(1L, employee);
    }

    @Test
    void testDeleteEmployee() {
       
        when(employeeService.deleteEmployee(1L)).thenReturn(true);

       
        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    void testGetAllEmployees() {
        
        Employee employee = new Employee(1L, "John Doe", "john.doe@example.com", "Manager", "Sales", "1234567890");
        employee.setId(1L);
        employee.setName("John Doe");
        Page<Employee> employeePage = new PageImpl<>(Collections.singletonList(employee));
        when(employeeService.getAllEmployees(0, 10, "name,asc")).thenReturn(employeePage);

       
        ResponseEntity<Page<Employee>> response = employeeController.getAllEmployees(0, 10, "name,asc");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        verify(employeeService, times(1)).getAllEmployees(0, 10, "name,asc");
    }

    @Test
    void testSearchEmployees() {
       
        Employee employee = new Employee(1L, "John Doe", "john.doe@example.com", "Manager", "Sales", "1234567890");
        employee.setId(1L);
        employee.setName("John Doe");
        when(employeeService.searchEmployees("John")).thenReturn(Collections.singletonList(employee));

       
        ResponseEntity<List<Employee>> response = employeeController.searchEmployees("John");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getName());
        verify(employeeService, times(1)).searchEmployees("John");
    }
}