package lanco.homeworkplanner;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by larrytang on 1/7/2016.
 */
public class RealPriority {
    ArrayList<Event> events;
    public float procrastinationRatio = 0.7f;
    public float importanceWeight = 6f;

    public static float alertThreshold = 20f;

    public RealPriority(ArrayList<Event> e) {
        events = e;
    }

    public void calculateRealPriority(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        for (int i = 0; i < events.size(); i++){
            events.get(i).timeDistance_inDays =
                    (events.get(i).year - year)*365.25f
                    + (events.get(i).month - (month+1))*30.5f
                    + (events.get(i).day - day)
                    - (calendar.get(Calendar.HOUR_OF_DAY) * (1f/24f));

            events.get(i).timeLeft =
                    events.get(i).timeDistance_inDays
                    - events.get(i).timeToComplete;


            //events.get(i).realPriority =
            //         (events.get(i).importance * importanceWeight)
            //        / (events.get(i).timeLeft * procrastinationRatio);

            events.get(i).realPriority =
                    importanceWeight / (events.get(i).timeLeft * procrastinationRatio)
                    + events.get(i).importance;

        }
    }

    public ArrayList<Event> getResortedEventList(){
        boolean flag = true;
        Event temp;

        while(flag){
            flag = false;
            for (int i = 0; i < events.size()-1; i++){
                if (events.get(i).realPriority < events.get(i+1).realPriority){
                    temp = events.get(i);
                    events.set(i, events.get(i+1));
                    events.set(i+1, temp);
                    flag = true;
                }
            }
        }
        return events;
    }
}

