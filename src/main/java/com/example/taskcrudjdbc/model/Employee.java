package com.example.taskcrudjdbc.model;

import java.util.List;

public class Employee {
    private Long id;
    private String name;
    private Long specialtyId;
    private List<Long> projectsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Long specialtyId) {
        this.specialtyId = specialtyId;
    }

    public List<Long> getProjectsId() {
        return projectsId;
    }

    public void setProjectsId(List<Long> projectsId) {
        this.projectsId = projectsId;
    }
}
