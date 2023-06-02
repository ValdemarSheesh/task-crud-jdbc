package com.example.taskcrudjdbc.controller;

import com.example.taskcrudjdbc.dto.ProjectDto;
import com.example.taskcrudjdbc.model.Project;
import com.example.taskcrudjdbc.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {
    private ProjectService projectService;

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<?> saveProject(@RequestBody Project project) {
        projectService.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body("Project is created");
    }

    @GetMapping
    public ResponseEntity<?> getProjects() {
        List<ProjectDto> projects = projectService.getProjects();
        if (projects.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Projects is not exist");
        else
            return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PutMapping()
    public ResponseEntity<?> updateProject(@RequestBody Project project) {
        projectService.update(project);
        return ResponseEntity.ok("Project is updated");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProject(@RequestParam(value = "id") Long id) {
        projectService.delete(id);
        return ResponseEntity.ok("Project is deleted");
    }
}
