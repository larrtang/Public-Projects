package lanco.homeworkplanner;

import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by larrytang on 1/4/2016.
 */
public class Event implements Serializable{
    public int day     = 0;
    public int month   = 0;
    public int year    = 0;
    public int day_of_year = 0;

    public String event_name        = "";
    public String event_description = "";

    public float importance = 3f;
    public float realPriority = 0f;

    public float timeDistance_inDays = 0;
    public float timeToComplete = 0;
    public float timeLeft = 0;



    public Event(String NAME, String DESCRIPTION, int MONTH,int DAY,int YEAR, float PRIORITY, float time){
        event_name = NAME;
        event_description = DESCRIPTION;
        month = MONTH;
        day = DAY;
        year = YEAR;
        importance = PRIORITY;
        timeToComplete = time;



    }

    public String getEvent_name(){
        return event_name;
    }
}
