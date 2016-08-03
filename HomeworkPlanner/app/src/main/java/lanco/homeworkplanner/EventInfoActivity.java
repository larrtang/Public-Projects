package lanco.homeworkplanner;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import java.util.Calendar;

public class EventInfoActivity extends AppCompatActivity {

    public Event event;

    TextView eventDescriptionTextView;
    TextView dueDate;
    TextView timeNeededTextView;
    RatingBar importanceRatingBar;
    TextView realPriorityTextView;

    Button finishButton;

    ImageView imageViewDeadline;
    ImageView imageViewNeeded;

    FloatingActionButton fabCalendar;

    Calendar calendar;
    int month;
    int day;
    int year;

    int position;
    boolean wasEdited = false;
    boolean wasDeleted = false;
    boolean addingToCalendar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info2);
        Toolbar toolbar_info = (Toolbar) findViewById(R.id.toolbar_info);
        setSupportActionBar(toolbar_info);

        position = getIntent().getIntExtra("position", 0);

        setTitle(getIntent().getStringExtra("event_name"));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        event = (Event) getIntent().getSerializableExtra("Event");
        month = event.month;
        day = event.day;
        year = event.year;

        eventDescriptionTextView = (TextView) findViewById(R.id.eventDescriptionTextView);
        dueDate = (TextView) findViewById(R.id.dueDateTextView);
        timeNeededTextView = (TextView) findViewById(R.id.timeNeededTextView);
        importanceRatingBar = (RatingBar) findViewById(R.id.importanceRatingBar);
        finishButton = (Button) findViewById(R.id.finishButton);
        realPriorityTextView = (TextView) findViewById(R.id.realPriorityTextView);

        imageViewDeadline = (ImageView) findViewById(R.id.imageViewDeadline);
        calendar = Calendar.getInstance();

        imageViewDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EventInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {
                        month = view.getMonth() + 1;
                        day = view.getDayOfMonth();
                        year = view.getYear();

                        event.month = month;
                        event.day = day;
                        event.year = year;

                        dueDate.setText(month+"/"+day+"/"+year);
                        wasEdited = true;

                    }
                },event.year, event.month-1, event.day).show();
            }
        });

        imageViewNeeded = (ImageView) findViewById(R.id.imageViewNeeded);
        imageViewNeeded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeNeededDialog();
                wasEdited = true;
            }
        });


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent();
            }
        });
        if (getIntent().getStringExtra("event_description").equals("")){
            eventDescriptionTextView.setText("(No Description)");
        }
        else {
            eventDescriptionTextView.setText(getIntent().getStringExtra("event_description"));
        }

        dueDate.setText(getIntent().getStringExtra("due_date"));
        timeNeededTextView.setText(Float.toString(getIntent().getFloatExtra("time_needed", 0f) * 24) + " hours");
        importanceRatingBar.setRating(getIntent().getFloatExtra("importance", 0f));
        realPriorityTextView.setText(Float.toString(getIntent().getFloatExtra("realPriority", 0f)));

        importanceRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                event.importance = rating;
                wasEdited = true;
            }
        });


        fabCalendar = (FloatingActionButton) findViewById(R.id.fabCalendar);

        fabCalendar.hide();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fabCalendar.show();
            }
        }).start();

        fabCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCalendar = true;
                addEventToCalendar();
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_info, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete){
            deleteEvent();
        }
        if (item.getItemId() == android.R.id.home){
            System.err.println("home");
            eventEdited(wasEdited, wasDeleted);
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteEvent(){
        wasDeleted = true;
        Intent intent = new Intent(EventInfoActivity.this, MainActivity.class);
        intent.putExtra("delete-event",1);
        intent.putExtra("position", position);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if (wasEdited && !wasDeleted){
            eventEdited(wasEdited,wasDeleted);
        }
        else {
            super.onBackPressed();
        }
    }


    public void openTimeNeededDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.timeneeded_dialog, null);

        final EditText timeNeededEdit = (EditText) view.findViewById(R.id.timeNeededEdit);
        timeNeededEdit.setText(Float.toString(event.timeToComplete*24f));

        new AlertDialog.Builder(this)
                .setTitle("Time Needed")
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (timeNeededEdit.getText().toString().equals("")) {
                            event.timeToComplete = 1f/24f;
                            timeNeededTextView.setText("1.0 hours");
                        } else {
                            event.timeToComplete = Float.parseFloat(timeNeededEdit.getText().toString()) * (1f / 24f);
                            timeNeededTextView.setText(Float.parseFloat(timeNeededEdit.getText().toString()) + " hours");
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void eventEdited (boolean edited, boolean wasDeleted){
        if (edited && !wasDeleted) {
            event.month = month;
            event.day = day;
            event.year = year;

            Intent intent = new Intent(EventInfoActivity.this, MainActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("event was edited", true);
            intent.putExtra("edited event", event);
            startActivity(intent);

        }

    }

    @Override
    protected void onPause() {
        if (!addingToCalendar) {
            eventEdited(wasEdited, wasDeleted);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        addingToCalendar = false;
        super.onResume();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void addEventToCalendar(){
        Intent CalendarIntent = new Intent(Intent.ACTION_INSERT);
        CalendarIntent.setData(CalendarContract.Events.CONTENT_URI);
        CalendarIntent.putExtra(CalendarContract.Events.TITLE, event.event_name);
        CalendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, event.event_description);
        Calendar beginTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        beginTime.set(event.year,event.month-1, event.day, 0,0);
        endTime.set(event.year, event.month - 1, event.day, 0,0);
        CalendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        CalendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        startActivity(CalendarIntent);
    }
}
