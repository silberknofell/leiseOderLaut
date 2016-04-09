package de.geihe.leiseOderLaut.alarmManager;

import de.geihe.leiseOderLaut.AlarmListener;
import de.geihe.leiseOderLaut.preferences.PrefHelper;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 07.10.2015.
 */
public class AlarmManagerExtra extends AlarmManager {

    private long extraZeit;

    public AlarmManagerExtra(ReadOnlyAmpel ampel, AlarmListener alarmListener) {
        super(ampel, alarmListener);
        this.extraZeit = PrefHelper.getExtrazeit();
    }

    private long getAktuelleZeit() {
        return System.currentTimeMillis();
    }

    @Override
    public boolean isAlarm() {
        return ampel.getLautzeit() >= extraZeit;
    }
}
