package com.my.voenmeh.Authentication;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ibm.icu.text.Transliterator;
import com.my.voenmeh.Activities.ScheduleActivity;
import com.my.voenmeh.Schedule.Schedule;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UserRepository { //статический класс, в течение работы проги хранит персональные данные
    static String login = "";
    static String password = "";
    static String group = "";
    static HashSet<String> Groups = new HashSet<String>();
    static HashMap<String, Integer> Subjects = new HashMap<String, Integer>();
    static int EvenWeeksInSem = 8, OddWeeksInSem = 8;
    static Schedule StudentSchedule = new Schedule();

    public static void SetLogin(String log){ //сеттер
        login = log;
    }

    public static void SetPassword(String pass){ //сеттер
        password = pass;
    }

    public static String GetGroup(){
        return group;
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

    public static void GetSchedule(){
        if(!Subjects.isEmpty()){ //класс статический, достаточно заполнитить один раз
            return;
        }
        StudentSchedule.PullSchedule("О721Б"); //заменить на group

        //циклы считают, сколько раз встречается каждый предмет на четной неделе
        for(Schedule.Day day : StudentSchedule.GetWeek(true)){
            for(String CurrentSubject : day.Get("subject")){
                if(!Subjects.containsKey(CurrentSubject)){
                    Subjects.put(CurrentSubject, EvenWeeksInSem);
                }
                else{
                    Subjects.replace(CurrentSubject, Subjects.get(CurrentSubject) + EvenWeeksInSem);
                }
            }//по ключy CurrentSubject получаем инт,
        }//где значение - кол-во занятий по предмету в семе

        //циклы считают, сколько раз встречается каждый предмет на нечетной неделе
        for(Schedule.Day day : StudentSchedule.GetWeek(false)){
            for(String CurrentSubject : day.Get("subject")){
                if(!Subjects.containsKey(CurrentSubject)){
                    Subjects.put(CurrentSubject, OddWeeksInSem);
                }
                else{
                    Subjects.replace(CurrentSubject, Subjects.get(CurrentSubject) + OddWeeksInSem);
                }
            }
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

