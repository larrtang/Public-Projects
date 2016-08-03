package lanco.homeworkplanner;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity  {

    EventManager eventManager;

    EditText eventName;
    EditText eventDescription;
    RatingBar ratingBar;
    TextView dueDateEditText;
    EditText dayEditText;
    ImageView imageView;
    ImageView imageView7;
    TextView timeNeededText;
    Switch repeatText;
    SeekBar repeatSeek;
    TextView repeatTextView;

    int month;
    int day;
    int year;

    float timeToComplete = 1f/24f;

    boolean isRepeated = false;
    int repeatFor = 1;

    Calendar calendar;

    Event newEvent = new Event("Failed","",1,1,1,1,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event2);
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        setTitle("Add Task");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        eventManager = new EventManager(this);
        calendar = Calendar.getInstance();

        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH)+1;
        year = calendar.get(Calendar.YEAR);

        eventName = (EditText) findViewById(R.id.eventNameEditText);
        eventDescription = (EditText) findViewById(R.id.eventDescriptionEditText);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        dueDateEditText = (TextView) findViewById(R.id.dueDateEditText);
        //hourEditText = (EditText) findViewById(R.id.hourEditText);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView7 = (ImageView) findViewById(R.id.imageView7);
        timeNeededText = (TextView) findViewById(R.id.timeNeededText);
        repeatText = (Switch) findViewById(R.id.repeatText);
        repeatSeek = (SeekBar) findViewById(R.id.repeatSeekbar);
        repeatTextView = (TextView) findViewById(R.id.repeatTextView);

        repeatSeek.setVisibility(View.GONE);
        repeatTextView.setVisibility(View.GONE);
        repeatText.setChecked(isRepeated);
        repeatSeek.setProgress(repeatFor - 2);
        repeatTextView.setText("for " + repeatFor + " week(s)");

        dueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                openDatePickerDialog();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });
        timeNeededText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeNeededDialog();
            }
        });
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeNeededDialog();
            }
        });

        repeatText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    repeatSeek.setVisibility(View.VISIBLE);
                    repeatTextView.setVisibility(View.VISIBLE);
                    isRepeated = true;
                    repeatFor =  2;
                    repeatSeek.setProgress(repeatFor-2);
                    repeatTextView.setText("for " + repeatFor + " week(s)");
                }
                else {
                    repeatSeek.setVisibility(View.GONE);
                    repeatTextView.setVisibility(View.GONE);
                    isRepeated = false;
                    repeatSeek.setProgress(repeatFor-2);
                }
            }
        });
        repeatSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                repeatFor = progress + 2;
                repeatTextView.setText("for " + repeatFor + " week(s)");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dueDateEditText.setText(month+"/"+day+"/"+year);
    }

    public Event newEvent(){
        String event_name = eventName.getText().toString();
        String event_description = eventDescription.getText().toString();
        float rating = ratingBar.getRating();

        return new Event(event_name, event_description, month, day, year, rating, timeToComplete);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflator = getMenuInflater();
        menuInflator.inflate(R.menu.menu_add_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.addEventButton){
            if (eventName.getText().toString().equals("")){
                eventName.setError("Enter task name");
            }
            else if (year < calendar.get(Calendar.YEAR)){
                Toast.makeText(getBaseContext(),"Date invalid", Toast.LENGTH_SHORT).show();
                openDatePickerDialog();
            }
            else if (month < calendar.get(Calendar.MONTH)+1
                    &&year <= calendar.get(Calendar.YEAR)){
                Toast.makeText(getBaseContext(),"Date invalid", Toast.LENGTH_SHORT).show();
                openDatePickerDialog();
            }
            else if (day <= calendar.get(Calendar.DAY_OF_MONTH)
                    && month <= calendar.get(Calendar.MONTH)+1
                    && year <= calendar.get(Calendar.YEAR)){
                Toast.makeText(getBaseContext(),"Date invalid", Toast.LENGTH_SHORT).show();
                openDatePickerDialog();
            }
            else if (timeToComplete == 0f){
                Toast.makeText(getBaseContext(),"Time need invalid", Toast.LENGTH_SHORT).show();
                openTimeNeededDialog();
            }
            else {
                addNewEvent();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewEvent (){
        newEvent = newEvent();

        if (isRepeated){
            addRepeatedEvent();
        }
        else {
            Intent intent = new Intent(AddEventActivity.this, MainActivity.class);
            intent.putExtra("sending new event", true);
            intent.putExtra("new event", newEvent);
            startActivity(intent);
        }


    }

    public void openDatePickerDialog(){
        new DatePickerDialog(new ContextThemeWrapper(this,R.style.AppTheme2), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {
                month = view.getMonth() + 1;
                day = view.getDayOfMonth();
                year = view.getYear();

                dueDateEditText.setText(month+"/"+day+"/"+year);


            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)+1).show();
    }

    public void openTimeNeededDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.timeneeded_dialog, null);

        final EditText timeNeededEdit = (EditText) view.findViewById(R.id.timeNeededEdit);
        timeNeededEdit.setText(Float.toString(timeToComplete*24f));

        new AlertDialog.Builder(this)
                .setTitle("Time Needed")
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (timeNeededEdit.getText().toString().equals("")) {
                            timeToComplete = 1f/24f;
                            timeNeededText.setText("1.0 hours");
                        } else {
                            timeToComplete = Float.parseFloat(timeNeededEdit.getText().toString()) * (1f / 24f);
                            timeNeededText.setText(Float.parseFloat(timeNeededEdit.getText().toString()) + " hours");
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void openRepeatDialog (){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.repeat_dialog, null);


        final Switch repeatSwitch = (Switch) view.findViewById(R.id.repeatSwitch);
        final SeekBar repeatSeek = (SeekBar) view.findViewById(R.id.repeatSeek);
        final TextView repeatTextView = (TextView) view.findViewById(R.id.repeatTextView);

        repeatSwitch.setChecked(isRepeated);
        repeatSeek.setProgress(repeatFor - 2);
        repeatSeek.setEnabled(isRepeated);
        repeatTextView.setText("for " + repeatFor + " week(s)");

        repeatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeatSeek.setEnabled(true);
                    isRepeated = true;
                    repeatFor =  2;
                    repeatTextView.setText("for " + repeatFor + " week(s)");
                } else {
                    repeatSeek.setEnabled(false);
                }
            }
        });
        repeatSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                repeatFor = progress + 2;
                repeatTextView.setText("for " + repeatFor + " week(s)");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new AlertDialog.Builder(this)
                .setTitle("Set Repeat")
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isRepeated = false;
                        repeatFor = 1;
                    }
                })
                .show();
    }

    public void addRepeatedEvent(){
        calendar.set(year,month-1,day);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int maxMonth = 12;
        calendar = Calendar.getInstance();

        ArrayList<Event> events = new ArrayList<Event>();
        for (int i = 0; i < repeatFor; i++){
            events.add(newEvent());

            if ((events.get(i).day + 7*(i) )<= maxDay){
                events.get(i).day += 7*(i);
            }
            else if (events.get(i).month + 1 <= maxMonth){
                events.get(i).month += 1;
                events.get(i).day = (events.get(i).day + 7*(i)) - maxDay;

            }
        }

        ArrayList<Event> realEventList = new ArrayList<Event>();
        realEventList = eventManager.getStoredEvents();
        System.err.println(events.size());
        for (int i = 0; i < events.size(); i++){
            realEventList.add(events.get(i));
        }
        eventManager.storeEvents(realEventList);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
