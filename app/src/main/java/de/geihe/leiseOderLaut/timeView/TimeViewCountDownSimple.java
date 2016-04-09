package de.geihe.leiseOderLaut.timeView;

import android.content.Context;
import android.view.ViewGroup;

import de.geihe.leiseOderLaut.preferences.PrefHelper;
import de.geihe.leiseOderLaut.R;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 05.10.2015.
 */
public class TimeViewCountDownSimple extends TimeView {

    private long countDownStart;

    public TimeViewCountDownSimple
            (Context context, ViewGroup vgSmall, ViewGroup viewLarge,
             ReadOnlyAmpel ampel) {
        super(context, vgSmall, viewLarge, ampel);

        this.countDownStart = PrefHelper.getCountdownStart();
    }


    @Override
    protected String getTag() {
        return "countdown_single";
    }

    @Override
    public int getColor() {
        return context.getResources().getColor(R.color.timeviewcountdown);
    }

    @Override
    protected String getTime() {
        long restZeit = countDownStart - ampel.getGruenzeit();
        return zeitString(-restZeit);
    }

    @Override
    protected String getText() {
        return "Countdown";
    }
}
