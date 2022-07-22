package com.example.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.Employee;
import com.example.repo.EmployeeRepository;
import com.example.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
    EntityManager entityManager;

	@Override
	public boolean createEmployee(Employee employee) {
		if(!isExistingEmployee(employee.getId())) {
			employeeRepository.save(employee);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean isExistingEmployee(long id) {
		return employeeRepository.existsById(id);
		
	}

	@Override
	public List<Employee> getEmployeeList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}

	@Override
	public Employee getEmployeeById(long id) {
		// TODO Auto-generated method stub
		return employeeRepository.findById(id).get();
	}

	@Override
	public List<Employee> searchEmployeesByName(String name) {
		List<Employee> resultList = entityManager.createQuery("from Employee e where e.employeeName LIKE :empName").setParameter("empName", "%"+name+"%").getResultList();
		return resultList;
	}

	@Override
	public List<Employee> getEmployeesByCountry(String country) {
		List<Employee> resultList = entityManager.createQuery("from Employee e where e.country = :country").setParameter("country", country).getResultList();
		return resultList;
	}
	
	
	
	

}
