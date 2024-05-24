package br.com.idealizeall.universitymanagement.model;

public class UserValidation {

    public static boolean validatePassword(String password){
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

    public static boolean validateUsername(String username){
        boolean isValid = false;
        if(username != null){
            if (!username.isBlank()){
                isValid = true;
            }
        }
        return isValid;
    }
}
