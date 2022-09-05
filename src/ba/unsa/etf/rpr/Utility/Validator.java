package ba.unsa.etf.rpr.Utility;

import java.sql.SQLException;

public class Validator {
    private static Validator instance = null;

    private Validator(){

    }

    public static void removeInstance() {
        if (instance == null) return;
        instance = null;
    }

    public static Validator getInstance(){
        if (instance == null){
            instance = new Validator();
        }

        return instance;
    }

    public boolean isStringCorrectEasy(String string){
        if (string.length() < 3 || string.length()>24)
            return false;
        return true;
    }

    public boolean isStringCorrectHeavy(String string){
        if (string.length() < 3 || string.length()>24)
            return false;

        for (int i = 0; i < string.length(); i++) {
            if (!((string.charAt(i)>='a' && string.charAt(i)<='z') ||
                    (string.charAt(i)>='A' && string.charAt(i)<= 'Z'))){
                return false;
            }
        }
        return true;
    }

    public boolean isPhoneCorrect(String string) {
        if (string.length() < 3 || string.length()>24)
            return false;
        for (int i = 0; i < string.length(); i++) {
            if (!(string.charAt(i)=='+' || string.charAt(i)=='/' ||
            (string.charAt(i)>='0' && string.charAt(i)<= '9'))){
                return false;
            }
        }
        return true;
    }
}
