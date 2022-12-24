package com.kapuchinka.facultydbproject.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringPatterns {

    private static final String NAME_PATTERN = "^[а-яА-Яa-zA-Z -]{3,}$";
    private static final String TEXT_PATTERN = "^[а-яА-Яa-zA-Z0-9\s,-]{3,}$";

    private static final String DATE_PATTERN = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";

    private static final String NUMBER_PATTERN = "[0-9]+";

    private static boolean isValid(String regex, String str){
        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }
    public static boolean isValidName(String name){
        return isValid(NAME_PATTERN, name);
    }
//[^a-zA-Z0-9], было - ^[A-Za-z]\w{4,29}$

    public static boolean isValidText(String text){
        return isValid(TEXT_PATTERN, text);
    }

    public static boolean isValidDate(String date) {return isValid(DATE_PATTERN, date);}

    public static boolean isValidNumbers(String numbers) {return isValid(NUMBER_PATTERN, numbers);}

    public static String getNamePattern(){
        return NAME_PATTERN;
    }

    public static String getTextPattern(){
        return TEXT_PATTERN;
    }

    public static String getDatePattern(){return DATE_PATTERN;}

    public static String getNumberPattern() {return NUMBER_PATTERN;}
}
