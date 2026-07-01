package com.automationexercise.utils;


public class TimeManager {

    // safe for screenshots, logs, reports
    public static String getTimeStamp() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                .format(new java.util.Date());
    }


    //unique timestamp for each test data
    public static String getSimpleTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

}
