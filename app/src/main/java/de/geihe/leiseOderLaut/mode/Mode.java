package de.geihe.leiseOderLaut.mode;

import de.geihe.leiseOderLaut.preferences.PrefHelper;

/**
 * Created by test on 05.10.2015.
 */
public class Mode {

    public static final int SIMPLE_VIEW = 1;
    public static final int ALARM = 2;
    public static final int SIMPLE_COUNTDOWN = 3;
    public static final int EXTRA_TIME = 4;
    public static final int ANZAHL = 4;

    public static final String KEY = "mode";

    private int mode;

    public Mode() {
        this(SIMPLE_VIEW);
    }

    public Mode(int mode) {
        this.mode = mode;
    }

    public static int readModeFromPrefs() {
        return PrefHelper.getMode();
    }

    public int getModeIndex() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
