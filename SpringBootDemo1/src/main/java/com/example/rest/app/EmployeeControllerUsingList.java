package com.example.rest.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class EmployeeControllerUsingList {

	List<Employee> employeeList = new ArrayList<Employee>();
	{
		employeeList.add(new Employee(1, "Sajal", "IT", "India"));
		employeeList.add(new Employee(2, "Lokesh", "Dev", "India"));
		employeeList.add(new Employee(3, "Kajal", "Dev", "USA"));
		employeeList.add(new Employee(4, "Sukesh", "Dev", "USA"));
	}

	@GetMapping("/getEmployeesList")
	public List<Employee> getEmployees() {
		return employeeList;
	}

	@GetMapping("/getEmployeeByIdFromList/{id}")
	public Employee getEmployeeById(@PathVariable(value = "id") long id) {
		Employee employee = getEmployee(id);
		return employee;

	}

	private Employee getEmployee(long id) {

		Stream<Employee> employeeStream = employeeList.stream();
		List<Employee> filteredList = employeeStream.filter((x) -> x.getId() == id).collect(Collectors.toList());
		if (filteredList.size() > 0) {
			return filteredList.get(0);
		}

		return null;

	}

	@GetMapping("/getEmployeesByCountryFromList/{country}")
	public List<Employee> getEmployeesByCountry(@PathVariable String country) {
		List<Employee> filteredList = employeeList.stream().filter((x) -> x.getCountry().equalsIgnoreCase(country))
				.sorted().collect(Collectors.toList());
		return filteredList;
	}

	@GetMapping("/searchEmployeesByNameFromList/{nameFilter}")
	public List<Employee> searchEmployeesByName(@PathVariable String nameFilter) {
		List<Employee> filteredList = employeeList.stream()
				.filter((x) -> x.getEmployeeName().toLowerCase().contains(nameFilter.toLowerCase()))
				.collect(Collectors.toList());
		return filteredList;
	}

	@PostMapping("/createEmployeeToList")
	@ResponseBody
	public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
		System.out.println("Emloyee details: id - " + employee.getId() + " Name - " + employee.getEmployeeName()
				+ " Department - " + employee.getEmployeeName());
		// Now make call to DAO class - insert in to the employeeList for this Demo
		if (null == getEmployee(employee.getId())) {
			employeeList.add(employee);

		} else {
			return new ResponseEntity<String>("Employee with id " + employee.getId() + " already exists",
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("Employee details inserted", HttpStatus.OK);
	}

}
