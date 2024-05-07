package com.my.voenmeh.Authentication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ibm.icu.text.Transliterator;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

public class UserRepository { //статический класс, в течение работы проги хранит персональные данные
    static String login = "";
    static String password = "";
    static String group = "";
    static HashSet<String> Groups = new HashSet<String>();

    public static void SetLogin(String log){ //сеттер
        login = log;
    }

    public static void SetPassword(String pass){ //сеттер
        password = pass;
    }
    private static String Convert(String value, String type){ //преобразование группы в логин и логина в группу
        Transliterator tr;         //в type передаем group(логин -> группа) или loginXX(группа -> логин), где XX - номер студента в группе
        String result = "";
        String T = type.substring(0, 5);
        switch (T){
            case "login":
                tr = Transliterator.getInstance("Russian-Latin/BGN");
                result = tr.transliterate(value);
                if(value.startsWith("Е")){
                    result = result.substring(1);
                }
                result += type.substring(type.length() - 2); //два последних символа параметра логин для номера группы
                result = result.toLowerCase();
                break;
            case "group":
                if(value.length() < 2)//защита от дуралеев, вводящих один символ в логин
                    break;
                tr = Transliterator.getInstance("Latin-Russian/BGN");
                result = tr.transliterate(value);
                if(value.startsWith("e")){
                    result = "Е" + result.substring(1);
                }
                result = result.toUpperCase();
                result = result.substring(0, result.length() - 2);
                break;
        }
        return result;
    }

    public static void PullGroups(Elements GroupsInHtml){
        for(Element group : GroupsInHtml){
            Groups.add(group.text());
        }
    }

    public static boolean CorrectLogin(){ //допилить проверку корректности логина в зависимости от
        if(login.length() == 1){ //имеющихся групп (мудл апи или че там ебать)
            return true;
        }/*
        String currentGroup = Convert(login, "group");
        if(Groups.contains(currentGroup)){
            login = login.toLowerCase(); //в подравняем регистр на всякий, а то при проверке он не учитывается
            group = currentGroup;
            return true;
        }*/
        return false;
    }

    public static boolean CorrectPassword(){ //также сверять пароли с базой
        return true;
    }
}
