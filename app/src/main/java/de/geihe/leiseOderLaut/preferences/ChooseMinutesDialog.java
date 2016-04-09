package de.geihe.leiseOderLaut.preferences;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import de.geihe.leiseOderLaut.R;

/**
 * Created by test on 10.10.2015.
 */
public class ChooseMinutesDialog extends DialogPreference
        implements OnClickListener {

    private EditText edExtra;

    public ChooseMinutesDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPersistent(false);
        setDialogLayoutResource(R.layout.preference_choose_minutes);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setTitle("Auswahl Minuten");
//        builder.setPositiveButton(null, null);
        builder.setNegativeButton(null, null);
        super.onPrepareDialogBuilder(builder);
    }

    @Override
    public void onBindDialogView(View view){
        Button bt_extra_p = (Button) view.findViewById(R.id.button_p);
        Button bt_extra_m = (Button) view.findViewById(R.id.button_m);

        edExtra = (EditText) view.findViewById(R.id.edExtra);
        edExtra.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((EditText) v).setText("");
                return false;
            }
        });

        bt_extra_p.setOnClickListener(this);
        bt_extra_m.setOnClickListener(this);

        String minString = PrefHelper.readMinStr(getKey(), 5);
        edExtra.setText(minString);
        setSumText(minString);

        super.onBindDialogView(view);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {

        if (positiveResult) {

            SharedPreferences.Editor editor = PrefHelper.getEditor();

            String minString = edExtra.getText().toString();
            int minuten = Integer.parseInt(minString);
            editor.putString(getKey(), PrefHelper.min2str(minuten));
            editor.commit();
            setSumText(minString);
        }

        super.onDialogClosed(positiveResult);
    }

    private void setSumText(String value) {
        setSummary(value + " Minute(n)");
    }

    @Override
    public void onClick(View v) {
        int extra;
        switch (v.getId()) {
            case R.id.button_p:
                extra= leseExtraZeitInMin()+1;
                if (extra>10) {
                    extra=(extra/5+1)*5;
                }
                setzeExtra(extra);
                break;
            case R.id.button_m:
                extra= leseExtraZeitInMin()-1;
                if (extra>10) {
                    extra=(extra/5)*5;
                }
                if (extra<0) {
                    extra=0;
                }
                setzeExtra(extra);
            default: break;
        }

    }
    private void setzeExtra(int extra) {
        edExtra.setText(Integer.toString(extra));
    }

    public int leseExtraZeitInMin() {
        String text=edExtra.getText().toString();
        if (text.length() == 0) {
            edExtra.setText("5");
            return 5;
        }
        return Integer.parseInt(text);
        //TODO check, ob Eingabe legal
    }

}
