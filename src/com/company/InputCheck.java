package com.company;

public class InputCheck {
    public static boolean isNumeric(char[] str) {
        if(str.length == 0){
            return false;
        }
        String string = String.valueOf(str);
        return string.matches("[\\+\\-]?\\d*(\\.\\d+)?([eE][\\+\\-]?\\d+)?");
    }
}