package lanco.homeworkplanner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by larrytang on 1/10/2016.
 */
public class CustomAdapter extends ArrayAdapter<Event> {

    public CustomAdapter(Context context, Event[] events) {
        super(context, R.layout.listitem1, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.listitem1, parent, false);

        TextView eventNameText = (TextView) view.findViewById(R.id.eventNameText);
        TextView infoText = (TextView) view.findViewById(R.id.infoText);

        eventNameText.setText(getItem(position).event_name);
        if (getItem(position).realPriority >= RealPriority.ALERT_THRESHOLD){
            eventNameText.setTextColor(Color.RED);
        }
        infoText.setText("Due " + getItem(position).month + "/" + getItem(position).day + "/"+ getItem(position).year + "      (Real Priority: " + getItem(position).realPriority+ ")");
        return view;

    }

    @Override
    public void remove(Event object) {
        super.remove(object);
    }

    @Override
    public void clear() {
        super.clear();
    }
}
