package com.my.voenmeh.Authentication;

import static com.my.voenmeh.Utils.Constants.DAYS_IN_WEEK;
import static com.my.voenmeh.Utils.Constants.EVEN_WEEKS_NUMBER;
import static com.my.voenmeh.Utils.Constants.ODD_WEEKS_NUMBER;

import android.util.Log;

import java.time.LocalDate;

import com.ibm.icu.text.Transliterator;
import com.my.voenmeh.Schedule.Schedule;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class UserRepository { //статический класс, в течение работы проги хранит персональные данные
    static String login = "";
    static String password = "";
    static String group = "";
    static HashSet<String> Groups = new HashSet<>();
    static HashMap<String, ArrayList<String>> Subjects = new HashMap<>();
    static int EvenWeeksInSem = EVEN_WEEKS_NUMBER, OddWeeksInSem = ODD_WEEKS_NUMBER;
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

    public static HashMap<String, ArrayList<String>> GetSubjectDates(){
        return Subjects;
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

    public static void FillGroups(Elements GroupsInHtml){
        for(Element group : GroupsInHtml){
            Groups.add(group.text());
        }
    }

    public static void GetSchedule() {
        if (!Subjects.isEmpty()) { //класс статический, достаточно заполнить один раз
            return;
        }

        //StudentSchedule.PullSchedule(group);

        //инициализация словаря
        for (Schedule.Day day : StudentSchedule.GetWeek(true)) {
            for (String CurrentSubject : day.Get("subject")) {
                if (!Subjects.containsKey(CurrentSubject)) {
                    Subjects.put(CurrentSubject, new ArrayList<>());
                }
            }
        }
        StudentSchedule.PullSchedule(group); //заменить на group
        Set<String> lek = new HashSet<>();
        Set<String> pr = new HashSet<>();
        Set<String> lab = new HashSet<>();
        Set<String> multiple = new HashSet<>();
        boolean week = true;
        for(int j = 0; j < 2; j++){
            for (Schedule.Day day : StudentSchedule.GetWeek(week)) {
                for (String CurrentSubject : day.Get("subject")) {
                    if(CurrentSubject.startsWith("пр")){
                        pr.add(CurrentSubject.substring(3));
                    }
                    if(CurrentSubject.startsWith("лек")){
                        lek.add(CurrentSubject.substring(4));
                    }
                    if(CurrentSubject.startsWith("лаб")){
                        lab.add(CurrentSubject.substring(4));
                    }

                }
            }
            week = !week;
        }
        for(String CurrentSubject : pr){ //вычисляем предметы, имеющие и практики и лекции/лабы
            if(lek.contains(CurrentSubject) || lab.contains(CurrentSubject)){
                multiple.add(CurrentSubject);
            }
        }
        for(String CurrentSubject : lek){ //вычисляем предметы, имеющие и лекции и лабы(без практик)
            if(lab.contains(CurrentSubject)){
                multiple.add(CurrentSubject);
            }
        }

        //инициализация словаря
        String actual = "";
        for(int j = 0; j < 2; j++){
            for (Schedule.Day day : StudentSchedule.GetWeek(week)) {
                for (String CurrentSubject : day.Get("subject")) {
                    if(CurrentSubject.startsWith("пр")){
                        actual = CurrentSubject.substring(3);
                    }
                    if(CurrentSubject.startsWith("лек") || CurrentSubject.startsWith("лаб")){
                        actual = CurrentSubject.substring(4);
                    }
                    if(multiple.contains(actual)){
                        Subjects.put(actual, new ArrayList<>());
                    }
                    else{
                        Subjects.put(CurrentSubject, new ArrayList<>());
                    }
                }
            }
            week = !week;
        }

        String CurrentDay, extra;
        int DayDifference, DaysProcessed, end_index = 0;
        LocalDate DayDate = LocalDate.of(2024, 2, 5); //ставим дату начала сема
        boolean isEvenWeek = false;
        for(int i = 0; i < EvenWeeksInSem + OddWeeksInSem; i++){ //цикл для всех недель в семе
            ArrayList<Schedule.Day> CurrentWeek = StudentSchedule.GetWeek(isEvenWeek);
            DaysProcessed = 0;
            for(Schedule.Day day : CurrentWeek){
                CurrentDay = DayInRussian(DayDate.getDayOfWeek().toString()); //день недели на русском, на который выпадает рассматриваемое число
                if(!CurrentDay.equals(day.dayName)){ //двигаем дату, если текущее число - неучебный день
                    DayDifference = DayNumber(day.dayName) - DayNumber(CurrentDay); //вычисляем длину неучебного пробела
                    DayDate = DayDate.plusDays(DayDifference);
                }
                String CurrentDate = DayDate.toString().substring(8) + "." + DayDate.toString().substring(5, 7);
                for(String CurrentSubject : day.Get("subject")){ //цикл для всех предметов в дне
                    actual = "";
                    if(CurrentSubject.startsWith("пр")){
                        actual = CurrentSubject.substring(3);
                        end_index = 2;
                    }
                    if(CurrentSubject.startsWith("лек") || CurrentSubject.startsWith("лаб")){
                        actual = CurrentSubject.substring(4);
                        end_index = 3;
                    }
                    if(Subjects.containsKey(actual)){
                        extra = " " + CurrentSubject.substring(0, end_index);
                        CurrentSubject = actual;
                    }
                    else{
                        extra = "";
                    }
                    Subjects.get(CurrentSubject).add(CurrentDate + extra); //добавляем в массив дат по ключу предмета дату дня
                }
                DaysProcessed++;
                if(DaysProcessed == CurrentWeek.size()){ //двигаем дату на воскресенье, когда обработали последний рабочий день
                    DayDifference = DAYS_IN_WEEK - DayNumber(day.dayName);
                    DayDate = DayDate.plusDays(DayDifference);
                }
                DayDate = DayDate.plusDays(1); //двигаем дату на 1
            }
            isEvenWeek = !isEvenWeek; //чередуем неделю
        }/*
        for(String sub : Subjects.keySet()){
            Log.d("MyTag", sub + ": " + Subjects.get(sub).size());
            for(String date : Subjects.get(sub)){
                Log.d("MyTag", date);
            } //ПРОВЕРКА ДАТ В КОНСОЛИ (НА МОЙ ВЗГЛЯД ВСЁ НОРМ, НО Я НЕ ПРОФ ТЕСТИРОВЩИК))
        }*/
    }

    public static boolean CorrectLogin(){ //допилить проверку корректности логина в зависимости от
        /*
        if(login.length() == 1){ //имеющихся групп (мудл апи или че там ебать)
            return true;
        }*/
        String currentGroup = Convert(login, "group");
        if(Groups.contains(currentGroup)){
            login = login.toLowerCase(); //в подравняем регистр на всякий, а то при проверке он не учитывается
            group = currentGroup;
            return true;
        }
        return false;
    }

    public static boolean CorrectPassword(){ //также сверять пароли с базой
        return true;
    }

    public static String DayInRussian(String DayInEnglish){
        switch(DayInEnglish){
            case "MONDAY":
                return "Понедельник";
            case "TUESDAY":
                return "Вторник";
            case "WEDNESDAY":
                return "Среда";
            case "THURSDAY":
                return "Четверг";
            case "FRIDAY":
                return "Пятница";
            case "SATURDAY":
                return "Суббота";
            default:
                return "";
        }
    }

    public static int DayNumber(String Day){
        switch(Day){
            case "Понедельник":
                return 1;
            case "Вторник":
                return 2;
            case "Среда":
                return 3;
            case "Четверг":
                return 4;
            case "Пятница":
                return 5;
            case "Суббота":
                return 6;
            default:
                return -1000;
        }
    }
}


