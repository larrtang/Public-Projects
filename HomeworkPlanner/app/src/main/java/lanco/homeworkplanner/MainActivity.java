package lanco.homeworkplanner;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.model.*;


public class MainActivity extends AppCompatActivity implements AddHwDialog.NoticeDialogListener {


    ArrayList<Event> events = new ArrayList<Event>();
    //Event dummyevent1 = new Event("Test","description",1,12,2016,5f);
    //Event dummyevent2 = new Event("HW","description",1,9,2016,3f);
    ListView listView;
    CustomAdapter listAdapter;

    Event selectedEvent;
    EventManager eventManager;

    //EditText eventName2;
    //EditText eventDescription2;
    Calendar calendar;

    GoogleAccountCredential mCredential;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        eventManager = new EventManager(events, getBaseContext());

        events = eventManager.getStoredEvents();
        calendar = Calendar.getInstance();
        checkIfEventsPassed();

        Intent intent = getIntent();

        if (getIntent().getIntExtra("delete-event", 0) == 1) {
            events.remove(getIntent().getIntExtra("position", 0));
        }
        if (intent.getBooleanExtra("event was edited", false)) {
            Event editedEvent = (Event) intent.getSerializableExtra("edited event");
            int pos = intent.getIntExtra("position", 0);

            if (editedEvent.event_name.equals(events.get(pos).event_name)) {
                System.err.println("changed");
                events.set(pos, editedEvent);
            }

        }


        System.err.println("onCreate*********" + events.size() + "****************");

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedEvent = events.get(position);
                openEventInfo(selectedEvent, position);
            }
        });
        addEventsToListView();
        updateEventListView();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fab.show();
            }
        }).start();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //addHwDialog.show(getFragmentManager(), "Add Event");
                Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
                startActivity(intent);

            }
        });
        Calendar calendar = Calendar.getInstance();
        String dir = getFilesDir().toString();
        //Toast.makeText(getBaseContext(),calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.YEAR),Toast.LENGTH_LONG).show();
        //Toast.makeText(getBaseContext(),dir,Toast.LENGTH_LONG).show();


        //eventManager.storeEvents(events);
    }

    public void addEventsToListView() {
        Event[] eventArray = new Event[events.size()];
        for (int i = 0; i < events.size(); i++) {
            eventArray[i] = events.get(i);
        }
        listAdapter = new CustomAdapter(this, eventArray);
        listView.setAdapter(listAdapter);
    }


    public void updateEventListView() {
        addEventsToListView();
    }



    public void openEventInfo(Event selectedEvent, int position) {
        Intent intent = new Intent(MainActivity.this, EventInfoActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("event_name", selectedEvent.event_name);
        intent.putExtra("event_description", selectedEvent.event_description);
        String due_date = selectedEvent.month + "/" + selectedEvent.day + "/" + selectedEvent.year;
        intent.putExtra("due_date", due_date);
        intent.putExtra("time_needed", selectedEvent.timeToComplete);
        intent.putExtra("importance", selectedEvent.importance);
        intent.putExtra("Event", selectedEvent);
        intent.putExtra("realPriority", selectedEvent.realPriority);
        startActivity(intent);

                /*setContentView(R.layout.activity_event_info);
                //LayoutInflater layoutInflater = getLayoutInflater();
                //View newView = layoutInflater.inflate(R.layout.activity_event_info,null);
                TextView eventNameTextView;
                TextView eventDescriptionTextView;
                TextView dueDate;
                RatingBar importanceRatingBar;
                eventNameTextView = (TextView) findViewById(R.id.eventNameTextView);
                eventDescriptionTextView = (TextView) findViewById(R.id.eventDescriptionTextView);
                dueDate = (TextView) findViewById(R.id.dueDateTextView);
                importanceRatingBar = (RatingBar) findViewById(R.id.importanceRatingBar);

                eventNameTextView.setText(selectedEvent.event_name);
                eventDescriptionTextView.setText(selectedEvent.event_description);
                dueDate.setText(due_date);
                importanceRatingBar.setRating(selectedEvent.importance);

                Button okButton = (Button) findViewById(R.id.okbutton);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.activity_main);
                        setSupportActionBar(toolbar);
                        addEventsToListView();
                    }
                });
                */

    }

    @Override
    protected void onPause() {
        super.onPause();
        //eventManager.storeEvents(events);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent.getBooleanExtra("sending new event", false)) {
            Event newEvent = (Event) intent.getSerializableExtra("new event");
            if (events.size() == 0) {
                events.add(newEvent);
                Toast.makeText(getBaseContext(), newEvent.getEvent_name() + " added", Toast.LENGTH_LONG).show();
            } else if (!events.get(events.size() - 1).equals(newEvent)) {
                events.add(newEvent);
                Toast.makeText(getBaseContext(), newEvent.getEvent_name() + " added", Toast.LENGTH_LONG).show();
            }
        }

        if (intent.getBooleanExtra("added repeated event", false)) {
            ArrayList<Event> eventArray = (ArrayList<Event>) intent.getSerializableExtra("event array");
            for (int i = 0; i < eventArray.size(); i++) {
                events.add(eventArray.get(i));

            }
            Toast.makeText(getBaseContext(), "Tasks added", Toast.LENGTH_LONG).show();
        }


        RealPriority realPriority = new RealPriority(events);
        realPriority.calculateRealPriority();
        events = realPriority.getResortedEventList();
        updateEventListView();
        checkAlertNotification ();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //eventManager.deleteData();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete2) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete All Tasks")
                    .setMessage("Are you sure you want to delete all Tasks?")
                    .setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            eventManager.deleteData();
                            //listAdapter = new CustomAdapter(MainActivity.this, new Event[0]);
                            //listView.setAdapter(listAdapter);
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getBaseContext(), "Deleted Tasks", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
        if (id == R.id.about) {

            new AlertDialog.Builder(this)
                    .setTitle("About")
                    .setMessage(getString(R.string.app_info)
                            + "\n\n\nApplication created by Lawrence Tang\n\nBuild Version: " + getString(R.string.build_version)
                            + "\nBuild Date: " + getString(R.string.build_date))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();
        }

        if (id == R.id.settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkIfEventsPassed() {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).year < calendar.get(Calendar.YEAR)) {
                events.remove(i);

            } else if (events.get(i).month < calendar.get(Calendar.MONTH) + 1
                    && events.get(i).year <= calendar.get(Calendar.YEAR)) {
                events.remove(i);

            } else if (events.get(i).day <= calendar.get(Calendar.DAY_OF_MONTH)
                    && events.get(i).month <= calendar.get(Calendar.MONTH) + 1
                    && events.get(i).year <= calendar.get(Calendar.YEAR)) {
                events.remove(i);

            }


        }

    }

    public void checkAlertNotification () {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean showNotification = sharedPref.getBoolean("notification_switch", true);

        if (showNotification) {
            for (int i = 0; i < events.size(); i++) {
                System.err.println(events.get(i).realPriority);
                if (events.get(i).realPriority >= RealPriority.alertThreshold) {
                    System.err.println("One or more tasks are close to their deadline");
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_event_white_48dp)
                            .setContentTitle("Task Alert")
                            .setContentText("One or more tasks are close to their deadline");

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notificationBuilder.build());
                }
            }
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //Event newEvent = addHwDialog.newEvent();
        //events.add(newEvent);
        System.err.println("onPosClick*********" + events.size() + "****************");
        //eventManager.storeEvents(events);
        //eventManager.storeNewEvent(newEvent);

        this.onResume();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //eventManager.deleteData();
        eventManager.storeEvents(events);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //eventManager.deleteData();
    }


}


