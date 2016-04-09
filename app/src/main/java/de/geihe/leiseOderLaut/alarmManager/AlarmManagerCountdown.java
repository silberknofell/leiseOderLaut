package de.geihe.leiseOderLaut.alarmManager;

import de.geihe.leiseOderLaut.AlarmListener;
import de.geihe.leiseOderLaut.preferences.PrefHelper;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 07.10.2015.
 */
public class AlarmManagerCountdown extends AlarmManager {
    private long countDownStart;

    public AlarmManagerCountdown(ReadOnlyAmpel ampel, AlarmListener alarmListener) {
        super(ampel, alarmListener);
        this.countDownStart = PrefHelper.getCountdownStart();
    }

    @Override
    public boolean isAlarm() {
        return countDownStart <= ampel.getGruenzeit();
    }
}
