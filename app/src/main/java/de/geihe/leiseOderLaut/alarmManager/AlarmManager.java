package de.geihe.leiseOderLaut.alarmManager;

import de.geihe.leiseOderLaut.AlarmListener;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 07.10.2015.
 */
public abstract class AlarmManager {
    protected ReadOnlyAmpel ampel;
    protected AlarmListener alarmListener;

    public AlarmManager(ReadOnlyAmpel ampel, AlarmListener alarmListener) {
        this.ampel = ampel;
        this.alarmListener = alarmListener;
    }

    public void checkAlarm() {
        if (isAlarm()) {
            alarmListener.alarm();
        }
    }

    public abstract boolean isAlarm();
}
