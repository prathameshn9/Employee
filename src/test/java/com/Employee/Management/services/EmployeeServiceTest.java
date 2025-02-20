package com.Employee.Management.services;

import com.Employee.Management.exceptions.GlobalExceptionHandler.ResourceNotFoundException;
import com.Employee.Management.models.Employee;
import com.Employee.Management.repo.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee() {
        Employee employee = new Employee(1L, "John Doe", "john.doe@example.com", "Manager", "Sales", "1234567890");

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee createdEmployee = employeeService.createEmployee(employee);

        assertNotNull(createdEmployee);
        assertEquals("John Doe", createdEmployee.getName());
        assertEquals("john.doe@example.com", createdEmployee.getEmail());

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee(1L, "John Doe", "john.doe@example.com", "Manager", "Sales", "1234567890");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.getEmployeeById(1L);

        assertNotNull(foundEmployee);
        assertEquals(1L, foundEmployee.getId());
        assertEquals("John Doe", foundEmployee.getName());

        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(1L));

        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateEmployee() {
        Employee existingEmployee = new Employee(1L, "John Doe", "john.doe@example.com", "Manager", "Sales", "1234567890");
        Employee updatedDetails = new Employee(1L, "Jane Doe", "jane.doe@example.com", "Lead", "HR", "9876543210");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedDetails);

        Employee updatedEmployee = employeeService.updateEmployee(1L, updatedDetails);

        assertNotNull(updatedEmployee);
        assertEquals("Jane Doe", updatedEmployee.getName());
        assertEquals("jane.doe@example.com", updatedEmployee.getEmail());

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(argThat(emp ->
                emp.getName().equals("Jane Doe") && emp.getEmail().equals("jane.doe@example.com")));
    }

    @Test
    void testUpdateEmployee_NotFound() {
        Employee updatedDetails = new Employee(1L, "Jane Doe", "jane.doe@example.com", "Lead", "HR", "9876543210");

        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(1L, updatedDetails));

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testDeleteEmployee() {
        when(employeeRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = employeeService.deleteEmployee(1L);

        assertTrue(isDeleted);

        verify(employeeRepository, times(1)).existsById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(1L));

        verify(employeeRepository, times(1)).existsById(1L);
        verify(employeeRepository, never()).deleteById(1L);
    }

    @Test
    void testGetAllEmployees() {
        Employee employee1 = new Employee(1L, "John Doe", "john.doe@example.com", "Manager", "Sales", "1234567890");
        Employee employee2 = new Employee(2L, "Jane Doe", "jane.doe@example.com", "Lead", "HR", "9876543210");

        Page<Employee> page = new PageImpl<>(Arrays.asList(employee1, employee2));

        when(employeeRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"))))
                .thenReturn(page);

        Page<Employee> result = employeeService.getAllEmployees(0, 10, "name,asc");

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getName());
        assertEquals("Jane Doe", result.getContent().get(1).getName());

        assertTrue(result.getContent().get(0).getName().compareTo(result.getContent().get(1).getName()) < 0);

        verify(employeeRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")));
    }

    @Test
    void testGetAllEmployees_EmptyDatabase() {
        Page<Employee> emptyPage = new PageImpl<>(List.of());

        when(employeeRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"))))
                .thenReturn(emptyPage);

        Page<Employee> result = employeeService.getAllEmployees(0, 10, "name,asc");

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());

        verify(employeeRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")));
    }

    @Test
    void testSearchEmployees() {
        Employee employee1 = new Employee(1L, "John Doe", "john.doe@example.com", "Manager", "Sales", "1234567890");
        Employee employee2 = new Employee(2L, "Jane Doe", "jane.doe@example.com", "Lead", "HR", "9876543210");

        when(employeeRepository.searchEmployees("Doe")).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> result = employeeService.searchEmployees("Doe");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());

        verify(employeeRepository, times(1)).searchEmployees("Doe");
    }
}
