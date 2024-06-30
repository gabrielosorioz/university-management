package br.com.idealizeall.universitymanagement.service;
import br.com.idealizeall.universitymanagement.exception.StudentRepositoryException;
import br.com.idealizeall.universitymanagement.model.Student;
import br.com.idealizeall.universitymanagement.repository.StudentRepository;

import java.util.logging.Logger;

public class StudentService {
    private final Logger log = Logger.getLogger(StudentService.class.getName());
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    private StudentService(){}

    public void registerStudent(Student student) {
        studentRepository.save(student);
    }
}
