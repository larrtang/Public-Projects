package lanco.homeworkplanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by larrytang on 1/4/2016.
 */
public class AddHwDialog extends DialogFragment {
    int state;
    View view;
    public AddHwDialog() {
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
    AlertDialog.Builder builder;
    LayoutInflater inflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.addhwdialog0_layout, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        state = 0;
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Event");
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.addhwdialog0_layout, null);
        builder.setView(view);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                mListener.onDialogPositiveClick(AddHwDialog.this);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogNegativeClick(AddHwDialog.this);
            }
        });
        return dialog;
    }

    public Event newEvent(){
        EditText eventName;
        EditText eventDescription;
        RatingBar ratingBar;
        DatePicker datePicker;
        eventName = (EditText) view.findViewById(R.id.eventName);
        eventDescription = (EditText) view.findViewById(R.id.eventDescription);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);

        String event_name = eventName.getText().toString();
        String event_description = eventDescription.getText().toString();
        int month = datePicker.getMonth() + 1;
        int day = datePicker.getDayOfMonth();
        int year = datePicker.getYear();
        float rating = ratingBar.getRating();

        return new Event(event_name, event_description, month, day, year, rating,1);

    }


}
