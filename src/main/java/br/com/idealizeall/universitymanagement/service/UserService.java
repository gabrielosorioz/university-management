package br.com.idealizeall.universitymanagement.service;

import br.com.idealizeall.universitymanagement.exception.UserException;
import br.com.idealizeall.universitymanagement.model.User;
import br.com.idealizeall.universitymanagement.model.UserRoles;
import br.com.idealizeall.universitymanagement.model.UserValidation;
import br.com.idealizeall.universitymanagement.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class UserService {
    private final Logger log = Logger.getLogger(UserService.class.getName());

    UserRepository userRepository = new UserRepository();

    public User createUserByRole(UserRoles role, String username, String password, String email){
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .dataCreate(LocalDateTime.now())
                .role(role)
                .build();
    }

    public void registerUser(User user) throws UserException {
       if(!userRepository.existsByUsername(user.getUsername())){
           if(UserValidation.validatePassword(user.getPassword())){
               if(UserValidation.validateUsername(user.getUsername())){

               } else {
                   throw new UserException("Username is blank");
               }
           } else {
               throw new UserException("Invalid user password" + user.getPassword());
           }
       } else {
           throw new UserException("Username already exists");
       }
        userRepository.save(user);

    }





    
}
