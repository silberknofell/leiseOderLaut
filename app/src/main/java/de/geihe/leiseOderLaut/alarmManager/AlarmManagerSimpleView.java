package de.geihe.leiseOderLaut.alarmManager;

import de.geihe.leiseOderLaut.AlarmListener;
import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;

/**
 * Created by test on 07.10.2015.
 */
public class AlarmManagerSimpleView extends AlarmManager {
    public AlarmManagerSimpleView(ReadOnlyAmpel ampel, AlarmListener alarmListener) {
        super(ampel, alarmListener);
    }

    @Override
    public boolean isAlarm() {
        return false;
    }


}
