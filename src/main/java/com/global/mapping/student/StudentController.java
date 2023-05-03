package com.global.mapping.student;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* we could make the service layer but this is a short practice project */
@RestController
@RequestMapping(path = "/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping
    ResponseEntity<?> findAllStudents(){
        return ResponseEntity.ok(studentRepository.findAll());
    }

    @PostMapping
    ResponseEntity<?> createStudent(@RequestBody Student student){
        return ResponseEntity.ok(studentRepository.save(student));
    }
}
