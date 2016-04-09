package de.geihe.leiseOderLaut.timeView;

import android.content.Context;
import android.view.ViewGroup;

import de.geihe.leiseOderLaut.preferences.PrefHelper;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 06.10.2015.
 */
public abstract class TimeViewExtra extends TimeView{

    private long endZeit;
    private long extraZeit;

    public TimeViewExtra(Context context, ViewGroup vgSmall, ViewGroup viewLarge, ReadOnlyAmpel ampel) {
        super(context, vgSmall, viewLarge, ampel);
        this.endZeit = PrefHelper.getEndzeit();
        this.extraZeit = PrefHelper.getExtrazeit();
    }

    @Override
    protected String getTag() {
        return "extra";
    }

    public long getAktuelleExtrazeit() {
        return extraZeit - ampel.getLautzeit();
    }

    private long getAktuelleZeit() {
        return System.currentTimeMillis();
    }

    public long getCountDownZeit() {
        return endZeit - getAktuelleExtrazeit() - getAktuelleZeit();    }
}
