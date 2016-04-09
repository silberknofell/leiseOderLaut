package de.geihe.leiseOderLaut.ampel;

import android.content.SharedPreferences;

import de.geihe.leiseOderLaut.Lautstaerkemesser;
import de.geihe.leiseOderLaut.preferences.PrefHelper;

/**
 * Created by test on 24.10.2015.
 */
public class PersistantAmpel extends Ampel {
    public static final String GRENZE_GRUEN = "grenzeGruen";
    public static final String GRENZE_GELB = "grenzeGelb";
    public static final String ZUSTAND_GRUEN = "zustand_gruen";
    public static final String ZUSTAND_GELB = "zustand_gelb";
    public static final String ZUSTAND_ROT = "zustand_rot";
    private static final String ZUSTAND_LEER = ZustandsStatistik.LEER_STRING;

    private SharedPreferences prefs;

    public PersistantAmpel(Lautstaerkemesser lautstaerkeMesser, SharedPreferences prefs) {
        super(lautstaerkeMesser);
        this.prefs = prefs;
    }

    public void saveState() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(GRENZE_GRUEN, p_grenzeGruen);
        editor.putFloat(GRENZE_GELB, p_grenzeGelb);
        editor.putString(ZUSTAND_GRUEN, statistik[ReadOnlyAmpel.ZUSTANDGRUEN].toString());
        editor.putString(ZUSTAND_GELB, statistik[ReadOnlyAmpel.ZUSTANDGELB].toString());
        editor.putString(ZUSTAND_ROT, statistik[ReadOnlyAmpel.ZUSTANDROT].toString());
        editor.apply();
    }

    public void readState() {
        if (PrefHelper.hasPrefs()) {
            p_grenzeGruen = prefs.getFloat(GRENZE_GRUEN, 0.25f);
            p_grenzeGelb = prefs.getFloat(GRENZE_GELB, 0.5f);
            String stringGruen = prefs.getString(ZUSTAND_GRUEN, ZUSTAND_LEER);
            String stringGelb = prefs.getString(ZUSTAND_GELB, ZUSTAND_LEER);
            String stringRot = prefs.getString(ZUSTAND_ROT, ZUSTAND_LEER);
            statistik[ReadOnlyAmpel.ZUSTANDGRUEN].readFromString(stringGruen);
            statistik[ReadOnlyAmpel.ZUSTANDGELB].readFromString(stringGelb);
            statistik[ReadOnlyAmpel.ZUSTANDROT].readFromString(stringRot);
        }
    }

    @Override
    public void start() {
        super.start();
        readState();
        aktualisiereZustand();
    }

    @Override
    public void stop() {
        super.stop();
        saveState();
    }
}
