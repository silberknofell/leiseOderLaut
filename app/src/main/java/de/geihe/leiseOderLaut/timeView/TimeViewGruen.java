package de.geihe.leiseOderLaut.timeView;

import android.content.Context;
import android.view.ViewGroup;

import de.geihe.leiseOderLaut.ampel.Ampel;
import de.geihe.leiseOderLaut.R;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 04.10.2015.
 */
public class TimeViewGruen extends TimeView {

    public TimeViewGruen(Context context, ViewGroup vgSmall, ViewGroup vgLarge, ReadOnlyAmpel ampel) {
        super(context, vgSmall, vgLarge, ampel);
    }

    @Override
    protected String getTag() {
        return "gruen";
    }


    @Override
    public int getColor() {
        return context.getResources().getColor(R.color.timeview_Green);
    }

    @Override
    protected String getText() {
        return "Grünzeit";
    }

    @Override
    protected String getTime() {
        return zeitString(getStatistik()[Ampel.ZUSTANDGRUEN]
                .getGesamtZeitImZustand());
    }


}
