package br.com.idealizeall.universitymanagement.model;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;

public class Student {
    private Integer id;
    private Integer studentId;
    private String fullName;
    private String gender;
    private Date birthDate;
    private String year;
    private String course;
    private String section;
    private double payment;
    private String image;
    private LocalDateTime dataInsert;
    private LocalDateTime dataDelete;
    private LocalDateTime dataUpdate;
    private String status;
    private String statusPayment;



    public Student(StudentBuilder studentBuilder) {
        this.id = studentBuilder.id;
        this.studentId = studentBuilder.studentId;
        this.fullName = studentBuilder.fullName;
        this.gender = studentBuilder.gender;
        this.birthDate = studentBuilder.birthDate;
        this.year = studentBuilder.year;
        this.course = studentBuilder.course;
        this.section = studentBuilder.section;
        this.payment = studentBuilder.payment;
        this.image = studentBuilder.image;
        this.dataInsert = studentBuilder.dataInsert;
        this.dataDelete = studentBuilder.dataDelete;
        this.dataUpdate = studentBuilder.dataUpdate;
        this.status = studentBuilder.status;
        this.statusPayment = studentBuilder.statusPayment;
    }

    public static StudentBuilder builder(){
        return new StudentBuilder();
    }

    private Student (){

    }

    public static class StudentBuilder {
        private Integer id;
        private Integer studentId;
        private String fullName;
        private String gender;
        private Date birthDate;
        private String year;
        private String course;
        private String section;
        private double payment;
        private String image;
        private LocalDateTime dataInsert;
        private LocalDateTime dataDelete;
        private LocalDateTime dataUpdate;
        private String status;
        private String statusPayment;

        protected StudentBuilder(){

        }

        public StudentBuilder id(Integer id){
            this.id = id;
            return this;
        }

        public StudentBuilder studentId(Integer id){
            this.studentId = id;
            return this;
        }
        public StudentBuilder fullName(String fullname){
            this.fullName = fullname;
            return this;
        }
        public StudentBuilder gender(String gender){
            this.gender = gender;
            return this;
        }
        public StudentBuilder birthDate(Date date){
            this.birthDate = date;
            return this;
        }
        public StudentBuilder year(String year){
            this.year = year;
            return this;
        }
        public StudentBuilder course(String course){
            this.course = course;
            return this;
        }
        public StudentBuilder section(String section){
            this.section = section;
            return this;
        }
        public StudentBuilder payment(double payment){
            this.payment = payment;
            return this;
        }
        public StudentBuilder image(String image){
            this.image = image;
            return this;
        }
        public StudentBuilder dataInsert(LocalDateTime dataInsert){
            this.dataInsert = dataInsert;
            return this;
        }
        public StudentBuilder dataDelete(LocalDateTime dataDelete){
            this.dataDelete = dataDelete;
            return this;
        }
        public StudentBuilder dataUpdate(LocalDateTime dataUpdate){
            this.dataUpdate = dataUpdate;
            return this;
        }
        public StudentBuilder status(String status){
            this.status = status;
            return this;
        }
        public StudentBuilder statusPayment (String status){
            this.statusPayment = status;
            return this;
        }

        public Student build (){
            return new Student(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getYear() {
        return year;
    }

    public String getCourse() {
        return course;
    }

    public String getSection() {
        return section;
    }

    public double getPayment() {
        return payment;
    }

    public String getImage() {
        return image;
    }

    public LocalDateTime getDataInsert() {
        return dataInsert;
    }

    public LocalDateTime getDataDelete() {
        return dataDelete;
    }

    public LocalDateTime getDataUpdate() {
        return dataUpdate;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusPayment() {
        return statusPayment;
    }
}
