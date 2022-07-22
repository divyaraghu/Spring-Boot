package com.example.rest.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.beans.Employee;
import com.example.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping("/getEmployees")
	public List<Employee> getEmployees() {
		return employeeService.getEmployeeList();
	}

	@GetMapping("/getEmployeeById/{id}")
	public Employee getEmployeeById(@PathVariable(value = "id") long id) {
		Employee employee = employeeService.getEmployeeById(id);
		return employee;

	}

	@GetMapping("/getEmployeesByCountry/{country}")
	public List<Employee> getEmployeesByCountry(@PathVariable String country) {
		List<Employee> filteredList = employeeService.getEmployeesByCountry(country);
		return filteredList;
	}

	@GetMapping("/searchEmployeesByName/{nameFilter}")
	public List<Employee> searchEmployeesByName(@PathVariable String nameFilter) {
		List<Employee> filteredList = employeeService.searchEmployeesByName(nameFilter);
		return filteredList;
	}

	@PostMapping("/createEmployee")
	@ResponseBody
	public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
		System.out.println("Emloyee details: id - " + employee.getId() + " Name - " + employee.getEmployeeName()
				+ " Department - " + employee.getEmployeeName());
		// Now make call to DAO class - insert in to the employeeList for this Demo
		boolean isCreated = employeeService.createEmployee(employee);
		if (isCreated) {
			return new ResponseEntity<String>("Employee details inserted", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("Employee with id " + employee.getId() + " already exists",
					HttpStatus.BAD_REQUEST);
		}

	}

}
