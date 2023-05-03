package com.global.mapping.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* we could make the service layer but this is a short practice project */
@RestController
@RequestMapping(path = "/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherRepository teacherRepository;

    @GetMapping
    ResponseEntity<?> findAllTeachers(){
        return ResponseEntity.ok(teacherRepository.findAll());
    }

    @PostMapping
    ResponseEntity<?> createTeacher(@RequestBody Teacher teacher){
        return ResponseEntity.ok(teacherRepository.save(teacher));
    }
}
