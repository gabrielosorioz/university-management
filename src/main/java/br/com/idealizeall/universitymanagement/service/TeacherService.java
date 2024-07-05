package br.com.idealizeall.universitymanagement.service;

import br.com.idealizeall.universitymanagement.model.Teacher;
import br.com.idealizeall.universitymanagement.repository.TeacherRepository;

import java.util.logging.Logger;

public class TeacherService {
    private final Logger log = Logger.getLogger(TeacherService.class.getName());
    private TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }

    private TeacherService(){}

    public void registerTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }
}
