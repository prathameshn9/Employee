package com.Employee.Management.models;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String email;
	private String designation;
	private String department;
	private String phone;

	public Employee(Long id, String name, String email, String designation, String department, String phone) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.designation = designation;
		this.department = department;
		this.phone = phone;
	}
	
	public Employee() {
    }

	// Getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getDesignation() {
		return designation;
	}

	public String getDepartment() {
		return department;
	}

	public String getPhone() {
		return phone;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
