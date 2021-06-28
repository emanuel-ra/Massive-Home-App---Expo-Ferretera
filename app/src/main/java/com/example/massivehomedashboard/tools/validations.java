package com.example.massivehomedashboard.tools;

import android.util.Patterns;

import java.util.regex.Pattern;

public class validations {

    public boolean validEmail(String email){
        Pattern oPattern = Patterns.EMAIL_ADDRESS;
        return oPattern.matcher(email).matches();
    }
    public  boolean validPhone(String phoneNumber){
        Pattern oPattern = Patterns.PHONE;
        return  oPattern.matcher(phoneNumber).matches();
    }
}
