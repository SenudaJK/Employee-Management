package com.organization.employee.service;

import com.organization.employee.dao.EmployeeRepository;
import com.organization.employee.dto.EmployeeDTO;
import com.organization.employee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(sanitize(employeeDTO.getFirstName()));
        employee.setLastName(sanitize(employeeDTO.getLastName()));
        employee.setEmail(sanitize(employeeDTO.getEmail()));
        employee.setTelephone(sanitize(employeeDTO.getTelephone()));
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        return employeeOpt.map(this::convertToDTO).orElse(null);
    }

    public EmployeeDTO getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email);
        return employee != null ? convertToDTO(employee) : null;
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        if (employeeRepository.existsById(id)) {
            Employee employee = new Employee();
            employee.setId(id);
            employee.setFirstName(sanitize(employeeDTO.getFirstName()));
            employee.setLastName(sanitize(employeeDTO.getLastName()));
            employee.setEmail(sanitize(employeeDTO.getEmail()));
            employee.setTelephone(sanitize(employeeDTO.getTelephone()));
            Employee updatedEmployee = employeeRepository.save(employee);
            return convertToDTO(updatedEmployee);
        }
        return null;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getTelephone());
    }

    private String sanitize(String input) {
        return Jsoup.clean(input, Safelist.basic());
    }
}