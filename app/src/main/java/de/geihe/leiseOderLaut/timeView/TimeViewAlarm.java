package de.geihe.leiseOderLaut.timeView;

import android.content.Context;
import android.view.ViewGroup;

import de.geihe.leiseOderLaut.preferences.PrefHelper;
import de.geihe.leiseOderLaut.R;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 05.10.2015.
 */
public class TimeViewAlarm extends TimeView {

    protected long alarmZeitInMS;

    public TimeViewAlarm(Context context,
                         ViewGroup vgSmall, ViewGroup viewLarge, ReadOnlyAmpel ampel) {
        super(context, vgSmall, viewLarge, ampel);

        String prefString = PrefHelper.getPrefs().getString("alarmZeit", "20");
        alarmZeitInMS = Long.parseLong(prefString)*1000;
    }

    @Override
    protected String getTag() {
        return "alarm";
    }

    @Override
    public int getColor() {
        return context.getResources().getColor(R.color.timeviewRed);
    }

    @Override
    protected String getText() {
        return "Alarm";
    }

    @Override
    protected String getTime() {
        return zeitString(alarmZeitInMS);
    }
}
