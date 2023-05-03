package com.global.mapping.subject;

import com.global.mapping.student.Student;
import com.global.mapping.student.StudentRepository;
import com.global.mapping.teacher.Teacher;
import com.global.mapping.teacher.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/* we could make the service layer but this is a short practice project */
@RestController
@RequestMapping(path = "/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository; /* shall be replaced with student service */
    private final TeacherRepository teacherRepository; /* shall be replaced with student service */

    @GetMapping
    public ResponseEntity<?> findAllSubjects() {
        return ResponseEntity.ok(subjectRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody Subject subject) {
        return ResponseEntity.ok(subjectRepository.save(subject));
    }

    @Transactional
    @PutMapping("/{subjectId}/students/{studentId}")
    public ResponseEntity<?> enrollStudentToSubject(
            @PathVariable("subjectId") Long subjectId,
            @PathVariable("studentId") Long studentId
    ) {
        // 1. get the student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException(String.format("student with id: %d is not found", studentId)));

        // 2. get the subject
        Subject subject = subjectRepository.findById((subjectId))
                .orElseThrow(() -> new RuntimeException(String.format("subject with id: %d is not found", subjectId)));

        // 3. assign student to student
        subject.enrollStudent(student);

        // 4. save the state
        return ResponseEntity.ok(subjectRepository.save(subject));
    }

    @Transactional
    @DeleteMapping("/{subjectId}/students/{studentId}")
    public ResponseEntity<?> removeStudentFromSubject(
            @PathVariable("subjectId") Long subjectId,
            @PathVariable("studentId") Long studentId
    ) {

        // 1. get student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException(String.format("student with id: %d is not found", studentId)));

        // 2. get subject
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException(String.format("subject with id: %d is not found", subjectId)));

        // 3. remove student from subject
        subject.excludeStudent(student);

        // 4. save the state
        return ResponseEntity.ok(subjectRepository.save(subject));
    }


    @Transactional
    @PutMapping("/{subjectId}/teachers/{teacherId}")
    public ResponseEntity<?> assignTeacherToSubject(
            @PathVariable("subjectId") Long subjectId,
            @PathVariable("teacherId") Long teacherId
    ) {

        // 1. get teacher
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException(String.format("teacher with id: %d is not found", teacherId)));

        // 2. get subject
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException(String.format("subject with id: %d is not found", subjectId)));

        // 3. assign teacher to subject
        subject.assignTeacherToSubject(teacher);

        // 4. save the state
        return ResponseEntity.ok(subjectRepository.save(subject));
    }
}
