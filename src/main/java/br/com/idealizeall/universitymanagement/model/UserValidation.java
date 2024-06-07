package br.com.idealizeall.universitymanagement.model;

import br.com.idealizeall.universitymanagement.exception.UserException;
import br.com.idealizeall.universitymanagement.repository.UserRepository;

public class UserValidation {

    private static boolean validatePassword(String password){
        boolean isValid = false;
        if (password.length() > 7){
            char c;
            boolean hasNum = false;
            boolean hasCap = false;
            boolean hasLow = false;

            for(int i = 0; i < password.length(); i++){
                c = password.charAt(i);
                if (Character.isDigit(c)){
                    hasNum = true;
                }
                else if (Character.isLowerCase(c)){
                    hasLow = true;
                }
                else if (Character.isUpperCase(c)){
                    hasCap = true;
                }

                if(hasNum && hasLow && hasCap){
                    isValid = true;
                }

            }

        }
        return isValid;
    }

    private static boolean validateUsername(String username){
        boolean isValid = false;
        if(username != null){
            if (!username.isBlank()){
                isValid = true;
            }
        }
        return isValid;
    }

    private static boolean validateEmail(String email){
            if (email == null || email.isBlank()) {
                return false;
            }
            // Regex pattern for a valid email address
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            return email.matches(emailRegex);
    }

    public static void validateUser (User user) throws UserException {
        if(!validateEmail(user.getEmail())){
            if(user.getRole() != UserRoles.ADMIN){
                throw new UserException("Invalid email");
            }
        }

        if(!validateUsername(user.getUsername())){
            throw new UserException("Invalid username");
        }

        if(!validatePassword(user.getPassword())){
            throw new UserException("Invalid password");
        }
    }


}
