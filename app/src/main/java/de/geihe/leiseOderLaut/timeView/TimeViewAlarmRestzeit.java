package de.geihe.leiseOderLaut.timeView;

import android.content.Context;
import android.view.ViewGroup;

import de.geihe.leiseOderLaut.R;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 05.10.2015.
 */
public class TimeViewAlarmRestzeit extends TimeViewAlarm {

    public TimeViewAlarmRestzeit(Context context,
                                 ViewGroup vgSmall, ViewGroup viewLarge, ReadOnlyAmpel ampel) {
        super(context, vgSmall, viewLarge, ampel);
    }

    @Override
    protected String getTag() {
        return "alarm_restzeit";
    }

    @Override
    public int getColor() {
        return context.getResources().getColor(R.color.timeviewRed);
    }

    @Override
    protected String getText() {
        return "Rest";
    }

    @Override
    protected String getTime() {
        return zeitString(-(alarmZeitInMS - ampel.getRotzeit()));
    }
}
