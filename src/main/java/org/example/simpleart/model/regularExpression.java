package org.example.simpleart.model;

public class regularExpression {
    public static boolean checkNickname(String stringa){
        String regularExpression = "^\\D.{2,20}$";
        return stringa.matches(regularExpression);
    }

    public static boolean checkNameOrSurname(String stringa){
        String regularExpression = "^[a-zA-Z]{2,}( ?[a-zA-Z])*$";
        return stringa.matches(regularExpression);
    }

    public static boolean checkEmail(String stringa){
        String regularExpression = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return stringa.matches(regularExpression);
    }

    public static boolean checkPassword(String stringa){
        String regularExpression = "^(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9!@#$%^&*()_\\-+=.?]{6,}$";
        return stringa.matches(regularExpression);
    }
}
