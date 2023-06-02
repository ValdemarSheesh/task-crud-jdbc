package com.example.taskcrudjdbc.model;

import java.util.List;

public class Project {
    private Long id;
    private String nameProject;
    private List<Long> employeesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public List<Long> getEmployeesId() {
        return employeesId;
    }

    public void setEmployeesId(List<Long> employeesId) {
        this.employeesId = employeesId;
    }
}
