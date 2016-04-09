package de.geihe.leiseOderLaut.alarmManager;

import de.geihe.leiseOderLaut.AlarmListener;
import de.geihe.leiseOderLaut.mode.Mode;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 07.10.2015.
 */
public class AlarmManagerFactory {
    private ReadOnlyAmpel ampel;
    private AlarmListener alarmListener;


    public AlarmManagerFactory(ReadOnlyAmpel ampel, AlarmListener alarmListener) {
        this.ampel = ampel;
        this.alarmListener = alarmListener;
    }

    public AlarmManager create(int mode) {
        switch (mode) {
            case Mode.ALARM:
                return new AlarmManagerAlarm(ampel, alarmListener);
            case Mode.SIMPLE_COUNTDOWN:
                return new AlarmManagerCountdown(ampel, alarmListener);
            case Mode.EXTRA_TIME:
                return new AlarmManagerExtra(ampel, alarmListener);
        }
        return new AlarmManagerSimpleView(ampel, alarmListener);
    }
}