package de.geihe.leiseOderLaut.timeView;

import android.content.Context;
import android.view.ViewGroup;

import de.geihe.leiseOderLaut.R;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 06.10.2015.
 */
public class TimeViewExtraExtrazeit extends TimeViewExtra {
    public TimeViewExtraExtrazeit(Context context, ViewGroup vgSmall, ViewGroup viewLarge, ReadOnlyAmpel ampel) {
        super(context, vgSmall, viewLarge, ampel);
    }

    @Override
    protected String getTag() {
        return "extra_extrazeit";
    }

    @Override
    public int getColor() {
        return context.getResources().getColor(R.color.timeview_extra);
    }

    @Override
    protected String getText() {
        return "Extra\n";
    }

    @Override
    protected String getTime() {
        return zeitString(getAktuelleExtrazeit());
    }
}
