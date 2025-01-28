package com.organization.employee.controller;

import com.organization.employee.dto.EmployeeDTO;
import com.organization.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.status(201).body(createdEmployee);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        CacheControl cacheControl = CacheControl.noCache();
        return ResponseEntity.ok().cacheControl(cacheControl).body(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        CacheControl cacheControl = CacheControl.noCache();
        return employee != null ? ResponseEntity.ok().cacheControl(cacheControl).body(employee) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeDTO> getEmployeeByEmail(@PathVariable String email) {
        EmployeeDTO employee = employeeService.getEmployeeByEmail(email);
        CacheControl cacheControl = CacheControl.noCache();
        return employee != null ? ResponseEntity.ok().cacheControl(cacheControl).body(employee) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@Valid @PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        return updatedEmployee != null ? ResponseEntity.ok(updatedEmployee) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}