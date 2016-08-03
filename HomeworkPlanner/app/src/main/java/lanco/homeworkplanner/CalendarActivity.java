package lanco.homeworkplanner;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.support.v7.widget.Toolbar;


import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    ArrayList<Event> events;

    CustomCalendarView calendarView;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar_calendar = (Toolbar) findViewById(R.id.toolbar_calendar);
        setSupportActionBar(toolbar_calendar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        calendarView = (CustomCalendarView) findViewById(R.id.calendarView);
        calendar = Calendar.getInstance();
        calendarView.setMinDate(calendar.getFirstDayOfWeek());
        calendarView.setDate(calendar.get(Calendar.DATE));

        Intent intent = getIntent();
        events = (ArrayList<Event>) intent.getSerializableExtra("Events");
    }

    public void addEventsToCalendar(){
        // TODO: 8/2/2016 add implementation with event adding 
    }
}
