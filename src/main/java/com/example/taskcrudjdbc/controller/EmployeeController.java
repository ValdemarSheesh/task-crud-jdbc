package com.example.taskcrudjdbc.controller;

import com.example.taskcrudjdbc.dto.EmployeeDto;
import com.example.taskcrudjdbc.model.Employee;
import com.example.taskcrudjdbc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@Validated @RequestBody Employee employee) {
        employeeService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body("Employee is created");
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeDto> employeeDtoList = employeeService.getEmployees();
        if (employeeDtoList.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Specialties is not exist");
        else
            return ResponseEntity.ok(employeeDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
        employeeService.update(employee);
        return ResponseEntity.ok("Employee is updated");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmployee(@RequestParam(value = "id") Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok("Employee is deleted");
    }
}









