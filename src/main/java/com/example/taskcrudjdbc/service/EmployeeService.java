package com.example.taskcrudjdbc.service;

import com.example.taskcrudjdbc.connection.ConnectionDB;
import com.example.taskcrudjdbc.dto.EmployeeDto;
import com.example.taskcrudjdbc.dto.ProjectDto;
import com.example.taskcrudjdbc.exceptions.NotFoundException;
import com.example.taskcrudjdbc.exceptions.ServiceException;
import com.example.taskcrudjdbc.model.Employee;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    public void save(Employee employee) {
        String sql = "INSERT INTO employees(name, specialty_id) VALUES(?, ?)";

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, employee.getName());
            statement.setLong(2, employee.getSpecialtyId());

            statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    public List<EmployeeDto> getEmployees() {
        String sql = "SELECT employees.id, employees.name, specialties.name_specialty " +
                "FROM employees " +
                "JOIN specialties " +
                "ON specialties.id = employees.specialty_id";

        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setId(resultSet.getLong(1));
                employeeDto.setName(resultSet.getString(2));
                employeeDto.setSpecialty(resultSet.getString(3));
                employeeDto.setProjectDtoList(getProjectForEmployee(employeeDto.getId()));
                employeeDtoList.add(employeeDto);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return employeeDtoList;
    }

    public EmployeeDto getEmployeeById(Long id) {
        String sql = "SELECT employees.id, employees.name, specialties.name_specialty " +
                "FROM employees " +
                "JOIN specialties " +
                "ON specialties.id = employees.specialty_id " +
                "WHERE employees.id=?";
        EmployeeDto employeeDto = new EmployeeDto();

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) throw new NotFoundException("Employee with id = " + id + " not found");

            employeeDto.setId(resultSet.getLong(1));
            employeeDto.setName(resultSet.getString(2));
            employeeDto.setSpecialty(resultSet.getString(3));
            employeeDto.setProjectDtoList(getProjectForEmployee(employeeDto.getId()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return employeeDto;
    }

    public void update(Employee employee) {
        String sql = "UPDATE employees SET name=?, specialty_id=? WHERE id=?";

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, employee.getName());
            statement.setLong(2, employee.getSpecialtyId());
            statement.setLong(3, employee.getId());

            statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM employees WHERE id=?";

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    private List<ProjectDto> getProjectForEmployee(Long employeeId) {
        String sql = "SELECT * FROM projects WHERE projects.id IN (SELECT project_id FROM \"employee-project\" WHERE employee_id=?)";
        List<ProjectDto> projectDtoList = new ArrayList<>();

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ProjectDto projectDto = new ProjectDto();
                projectDto.setId(resultSet.getLong(1));
                projectDto.setNameProject(resultSet.getString(2));
                projectDtoList.add(projectDto);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return projectDtoList;
    }
}




















