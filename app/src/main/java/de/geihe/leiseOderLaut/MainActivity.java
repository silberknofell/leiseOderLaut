package de.geihe.leiseOderLaut;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.geihe.leiseOderLaut.alarmManager.AlarmManagerFactory;
import de.geihe.leiseOderLaut.ampel.Ampel;
import de.geihe.leiseOderLaut.ampel.AmpelController;
import de.geihe.leiseOderLaut.ampel.AmpelView;
import de.geihe.leiseOderLaut.ampel.PersistantAmpel;
import de.geihe.leiseOderLaut.mode.ModeChangeListener;
import de.geihe.leiseOderLaut.mode.ModeSelector;
import de.geihe.leiseOderLaut.preferences.AmpelPreferenceActivity;
import de.geihe.leiseOderLaut.preferences.PrefHelper;
import de.geihe.leiseOderLaut.timeView.TimeViewFactory;
import de.geihe.leiseOderLaut.timeView.TimeViewManager;


public class MainActivity extends Activity implements AlarmListener, Updater, ModeChangeListener {


    private State state = State.NEW;
    private Loop loop;
    private MenuItem item_quit;
    private MenuItem item_mute;
    private MenuItem item_settings;
    private MenuItem item_reset;
    private MenuItem item_start;
    private MenuItem item_pause;
    private TextView tvPause;
    private Ringtone ringtone = null;
    private boolean p_alarmBlinker = true;
    private boolean p_alarmTon = true;
    private String p_ringtone;
    private AmpelController ampelController;
    private AmpelView ampelView;
    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preferences_mode, false);
        setContentView(R.layout.activity_main);

        ActionBar bar = getActionBar();

        PrefHelper.setPrefs(PreferenceManager.getDefaultSharedPreferences(this));

        createAmpelControllerEtc();

        tvPause = (TextView) findViewById(R.id.textViewPause);

        ModeSelector modeSelector = new ModeSelector(this);
        modeSelector.add(findViewById(R.id.button_simple_view));
        modeSelector.add(findViewById(R.id.button_alarm));
        modeSelector.add(findViewById(R.id.button_simple_countdown));
        modeSelector.add(findViewById(R.id.button_extra_time));

        loop = new Loop(this);
        state = State.NEW;
    }

    public void buttonReset(View view) {
    }

    public void buttonStop(View view) {
    }

    private void createAmpelControllerEtc() {
        Lautstaerkemesser lautstaerkeMesser = new Lautstaerkemesser();
        Ampel ampel = new PersistantAmpel(lautstaerkeMesser, PrefHelper.getPrefs());
        ampelView = (AmpelView) findViewById(R.id.anzeige);
        ampelView.setAmpel(ampel);

        ViewGroup vgLarge = (ViewGroup) findViewById(R.id.anzeigeGross);
        ViewGroup vgSmall = (ViewGroup) findViewById(R.id.fussZeile);
        TimeViewFactory timeViewFactory = new TimeViewFactory(this, vgSmall, vgLarge, ampel);
        TimeViewManager timeViewManager = new TimeViewManager(timeViewFactory);
        AlarmManagerFactory alarmManagerFactory = new AlarmManagerFactory(ampel, this);

        ampelController = new AmpelController(ampel, ampelView);
        mainController = new MainController(timeViewManager, alarmManagerFactory, ampelController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        item_quit = menu.findItem(R.id.action_quit);
        item_mute = menu.findItem(R.id.action_mute);
        item_settings = menu.findItem(R.id.action_settings);
        item_reset = menu.findItem(R.id.action_reset);
        item_start = menu.findItem(R.id.action_start);
        item_pause = menu.findItem(R.id.action_pause);
        setState(State.PAUSE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;
        stopRingtone();
        switch (item.getItemId()) {
            case (R.id.action_pause):
                stop();
                break;
            case (R.id.action_quit):
                quit();
                break;
            case (R.id.action_reset):
                reset();
                break;
            case (R.id.action_start):
                start();
                break;
            case (R.id.action_settings):
                Intent prefsInt = new Intent(this, AmpelPreferenceActivity.class);
                startActivity(prefsInt);
                break;
            case (R.id.action_mute):
                ampelController.toggleMute();
                break;
            default:
                result = false;
        }
        return result;
    }//                Intent countdownPrefsInt = new Intent(this, CountDownPrefs.class);

    private void setState(State state) {
        this.state = state;
        switch (state) {
            case PREPARED:
                tvPause.setVisibility(View.GONE);
                item_quit.setVisible(true);
                item_mute.setVisible(false);
                item_settings.setVisible(true);
                item_reset.setVisible(false);
                item_start.setVisible(true);
                item_pause.setVisible(false);
                break;
            case PAUSE:
                tvPause.setVisibility(View.VISIBLE);
                item_quit.setVisible(true);
                item_mute.setVisible(false);
                item_settings.setVisible(true);
                item_reset.setVisible(true);
                item_start.setVisible(true);
                item_pause.setVisible(false);
                break;
            case RUNNING:
                tvPause.setVisibility(View.GONE);
                item_quit.setVisible(false);
                item_mute.setVisible(true);
                item_settings.setVisible(false);
                item_reset.setVisible(false);
                item_start.setVisible(false);
                item_pause.setVisible(true);
                break;

        }
    }
//                startActivityForResult(countdownPrefsInt, PREFS_COUNTDOWN);

    public void start() {
        readApplicationPreferences();
        setRingtonVolumeMax();

        mainController.start();
        loop.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        stop();
    }

    public void stop() {
        stopRingtone();
        mainController.stop();
        loop.stop();
        setState(State.PAUSE);
    }

    private void stopRingtone() {
        if (ringtone != null) {
            ringtone.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        start();
    }

    public void quit() {
        onPause();
        finish();
    }

    public void reset() {
        stopRingtone();
        setState(State.PREPARED);
        mainController.reset();
    }

    public void alarm() {
        reset();
        ampelController.stop();

        if (p_alarmTon) {
            ringtone = RingtoneManager.getRingtone(this, Uri.parse(p_ringtone));
            if (ringtone != null) {
                ringtone.play();
            }
        }
        if (p_alarmBlinker) {
            new Blink(tvPause);
        }
    }

    private void readApplicationPreferences() {
        ampelView.readPrefs();
        p_alarmBlinker = PrefHelper.getIsAlarmBlinker();
        p_alarmTon = PrefHelper.getIsAlarmSound();
        p_ringtone = PrefHelper.getRingTone();
    }

    private void setRingtonVolumeMax() {
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        am.setStreamVolume(
                AudioManager.STREAM_RING,
                am.getStreamMaxVolume(AudioManager.STREAM_RING),
                0);
    }

    @Override
    public void update() {
        mainController.update();
    }

    @Override
    public void onModeChanged(int mode) {
        mainController.setMode(mode);
    }


    public enum State {NEW, PREPARED, RUNNING, PAUSE, ALARM}
}
