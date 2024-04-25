package com.my.voenmeh.Authentication;

public class UserRepository { //статический класс, в течение работы проги хранит персональные данные
    static String login = "";
    static String password = "";

    public static void SetLogin(String log){ //сеттер
        login = log;
    }

    public static void SetPassword(String pass){ //сеттер
        password = pass;
    }

    public static boolean CorrectLogin(){ //допилить проверку корректности логина в зависимости от
        if(login.length() == 7){ //имеющихся групп (мудл апи или че там ебать)
            return true;
        }
        return false;
    }

    public static boolean CorrectPassword(){ //также сверять пароли с базой
        return true;
    }

    public String GetGroup() {
        if(CorrectLogin()){
            return login.substring(0, 5);
        }
        return "";
    }
}
