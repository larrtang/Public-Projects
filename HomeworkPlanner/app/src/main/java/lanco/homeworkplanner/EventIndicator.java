package lanco.homeworkplanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by larrytang on 1/13/2016.
 */
public class EventIndicator extends View{
    ArrayList<Event> events;
    Paint paint;

    int calHeight = 40;
    int calWidth = 30;

    final int size = 3;

    Calendar calendar;

    public EventIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Integer.parseInt("0d47a1", 16));

        calendar = Calendar.getInstance();


        events = new ArrayList<Event>();
        events.add(new Event("","",1,20,2016,1,1));
        events.add(new Event("","",1,21,2016,1,1));
        events.add(new Event("","",1,22,2016,1,1));
        events.add(new Event("","",1,23,2016,1,1));
        events.add(new Event("","",1,24,2016,1,1));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawOval();
    }

    public void drawIndicator(Canvas canvas){
        for (Event event : events){
            int firstDayOfWeek = calendar.getFirstDayOfWeek();
            int distance = event.day_of_year = calendar.get(Calendar.DATE);
        }
    }

}
