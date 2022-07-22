package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.beans.Employee;

//@Repository annotation helps Sprint to bootstrap JPA dependencies during component scanning
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{}
