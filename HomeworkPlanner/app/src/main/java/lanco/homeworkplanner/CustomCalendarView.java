package lanco.homeworkplanner;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.CalendarView;

/**
 * Created by larrytang on 1/13/2016.
 */
public class CustomCalendarView extends CalendarView {
    public CustomCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
