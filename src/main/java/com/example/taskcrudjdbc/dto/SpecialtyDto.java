package com.example.taskcrudjdbc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecialtyDto {
    private Long id;
    private String nameSpecialty;
    private List<EmployeeDto> employeeDtoList;

    public SpecialtyDto(Long id, String nameSpecialty, List<EmployeeDto> employeeDtoList) {
        this.id = id;
        this.nameSpecialty = nameSpecialty;
        this.employeeDtoList = employeeDtoList;
    }

    public SpecialtyDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSpecialty() {
        return nameSpecialty;
    }

    public void setNameSpecialty(String nameSpecialty) {
        this.nameSpecialty = nameSpecialty;
    }

    public List<EmployeeDto> getEmployeeDtoList() {
        return employeeDtoList;
    }

    public void setEmployeeDtoList(List<EmployeeDto> employeeDtoList) {
        this.employeeDtoList = employeeDtoList;
    }
}
