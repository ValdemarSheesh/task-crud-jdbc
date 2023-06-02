package com.example.taskcrudjdbc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {
    private Long id;
    private String name;
    private String specialty;
    private List<ProjectDto> projectDtoList;

    public EmployeeDto(Long id, String name, String specialty, List<ProjectDto> projectDtoList) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.projectDtoList = projectDtoList;
    }

    public EmployeeDto() {
    }

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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<ProjectDto> getProjectDtoList() {
        return projectDtoList;
    }

    public void setProjectDtoList(List<ProjectDto> projectDtoList) {
        this.projectDtoList = projectDtoList;
    }
}
