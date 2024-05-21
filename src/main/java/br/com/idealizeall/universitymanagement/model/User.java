package br.com.idealizeall.universitymanagement.model;
import java.time.LocalDateTime;

public class User {
    private final Integer id;
    private final String username;
    private final String password;
    private final String email;
    private final LocalDateTime dataCreate;
    private final LocalDateTime dataUpdate;
    private final UserRoles role;

    public User(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.username = userBuilder.username;
        this.password = userBuilder.password;
        this.email = userBuilder.email;
        this.dataCreate = userBuilder.dataCreate;
        this.dataUpdate = userBuilder.dataUpdate;
        this.role = userBuilder.role;
    }

    public static UserBuilder builder(){
        return new UserBuilder();
    }

    public static class UserBuilder {

        private Integer id;
        private String username;
        private String password;
        private String email;
        private LocalDateTime dataCreate;
        private LocalDateTime dataUpdate;
        private UserRoles role;

        public UserBuilder id(Integer id){
            this.id = id;
            return this;
        }
        public UserBuilder username(String username){
            this.username= username;
            return this;
        }
        public UserBuilder password(String password){
            this.password = password;
            return this;
        }
        public UserBuilder email(String email){
            this.email = email;
            return this;
        }
        public UserBuilder dataCreate(LocalDateTime dataCreate){
            this.dataCreate = dataCreate;
            return this;
        }
        public UserBuilder dataUpdate(LocalDateTime dataUpdate){
            this.dataUpdate = dataUpdate;
            return this;
        }

        public UserBuilder role(UserRoles role){
            this.role = role;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDataCreate() {
        return dataCreate;
    }

    public LocalDateTime getDataUpdate() {
        return dataUpdate;
    }

    public UserRoles getRole() {
        return role;
    }
}
