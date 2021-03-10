package com.cg.empmgt.service;

import com.cg.empmgt.beans.Employee;
import com.cg.empmgt.dao.*;
import com.cg.empmgt.exceptions.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeDao dao;

	@Override
	public Employee addEmployee(String name, String department) {

		validateName(name);
		validateDepartment(department);
		Employee employee = new Employee(name, department);
		dao.add(employee);
		return employee;
	}

	@Override
	public Employee findById(int id) {

		validateId(id);
		Employee employee = dao.findById(id);
		return employee;
	}
	
	@Override
	public Employee updateName(int id, String name){
		validateName(name);
		Employee employee = findById(id);
		employee.setName(name);
		employee=dao.update(employee);
		return employee;
	}


	@Override
	public void removeById(int id) {

		

		if (MarketHolder.market.containsKey(id)) {
			dao.removeById(id);
		} else {
			throw new EmployeeNotFoundException("Employee not found for id :" + id);
		}

	}

	@Override
	public List<Employee> findAll() {

		List<Employee> employees = dao.findAll();
		return employees;
	}

	public void validateId(int id) {
		if (id < 0) {
			throw new InvalidIdException("id cannot be negative or zero" + id);
		}

	}

	public void validateName(String name) {
		if (name == null || name.isEmpty() || name.trim().isEmpty()) {
			throw new InvalidEmployeeNameException("name is invalid " + name);
		}

	
	}

	public void validateDepartment(String department) {
		if (department == null || department.isEmpty() || department.trim().isEmpty()) {
			throw new InvalidDepartmentException("department is invalid " + department);
		}

	}
}
