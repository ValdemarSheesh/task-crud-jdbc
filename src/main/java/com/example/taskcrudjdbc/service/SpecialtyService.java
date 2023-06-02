package com.example.taskcrudjdbc.service;

import com.example.taskcrudjdbc.connection.ConnectionDB;
import com.example.taskcrudjdbc.dto.EmployeeDto;
import com.example.taskcrudjdbc.dto.SpecialtyDto;
import com.example.taskcrudjdbc.exceptions.NotFoundException;
import com.example.taskcrudjdbc.exceptions.ServiceException;
import com.example.taskcrudjdbc.model.Specialty;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialtyService {

    public void save(Specialty specialty) {
        String sql = "INSERT INTO specialties(name_specialty) VALUES(?)";

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, specialty.getNameSpecialty());

            statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    public List<SpecialtyDto> getSpecialties() {
        String sql = "SELECT * FROM specialties";
        List<SpecialtyDto> specialtyDtoList = new ArrayList<>();

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SpecialtyDto specialtyDto = new SpecialtyDto();
                specialtyDto.setId(resultSet.getLong(1));
                specialtyDto.setNameSpecialty(resultSet.getString(2));
                specialtyDto.setEmployeeDtoList(getEmployeeForSpecialty(specialtyDto.getId()));
                specialtyDtoList.add(specialtyDto);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return specialtyDtoList;
    }

    public SpecialtyDto getSpecialtyById(Long id) {
        String sql = "SELECT * FROM specialties WHERE id=?";
        SpecialtyDto specialtyDto = new SpecialtyDto();

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) throw new NotFoundException("Specialty with id = " + id + " not found");

            specialtyDto.setId(resultSet.getLong(1));
            specialtyDto.setNameSpecialty(resultSet.getString(2));
            specialtyDto.setEmployeeDtoList(getEmployeeForSpecialty(specialtyDto.getId()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return specialtyDto;
    }

    public void update(Specialty specialty) {
        String sql = "UPDATE specialties SET name_specialty=? WHERE id=?";

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, specialty.getNameSpecialty());
            statement.setLong(2, specialty.getId());

            statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM specialties WHERE id=?";

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    private List<EmployeeDto> getEmployeeForSpecialty(Long specialtyId) {
        String sql = "SELECT * FROM employees WHERE specialty_id=?";
        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, specialtyId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setId(resultSet.getLong(1));
                employeeDto.setName(resultSet.getString(2));
                employeeDtoList.add(employeeDto);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return employeeDtoList;
    }
}












