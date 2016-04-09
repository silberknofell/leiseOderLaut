package de.geihe.leiseOderLaut.mode;

import android.view.View;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import de.geihe.leiseOderLaut.preferences.PrefHelper;

/**
 * Created by test on 15.10.2015.
 */
public class ModeSelector implements View.OnClickListener {
    private final ModeChangeListener modeChangeListener;
    private List<ToggleButton> list;
    private int mode;

    public ModeSelector(ModeChangeListener modeChangeListener) {
        list = new ArrayList<ToggleButton>();
        this.modeChangeListener = modeChangeListener;
    }

    public void add(View v) {
        ToggleButton toggleButton = (ToggleButton) v;
        if (list.isEmpty()) {
            toggleButton.setChecked(true);
        } else {
            toggleButton.setChecked(false);
        }
        toggleButton.setOnClickListener(this);
        list.add(toggleButton);
    }

    @Override
    public void onClick(View v) {
        ToggleButton tb = (ToggleButton) v;
        for (ToggleButton tbList : list) {
            if (tbList.isChecked() && tbList != tb) {
                tbList.setChecked(false);
                tbList.setEnabled(true);
            }
        }
        tb.setEnabled(false);
        Object tag = tb.getTag();
        if (tag != null) {
            setMode(tag.toString());
        }
    }


    private void setMode(String tag) {
        if (tag.equals("simple_view")) {
            mode = Mode.SIMPLE_VIEW;
        }
        if (tag.equals("alarm")) {
            mode = Mode.ALARM;
        }
        if (tag.equals("simple_countdown")) {
            mode = Mode.SIMPLE_COUNTDOWN;
        }
        if (tag.equals("extra_time")) {
            mode = Mode.EXTRA_TIME;
        }
        PrefHelper.setMode(mode);
        modeChangeListener.onModeChanged(mode);
    }


}
