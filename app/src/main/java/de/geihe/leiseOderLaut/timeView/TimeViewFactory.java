package de.geihe.leiseOderLaut.timeView;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.geihe.leiseOderLaut.mode.Mode;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 05.10.2015.
 */
public class TimeViewFactory {
    private final Context context;
    private final ViewGroup vgLarge;
    private final ViewGroup vgSmall;
    private final ReadOnlyAmpel ampel;


    public TimeViewFactory(Context context,
                           ViewGroup vgSmall, ViewGroup vgLarge, ReadOnlyAmpel ampel) {
        this.context = context;
        this.vgLarge = vgLarge;
        this.vgSmall = vgSmall;
        this.ampel = ampel;
    }

    public List<TimeView> createTimeViewList(Mode mode) {
        return createTimeViewList(mode.getModeIndex());
    }

    public List<TimeView> createTimeViewList(int mode) {
        vgSmall.removeAllViews();
        vgLarge.removeAllViews();
        ArrayList<TimeView> list = new ArrayList<TimeView>();
        switch (mode) {
            case Mode.SIMPLE_VIEW:
                list.add(new TimeViewGruen(context, vgSmall, vgLarge, ampel));
                list.add(new TimeViewGelb(context, vgSmall, vgLarge, ampel));
                list.add(new TimeViewRot(context, vgSmall, vgLarge, ampel));
                break;
            case Mode.ALARM:
                list.add(new TimeViewAlarm
                        (context, vgSmall, vgLarge, ampel));
                list.add(new TimeViewAlarmRestzeit
                        (context, vgSmall, vgLarge, ampel));
                break;
            case Mode.SIMPLE_COUNTDOWN:
                list.add(new TimeViewCountDownSimple
                        (context, vgSmall, vgLarge, ampel));
                list.add(new TimeViewGruen(context, vgSmall, vgLarge, ampel));
                break;
            case Mode.EXTRA_TIME:
                list.add(new TimeViewExtraCountdown(context, vgSmall, vgLarge, ampel));
                list.add(new TimeViewExtraExtrazeit(context, vgSmall, vgLarge, ampel));
        }
        return list;
    }

}
