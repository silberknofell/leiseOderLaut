package de.geihe.leiseOderLaut.timeView;

import android.content.Context;
import android.view.ViewGroup;

import de.geihe.leiseOderLaut.R;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 06.10.2015.
 */
public class TimeViewExtraCountdown extends TimeViewExtra {
    public TimeViewExtraCountdown(Context context, ViewGroup vgSmall, ViewGroup viewLarge, ReadOnlyAmpel ampel) {
        super(context, vgSmall, viewLarge, ampel);
    }

    @Override
    protected String getTag() {
        return "extra_countdown";
    }

    @Override
    public int getColor() {
        return context.getResources().getColor(R.color.timeviewcountdown);
    }

    @Override
    protected String getText() {
        return "Rest\n";
    }

    @Override
    protected String getTime() {
        return zeitString(-getCountDownZeit());
    }
}
