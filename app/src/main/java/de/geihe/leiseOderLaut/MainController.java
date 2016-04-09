package de.geihe.leiseOderLaut;

import de.geihe.leiseOderLaut.alarmManager.AlarmManager;
import de.geihe.leiseOderLaut.alarmManager.AlarmManagerFactory;
import de.geihe.leiseOderLaut.ampel.AmpelController;
import de.geihe.leiseOderLaut.preferences.PrefHelper;
import de.geihe.leiseOderLaut.timeView.TimeViewManager;

/**
 * Created by test on 01.11.2015.
 */
public class MainController  {
    private final TimeViewManager timeViewManager;
    private final AlarmManagerFactory alarmManagerFactory;
    private final AmpelController ampelController;
    private AlarmManager alarmManager;
    private int mode;


    public MainController(TimeViewManager timeViewManager
            , AlarmManagerFactory alarmManagerFactory
            , AmpelController ampelController) {
        this.timeViewManager = timeViewManager;
        this.alarmManagerFactory = alarmManagerFactory;
        this.ampelController = ampelController;
        setMode(PrefHelper.getMode());
    }

    public void setMode(int mode) {
        if (this.mode != mode) {
            modeChanged(mode);
        }
    }

    private void modeChanged(int newMode) {
        timeViewManager.removeViews(mode);
        mode = newMode;
        PrefHelper.setMode(newMode);
        timeViewManager.showViews(newMode);
        alarmManager = alarmManagerFactory.create(newMode);
    }

    public void update() {
        ampelController.update();
        timeViewManager.update(mode);
        alarmManager.checkAlarm();
    }

    public void start() {
        ampelController.start();
    }

    public void stop() {
        ampelController.pause();
    }

    public void reset() {
        ampelController.reset();
    }

    public boolean isAlarm() {
        return alarmManager.isAlarm();
    }
}
