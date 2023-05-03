package com.global.mapping.subject;

import com.global.mapping.student.Student;
import com.global.mapping.teacher.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subject")
public class Subject {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "subject_student",
            joinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id")
    )
    private Set<Student> students = new HashSet<>();

    /* helper methods */
    public void enrollStudent(Student student) {
        students.add(student);
        student.getSubjects().add(this);
    }

    public void excludeStudent(Student student) {
        students.remove(student);
        student.getSubjects().remove(this);
    }

    public void assignTeacherToSubject(Teacher teacher) {
        this.teacher = teacher;
    }

    public void unAssignTeacherToSubject(Teacher teacher) {
        this.teacher = null;
    }
}
