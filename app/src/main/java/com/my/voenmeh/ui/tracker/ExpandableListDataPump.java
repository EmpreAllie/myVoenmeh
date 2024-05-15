package com.my.voenmeh.ui.tracker;

import com.my.voenmeh.Authentication.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kotlin.collections.ArrayDeque;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();
        Set<String> setOfSubjects = new HashSet<>();

        for (Map.Entry<String, ArrayList<String>> entry : UserRepository.GetSubjectDates().entrySet()) {
            //String subject = s.substring(s.indexOf(' ') + 1); // здесь хранится название предмета без указания типа пары
            //setOfSubjects.add(subject);

            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            expandableListDetail.put(key, value);
        }


        return expandableListDetail;
    }
}
