package com.example.taskcrudjdbc.service;

import com.example.taskcrudjdbc.connection.ConnectionDB;
import com.example.taskcrudjdbc.dto.EmployeeDto;
import com.example.taskcrudjdbc.dto.ProjectDto;
import com.example.taskcrudjdbc.exceptions.NotFoundException;
import com.example.taskcrudjdbc.exceptions.ServiceException;
import com.example.taskcrudjdbc.model.Project;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    public void save(Project project) {
        String sql = "INSERT INTO projects(name_project) VALUES(?) RETURNING id";
        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, project.getNameProject());

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            project.setId(resultSet.getLong(1));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        String sql1 = "INSERT INTO \"employee-project\"(project_id, employee_id) VALUES(?, ?)";

        putEmployeesInProject(project);
    }

    public List<ProjectDto> getProjects() {
        String sql = "SELECT * FROM projects";
        List<ProjectDto> projectDtoList = new ArrayList<>();

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ProjectDto projectDto = new ProjectDto();
                projectDto.setId(resultSet.getLong(1));
                projectDto.setNameProject(resultSet.getString(2));
                projectDto.setEmployeeDtoList(getEmployeesInProject(projectDto.getId()));
                projectDtoList.add(projectDto);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return projectDtoList;
    }

    public ProjectDto getProjectById(Long id) {
        String sql = "SELECT * FROM projects WHERE id=?";
        ProjectDto projectDto = new ProjectDto();

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) throw new NotFoundException("Project with id = " + id + " not found");
            projectDto.setId(resultSet.getLong(1));
            projectDto.setNameProject(resultSet.getString(2));
            projectDto.setEmployeeDtoList(getEmployeesInProject(projectDto.getId()));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return projectDto;
    }

    public void update(Project project) {
        String sql = "UPDATE projects SET name_project=? WHERE id=?";

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, project.getNameProject());
            statement.setLong(2, project.getId());

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }

        String sql1 = "DELETE FROM \"employee-project\" WHERE project_id=?";

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql1);

            statement.setLong(1, project.getId());

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        putEmployeesInProject(project);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM projects WHERE id=?";

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    private void putEmployeesInProject(Project project) {
        String sql = "INSERT INTO \"employee-project\"(project_id, employee_id) VALUES(?, ?)";

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            for (Long id : project.getEmployeesId()) {
                statement.setLong(1, project.getId());
                statement.setLong(2, id);
                statement.execute();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    private List<EmployeeDto> getEmployeesInProject(Long projectId) {
        String sql = "SELECT employees.id, employees.name, specialties.name_specialty " +
                "FROM employees JOIN specialties " +
                "ON specialties.id = employees.specialty_id " +
                "WHERE employees.id IN (SELECT employee_id FROM \"employee-project\" WHERE project_id=?)";
        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        try (Connection connection = ConnectionDB.createConnectionDB()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, projectId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setId(resultSet.getLong(1));
                employeeDto.setName(resultSet.getString(2));
                employeeDto.setSpecialty(resultSet.getString(3));
                employeeDtoList.add(employeeDto);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return employeeDtoList;
    }
}

















