package lanco.homeworkplanner;

import android.content.Context;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by larrytang on 1/7/2016.
 */
public class EventManager {
    ArrayList<Event> eventList;
    public File eventFile;

    public EventManager(ArrayList<Event> e, Context context){

        eventFile = new File(context.getFilesDir(),"Event.evts");
        if (!eventFile.exists()){
            try {
                eventFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
                System.err.println(e.toString());
            }
        }
        eventList = e;
    }
    public EventManager(Context context){

        eventFile = new File(context.getFilesDir(),"Event.evts");
        if (!eventFile.exists()){
            try {
                eventFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        eventList = new ArrayList<Event>();
    }

    public ArrayList<Event> getStoredEvents(){
        int day     = 0;
        int month   = 0;
        int year    = 0;

        String event_name        = "";
        String event_description = "";
        String textLine = "";

        float priority = 0;
        float timeToComplete = 0;

        try{
            BufferedReader br = new BufferedReader(new FileReader(eventFile));
            while ((textLine = br.readLine()) != null){
                String[] texts = textLine.split("`");
                System.err.println(textLine);
                event_name = texts[0];
                event_description = texts[1];
                month = Integer.parseInt(texts[2]);
                day = Integer.parseInt(texts[3]);
                year = Integer.parseInt(texts[4]);
                priority = Float.parseFloat(texts[5]);
                timeToComplete = Float.parseFloat(texts[6]);
                eventList.add(new Event(event_name,event_description, month,day,year,priority,timeToComplete));

            }
            br.close();
        } catch(Exception e){System.err.println(e);}

        return eventList;
    }

    public void storeEvents(){
        deleteData();
        try {
            eventFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String textLine = "";
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(eventFile));

            for (int i = 0; i < eventList.size(); i++){
                textLine = eventList.get(i).event_name + "`" + eventList.get(i).event_description + "`" + eventList.get(i).month + "`" + eventList.get(i).day + "`" + eventList.get(i).year + "`" + eventList.get(i).importance + "`"+eventList.get(i).timeToComplete;
                bw.write(textLine);
                bw.newLine();
            }

            bw.close();
        }catch (Exception e){System.err.println(e);}
    }

    public void storeEvents(ArrayList<Event> events){
        deleteData();
        try {
            eventFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String textLine = "";
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(eventFile));

            for (int i = 0; i < events.size(); i++){
                textLine = eventList.get(i).event_name + "`" + eventList.get(i).event_description + "`" + eventList.get(i).month + "`" + eventList.get(i).day + "`" + eventList.get(i).year + "`" + eventList.get(i).importance + "`"+eventList.get(i).timeToComplete;
                bw.write(textLine);
                bw.newLine();
            }

            bw.close();
        }catch (Exception e){System.err.println(e);}
    }

    public void storeNewEvent(Event event){
        String textLine = "";
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(eventFile));
            textLine = event.event_name + "`" + event.event_description + "`" + event.month + "`" + event.day + "`" + event.year + "`" + event.importance + "`" + event.timeToComplete;
            bw.write(textLine);
            bw.newLine();
            bw.close();
        }catch (Exception e){System.err.println(e.toString());}
    }



    public void changeEvent(int pos, Event newEvent, ArrayList<Event> events){

        deleteData();
        try {
            eventFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String textLine = "";
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(eventFile));

            for (int i = 0; i < events.size(); i++){
                if (i == pos){
                    textLine = newEvent.event_name + "`" + newEvent.event_description + "`" + newEvent.month + "`" + newEvent.day + "`" +newEvent.year + "`" + newEvent.importance + "`" + newEvent.timeToComplete;
                    bw.write(textLine);
                    bw.newLine();
                }
                else {
                    textLine = eventList.get(i).event_name + "`" + eventList.get(i).event_description + "`" + eventList.get(i).month + "`" + eventList.get(i).day + "`" + eventList.get(i).year + "`" + eventList.get(i).importance + "`" + eventList.get(i).timeToComplete;
                    bw.write(textLine);
                    bw.newLine();
                }
            }

            bw.close();
        }catch (Exception e){System.err.println(e);}


    }



    public void deleteData (){
        try{
            eventFile.delete();
        }catch (Exception e){System.err.println(e.toString());}
    }

    public void errorPrintFlag(){
        System.err.println("FLAG******");
    }


}
