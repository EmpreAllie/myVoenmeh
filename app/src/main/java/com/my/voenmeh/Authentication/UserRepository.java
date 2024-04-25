package com.my.voenmeh.Authentication;

public class UserRepository {
    String login;
    String password;

    public UserRepository(){
        password = "";
        login = "";
    }
    public void SetLogin(String log){ //сеттер
        login = log;
    }
    public void SetPassword(String pass){ //сеттер
        password = pass;
    }
    public boolean CorrectLogin(){ //допилить проверку корректности логина в зависимости от
        if(login.length() == 7){ //имеющихся групп
            return true;
        }
        return false;
    }

    public boolean CorrectPassword(){ //также сверять пароли с базой
        return true;
    }

    public String GetGroup() {
        if(CorrectLogin()){
            return login.substring(0, 5);
        }
        return "";
    }
}
