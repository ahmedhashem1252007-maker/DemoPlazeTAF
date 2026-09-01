package com.automationexercices.utils.logs;

public class TimeManager {
    //ScreenShots - logs - reports
    public static String getTimestamp()
    {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new java.util.Date());
    }
    //unique timestamp for each data
    public static String getSimpleTimestamp()
    {
        return Long.toString(System.currentTimeMillis());
    }
}
