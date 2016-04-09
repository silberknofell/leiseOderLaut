package de.geihe.leiseOderLaut.preferences;

import android.preference.PreferenceActivity;

import java.util.List;

import de.geihe.leiseOderLaut.R;

/**
 * Created by test on 07.10.2015.
 */
public class AmpelPreferenceActivity extends PreferenceActivity {
    @Override
    public void onBuildHeaders(List<Header> target) {
       loadHeadersFromResource(R.xml.preference_headers, target);
    }
}
