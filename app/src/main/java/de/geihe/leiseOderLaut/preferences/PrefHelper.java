package de.geihe.leiseOderLaut.preferences;

import android.content.SharedPreferences;

import de.geihe.leiseOderLaut.mode.Mode;

/**
 * Created by test on 09.10.2015.
 */
public class PrefHelper {

    public static final String ABFRAGE_INTERVALL = "abfrageIntervall";
    public static final String ALARM_BLINKER = "alarmBlinker";
    public static final String ALARM_TON = "alarmTon";
    public static final String RINGTONE = "ringtone";
    public static final String SKALA_ANZEIGEN = "skalaAnzeigen";
    public static final String PADDING_LEFT_RIGHT = "padding_left_right";

    public static final String TIMEVIEW_PREFIX = "tv_";

    public static final String COUNTDOWN_ZEIT = "countdownZeit";
    public static final String ENDZEIT = "endzeit";
    public static final String EXTRAZEIT = "extrazeit";

    private static SharedPreferences prefs = null;

    public static SharedPreferences getPrefs() {
        return prefs;
    }


    public static void setPrefs(SharedPreferences prefs) {
        PrefHelper.prefs = prefs;
    }

    public static SharedPreferences.Editor getEditor() {
        if (prefs != null) {
            return prefs.edit();
        }
        return null;
    }

    public static boolean hasPrefs() {
        return prefs != null;
    }

    public static int getAbfrageIntervall() {
        String ai = getPrefs().getString(ABFRAGE_INTERVALL, "100");
        return Integer.parseInt(ai);
    }

    public static boolean getIsAlarmBlinker() {
        return getPrefs().getBoolean(ALARM_BLINKER, true);
    }

    public static boolean getIsAlarmSound() {
        return getPrefs().getBoolean(
                ALARM_TON, true);
    }

    public static String getRingTone() {
        return getPrefs().getString(RINGTONE, null);
    }

    public static boolean getIsSkalaVisible() {
        return getPrefs().getBoolean(SKALA_ANZEIGEN, true);
    }

    public static int getPaddingLeftRight(int vorgabe) {
        return getPrefs().getInt(
                PADDING_LEFT_RIGHT, vorgabe);
    }

    public static boolean isTimeViewLarge(String tag) {
        String key = TIMEVIEW_PREFIX + tag;
        return getPrefs().getBoolean(key, false);
    }

    public static void setTimeViewLarge(String tag, boolean isLarge) {
        String key = TIMEVIEW_PREFIX + tag;
        SharedPreferences.Editor editor = getEditor();

        if (editor != null) {
            editor.putBoolean(key, isLarge);
            editor.apply();
        }
    }

    public static long getCountdownStart() {
        String cds = getPrefs().getString(PrefHelper.COUNTDOWN_ZEIT, "300000");
        return str2ms(cds);
    }

    public static long ms2min(long ms) {
        return ms / 1000 / 60;
    }

    public static long min2ms(long min) {
        return min * 60 * 1000;
    }

    public static long minstr2ms(String cds) {
        return min2ms(Long.parseLong(cds));
    }

    public static long str2ms(String cds) {
        return Long.parseLong(cds);
    }

    public static String min2str(long min) {
        return ms2str(min2ms(min));
    }

    public static String ms2str(long ms) {
        return Long.toString(ms);
    }

    public static String ms2minstr(long ms) {
        return Long.toString(ms2min(ms));
    }

    public static String readMinStr(String key, long defaultMin) {
        String defString = min2str(defaultMin);
        String str = getPrefs().getString(key, defString);
        long ms = str2ms(str);
        return ms2minstr(ms);
    }

    public static long getEndzeit() {
        String cds = getPrefs().getString(PrefHelper.ENDZEIT, "47400000");
        return str2ms(cds);
    }

    public static long getExtrazeit() {
        String cds = getPrefs().getString(PrefHelper.EXTRAZEIT, "30000");
        return str2ms(cds);
    }

    public static int getMode() {
        int modeInt = getPrefs().getInt(Mode.KEY, Mode.SIMPLE_VIEW);
        return modeInt;
    }

    public static void setMode(int modeIndex) {
        SharedPreferences.Editor editor = getEditor();
        if (editor != null) {
            editor.putInt(Mode.KEY, modeIndex);
            editor.apply();
        }
    }
}
