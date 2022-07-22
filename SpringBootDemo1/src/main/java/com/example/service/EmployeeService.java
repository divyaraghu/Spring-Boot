package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.beans.Employee;

@Service
public interface EmployeeService{
	
	public boolean createEmployee(Employee employee);
	
	public boolean isExistingEmployee(long id);
	
	public List<Employee> getEmployeeList();
	
	public Employee getEmployeeById(long id);
	
	public List<Employee> searchEmployeesByName(String name);
	
	public List<Employee> getEmployeesByCountry(String country);

}
