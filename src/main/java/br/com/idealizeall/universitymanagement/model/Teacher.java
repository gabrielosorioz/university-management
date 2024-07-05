package br.com.idealizeall.universitymanagement.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Teacher {

    private Integer id;
    private Integer teacherId;
    private String fullName;
    private String gender;
    private LocalDate birthDate;
    private String yearExperience;
    private String experience;
    private String department;
    private BigDecimal salary;
    private String salaryStatus;
    private String image;
    private String status;
    private Timestamp dateInsert;
    private Timestamp dateUpdate;
    private Timestamp dateDelete;
    private User user;



    public static TeacherBuilder builder(){
        return new TeacherBuilder();
    }

    private Teacher(TeacherBuilder teacherBuilder) {
        this.id = teacherBuilder.id;
        this.teacherId = teacherBuilder.teacherId;
        this.fullName = teacherBuilder.fullName;
        this.gender = teacherBuilder.gender;
        this.birthDate = teacherBuilder.birthDate;
        this.yearExperience = teacherBuilder.yearExperience;
        this.experience = teacherBuilder.experience;
        this.department = teacherBuilder.department;
        this.salary = teacherBuilder.salary;
        this.salaryStatus = teacherBuilder.salaryStatus;
        this.status = teacherBuilder.status;
        this.image = teacherBuilder.image;
        this.dateInsert = teacherBuilder.dateInsert;
        this.dateUpdate = teacherBuilder.dateUpdate;
        this.dateDelete = teacherBuilder.dateDelete;
        this.user = teacherBuilder.user;

    }



    public static class TeacherBuilder {
        private Integer id;
        private Integer teacherId;
        private String fullName;
        private String gender;
        private LocalDate birthDate;
        private String yearExperience;
        private String experience;
        private String department;
        private BigDecimal salary;
        private String salaryStatus;
        private String image;
        private String status;
        private Timestamp dateInsert;
        private Timestamp dateUpdate;
        private Timestamp dateDelete;
        private User user;

        public TeacherBuilder id (Integer id){
            this.id = id;
            return this;
        }
        public TeacherBuilder teacherId (Integer id){
            this.teacherId = id;
            return this;
        }
        public TeacherBuilder fullName (String fullName){
            this.fullName = fullName;
            return this;
        }
        public TeacherBuilder gender (String gender){
            this.gender = gender;
            return this;
        }
        public TeacherBuilder birthDate(LocalDate birthDate){
            this.birthDate = birthDate;
            return this;
        }
        public TeacherBuilder yearExperience (String yearExperience){
            this.yearExperience= yearExperience;
            return this;
        }
        public TeacherBuilder experience (String experience){
            this.experience = experience;
            return this;
        }
        public TeacherBuilder department(String department){
            this.department = department;
            return this;
        }
        public TeacherBuilder salary(BigDecimal salary){
            this.salary = salary;
            return this;
        }
        public TeacherBuilder salaryStatus (String salaryStatus){
            this.salaryStatus = salaryStatus;
            return this;
        }
        public TeacherBuilder status (String status){
            this.status = status;
            return this;
        }
        public TeacherBuilder image (String image){
            this.image = image;
            return this;
        }
        public TeacherBuilder dateInsert (Timestamp dateInsert){
            this.dateInsert = dateInsert;
            return this;
        }
        public TeacherBuilder dateUpdate (Timestamp dateUpdate){
            this.dateUpdate = dateUpdate;
            return this;
        }
        public TeacherBuilder dateDelete (Timestamp dateDelete){
            this.dateDelete = dateDelete;
            return this;
        }
        public TeacherBuilder user (User user){
            this.user = user;
            return this;
        }

        public Teacher build(){
            if(user == null){
                throw new IllegalArgumentException("ERROR: User is null");
            }
            return new Teacher(this);
        }

    }

    public Integer getId() {
        return id;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getYearExperience() {
        return yearExperience;
    }

    public String getExperience() {
        return experience;
    }

    public String getDepartment() {
        return department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getSalaryStatus() {
        return salaryStatus;
    }

    public String getImage() {
        return image;
    }

    public Timestamp getDateInsert() {
        return dateInsert;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public Timestamp getDateDelete() {
        return dateDelete;
    }

    public String getStatus() {
        return status;
    }

    public User getUser(){
        return user;
    }
}
