package de.geihe.leiseOderLaut.preferences;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;

import java.util.ArrayList;
import java.util.List;

import de.geihe.leiseOderLaut.R;

/**
 * Created by test on 07.10.2015.
 */
public class AmpelPreferenceFragmentMode extends SettingsFragment implements Preference.OnPreferenceClickListener {
    List<CheckBoxPreference> cbpList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_mode);

        cbpList = new ArrayList<CheckBoxPreference>();
        cbpList.add((CheckBoxPreference) getPreferenceManager()
                .findPreference("nurAnzeige"));
        cbpList.add((CheckBoxPreference) getPreferenceManager()
                .findPreference("alarm"));
        cbpList.add((CheckBoxPreference) getPreferenceManager()
                .findPreference("countdown"));
        cbpList.add((CheckBoxPreference) getPreferenceManager()
                .findPreference("extraZeit"));
        for (CheckBoxPreference cbp : cbpList) {
            cbp.setOnPreferenceClickListener(this);
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        checkModes(preference);
        checkAlarmOptions(preference);
        checkDisplayOptions(preference);
        return false;
    }

    private void checkModes(Preference preference) {
        for (CheckBoxPreference cbp : cbpList) {
            if (!cbp.getKey().equals(preference.getKey()) && cbp.isChecked()) {
                cbp.setChecked(false);
            }
        }
    }

    private void checkAlarmOptions(Preference preference) {
    //TODO
    }

    private void checkDisplayOptions(Preference preference) {
    //TODO
    }

}