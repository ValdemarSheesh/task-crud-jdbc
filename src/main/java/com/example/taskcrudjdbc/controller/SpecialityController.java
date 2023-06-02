package com.example.taskcrudjdbc.controller;

import com.example.taskcrudjdbc.dto.SpecialtyDto;
import com.example.taskcrudjdbc.model.Specialty;
import com.example.taskcrudjdbc.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("specialty")
public class SpecialityController {
    private SpecialtyService specialtyService;

    @Autowired
    public void setSpecialtyService(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping
    public ResponseEntity<?> saveSpeciality(@Validated @RequestBody Specialty specialty) {
        specialtyService.save(specialty);
        return ResponseEntity.status(HttpStatus.CREATED).body("Specialty is created");
    }

    @GetMapping
    public ResponseEntity<?> getAllSpecialities() {
        List<SpecialtyDto> specialtyDtoList = specialtyService.getSpecialties();
        if (specialtyDtoList.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Specialties is not exist");
        else
            return ResponseEntity.ok(specialtyDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSpecialty(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(specialtyService.getSpecialtyById(id));
    }

    @PutMapping
    public ResponseEntity<?> updateSpecialty(@RequestBody Specialty specialty) {
        specialtyService.update(specialty);
        return ResponseEntity.ok("Specialty is updated");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSpecialty(@RequestParam(value = "id") Long id) {
        specialtyService.delete(id);
        return ResponseEntity.ok("Specialty is deleted");
    }
}
