package de.geihe.leiseOderLaut.alarmManager;

import de.geihe.leiseOderLaut.AlarmListener;
import de.geihe.leiseOderLaut.preferences.PrefHelper;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 07.10.2015.
 */
public class AlarmManagerAlarm extends AlarmManager {

    protected long alarmZeitInMS;

    public AlarmManagerAlarm(ReadOnlyAmpel ampel, AlarmListener alarmListener) {
        super(ampel, alarmListener);
        String prefString = PrefHelper.getPrefs().getString("alarmZeit", "20");
        alarmZeitInMS = Long.parseLong(prefString)*1000;
    }

    @Override
    public boolean isAlarm() {
        return ampel.getRotzeit() >= alarmZeitInMS;
    }
}
