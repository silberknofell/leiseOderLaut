package de.geihe.leiseOderLaut.timeView;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.geihe.leiseOderLaut.ampel.ReadOnlyAmpel;
import de.geihe.leiseOderLaut.ampel.ZustandsStatistik;
import de.geihe.leiseOderLaut.preferences.PrefHelper;

/**
 * Created by test on 19.09.2015.
 */
public abstract class TimeView implements View.OnClickListener {

    public static final int TEXT_SIZE_SMALL = 22;
    public static final int TEXT_SIZE_LARGE = 111;
    protected ReadOnlyAmpel ampel;
    protected Context context;
    private TextView tvKlein;
    private TextView tvGross;
    private ViewGroup vgLarge;
    private ViewGroup vgSmall;
    private boolean isLarge;

    public TimeView(Context context, ViewGroup vgSmall, ViewGroup viewLarge, ReadOnlyAmpel ampel) {
        this.vgLarge = viewLarge;
        this.vgSmall = vgSmall;
        this.ampel = ampel;
        this.context = context;

        createTextViewSmall(context);
        createTextViewLarge(context);
        setColor(getColor());
    }

    public static String zeitString(long zeitInMS) {
        int vorzeichen = Long.signum(zeitInMS);
        long zeitInS = Math.round(zeitInMS / 1000.0 * vorzeichen); //damit auf jeden Fall positiv
        String minutenString = Long.toString(zeitInS / 60);
        long sekunden = zeitInS % 60;
        String sekundenString = (sekunden < 10) ? "0" + Long.toString(sekunden)
                : Long.toString(sekunden);
        String vorzeichenString = (vorzeichen < 0) ? "-" : "";
        return vorzeichenString + minutenString + ":" + sekundenString;
    }

    protected abstract String getTag();

    private void createTextViewLarge(Context context) {
        tvGross = new TextView(context);
        tvGross.setTextSize(TEXT_SIZE_LARGE);
        tvGross.setGravity(Gravity.CENTER_HORIZONTAL);

        tvGross.setOnClickListener(this);
    }

    private void createTextViewSmall(Context context) {
        tvKlein = new TextView(context);
        tvKlein.setTextSize(TEXT_SIZE_SMALL);
        tvKlein.setGravity(Gravity.CENTER_HORIZONTAL);

        tvKlein.setOnClickListener(this);
    }

    public void show() {
        vgSmall.addView(tvKlein);
        isLarge = PrefHelper.isTimeViewLarge(getTag());
        if (isLarge) {
            showLarge();
        }
    }

    @Override
    public void onClick(View v) {
        toggleLarge();
    }

    public abstract int getColor();

    public void setColor(int color) {
        tvKlein.setTextColor(color);
        tvGross.setTextColor(color);
    }

    public void update() {
        tvKlein.setText(getContent());
        tvGross.setText(getTime());
    }

    ;

    protected String getContent() {
        return getText() + "\n" + getTime();
    }

    protected abstract String getText();

    protected abstract String getTime();

    protected ZustandsStatistik[] getStatistik() {
        return ampel.getStatistik();
    }

    public View getTextViewSmall() {
        return tvKlein;
    }

    private void showLarge() {
        vgLarge.removeAllViews();
        vgLarge.addView(tvGross);
        isLarge = true;
        setPref();
    }

    public void hideLarge() {
        vgLarge.removeView(tvGross);
        isLarge = false;
        setPref();
    }

    private void setPref() {
        PrefHelper.setTimeViewLarge(getTag(), isLarge);
    }

    public void remove() {
        vgLarge.removeAllViews();
        isLarge = false;
        vgSmall.removeView(tvKlein);
    }

    public void toggleLarge() {
        if (isLarge) {
            hideLarge();
        } else {
            showLarge();
        }
    }

    private boolean largeIsVisible() {
        return vgLarge.indexOfChild(tvGross) >= 0;
    }
}
