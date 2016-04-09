package de.geihe.leiseOderLaut;

import android.os.Handler;

import de.geihe.leiseOderLaut.preferences.PrefHelper;

/**
 * Created by test on 27.09.2015.
 */
public class Loop implements Runnable {


    private Handler handler;
    private Updater updater;
    private int abfrageIntervall = 100;

    public Loop(Updater updater) {
        handler = new Handler();
        this.updater = updater;
    }

    @Override
    public void run() {
        updater.update();
        handler.postDelayed(this, abfrageIntervall);
    }

    public void start() {
        abfrageIntervall = PrefHelper.getAbfrageIntervall();
        handler.post(this);
    }

    public void stop() {
        handler.removeCallbacks(this);
    }

}
