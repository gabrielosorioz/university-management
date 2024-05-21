package br.com.idealizeall.universitymanagement.service;

import br.com.idealizeall.universitymanagement.exception.UserException;
import br.com.idealizeall.universitymanagement.model.User;
import br.com.idealizeall.universitymanagement.model.UserValidation;
import br.com.idealizeall.universitymanagement.repository.UserRepository;

import java.util.logging.Logger;

public class UserService {
    private final Logger log = Logger.getLogger(UserService.class.getName());

    UserRepository userRepository = new UserRepository();

    public void registerUser(User user) throws UserException {
       if(!userRepository.existsByUsername(user.getUsername())){
           if(UserValidation.validatePassword(user.getPassword())){
               if(UserValidation.validateUsername(user.getUsername())){
                    user.getUsername().toLowerCase();
               } else {
                   throw new UserException("Username is blank");
               }
           } else {
               throw new UserException("Invalid user password");
           }
       } else {
           throw new UserException("User already exists");
       }
       userRepository.save(user);
    }





    
}
