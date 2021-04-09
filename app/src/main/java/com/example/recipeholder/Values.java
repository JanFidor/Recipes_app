package com.example.recipeholder;

import android.util.Log;

import java.util.HashMap;


// TODO limit choice for create to one without "any"
public class Values {
    public static int getTaste(String taste){
        HashMap<String, Integer> tasteMap = new HashMap<>();
        tasteMap.put("Any", 0);
        tasteMap.put("Sweet", 1);
        tasteMap.put("Sour", 2);
        tasteMap.put("Salty", 3);
        tasteMap.put("Umami", 4);
        tasteMap.put("Bitter",  5);

        //Log.e("value", taste + ' ');

        return tasteMap.get(taste);
    }
    public static int getType(String type){
        HashMap<String, Integer> typeMap = new HashMap<>();
        typeMap.put("Any", 0);
        typeMap.put("Lunch", 1);
        typeMap.put("Dinner", 2);
        typeMap.put("Supper", 3);
        typeMap.put("Drink", 4);
        typeMap.put("Snack",  5);

        return typeMap.get(type);
    }
    public static int getTime(String time){
        HashMap<String, Integer> timeMap = new HashMap<>();
        timeMap.put("0", 0);
        timeMap.put("15", 1);
        timeMap.put("30", 2);
        timeMap.put("45", 3);
        timeMap.put("60", 4);
        timeMap.put(">60",  5);

        return timeMap.get(time);
    }

    public static String timeString(int time){
        HashMap<Integer, String> timeMap = new HashMap<>();
        timeMap.put(0, "0");
        timeMap.put(1, "15");
        timeMap.put(2, "30");
        timeMap.put(3, "45");
        timeMap.put(4, "60");
        timeMap.put(5, ">60");

        return timeMap.get(time);
    }

    //TODO add different colors for times
    public static void getColor(int time){};


}
