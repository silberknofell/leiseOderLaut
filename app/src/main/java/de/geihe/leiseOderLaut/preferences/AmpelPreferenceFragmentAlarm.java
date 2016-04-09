package de.geihe.leiseOderLaut.preferences;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;

import java.util.List;

import de.geihe.leiseOderLaut.R;

/**
 * Created by test on 07.10.2015.
 */
public class AmpelPreferenceFragmentAlarm extends SettingsFragment implements Preference.OnPreferenceClickListener {
    List<CheckBoxPreference> cbpList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_alarm);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }
}

