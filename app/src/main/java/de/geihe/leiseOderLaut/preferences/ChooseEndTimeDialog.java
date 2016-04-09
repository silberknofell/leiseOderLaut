package de.geihe.leiseOderLaut.preferences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.geihe.leiseOderLaut.R;

/**
 * Created by test on 10.10.2015.
 */
public class ChooseEndTimeDialog extends DialogPreference
        implements View.OnClickListener {

    public static final String ENDEZEIT = "endezeit";

    public final static int[] STUFEN_IN_MIN = {
            60 * 1 + 0,
            60 * 2 + 0,
            60 * 3 + 0,
            60 * 4 + 0,
            60 * 5 + 0,
            60 * 6 + 0,
            60 * 7 + 0,
            60 * 8 + 0,
            60 * 8 + 45,
            60 * 9 + 30,
            60 * 10 + 30,
            60 * 11 + 15,
            60 * 12 + 20,
            60 * 13 + 10,
            60 * 14 + 0,
            60 * 14 + 45,
            60 * 15 + 30,
            60 * 16 + 15,
            60 * 17 + 00,
            60 * 18 + 00,
            60 * 19 + 00,
            60 * 20 + 00,
            60 * 21 + 00,
            60 * 22 + 00,
            60 * 23 + 00
    };
    public final static int ANZAHL_STUFEN = STUFEN_IN_MIN.length;
    private TimePicker timePicker;

    public ChooseEndTimeDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPersistent(false);
        setDialogLayoutResource(R.layout.preference_choose_endtime);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setTitle("Auswahl Endzeit");
//        builder.setPositiveButton(null, null);
        builder.setNegativeButton(null, null);
        super.onPrepareDialogBuilder(builder);
    }

    @Override
    public void onBindDialogView(View view) {
        Button bt_ende_p = (Button) view.findViewById(R.id.endeButton_p);
        Button bt_ende_m = (Button) view.findViewById(R.id.endeButton_m);
        bt_ende_p.setOnClickListener(this);
        bt_ende_m.setOnClickListener(this);

        timePicker = (TimePicker) view.findViewById(R.id.timePicker);

        super.onBindDialogView(view);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {

        if (positiveResult) {

            SharedPreferences.Editor editor = PrefHelper.getEditor();
            long endeZeit = leseEndeZeit();
            editor.putString(getKey(), PrefHelper.ms2str(endeZeit));
            editor.commit();
//            setSumText(value);
        }

        super.onDialogClosed(positiveResult);
    }

    @Override
    public void onClick(View v) {
        int extra;
        switch (v.getId()) {
            case R.id.endeButton_p:
                setzeNaechsteStufe(leseEndeZeitInMin());
                break;
            case R.id.endeButton_m:
                setzeLetzteStufe(leseEndeZeitInMin());
                break;

            default:
                break;
        }
    }

    public void setzeLetzteStufe(int minuten) {
        int stufe = 0;
        while (STUFEN_IN_MIN[stufe] < minuten && stufe < ANZAHL_STUFEN) {
            stufe++;
        }
        stufe--;
        if (stufe < 0) {
            stufe = ANZAHL_STUFEN - 1;

        }
        setzeTimePicker(stufe);
    }

    public void setzeNaechsteStufe(int minuten) {
        int stufe = 0;
        while (STUFEN_IN_MIN[stufe] <= minuten && stufe < ANZAHL_STUFEN) {
            stufe++;
        }
        if (stufe >= ANZAHL_STUFEN) {
            stufe = 0;
        }
        setzeTimePicker(stufe);
    }

    public void setzeNaechsteStufe() { //verwendet aktuelle Zeit
        Calendar calendar = new GregorianCalendar();
        int m = calendar.get(Calendar.MINUTE);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        setzeNaechsteStufe(60 * h + m);
    }

    public int leseEndeZeitInMin() {
        int h = timePicker.getCurrentHour();
        int m = timePicker.getCurrentMinute();
        return 60 * h + m;
    }
    public long leseEndeZeit() {
        int h=timePicker.getCurrentHour();
        int m=timePicker.getCurrentMinute();
        Calendar ende=new GregorianCalendar();
        ende.set(Calendar.HOUR_OF_DAY, h);
        ende.set(Calendar.MINUTE, m);
        ende.set(Calendar.SECOND, 0);
        ende.set(Calendar.MILLISECOND, 0);

        return ende.getTimeInMillis();
    }
    private void setzeTimePicker(int stufe) {
        timePicker.setCurrentHour(STUFEN_IN_MIN[stufe] / 60);
        timePicker.setCurrentMinute(STUFEN_IN_MIN[stufe] % 60);
    }
}
