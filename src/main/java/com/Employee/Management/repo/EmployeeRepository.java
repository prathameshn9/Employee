package com.Employee.Management.repo;

import com.Employee.Management.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(e.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Employee> searchEmployees(String query);

    Page<Employee> findAll(Pageable pageable);
}
