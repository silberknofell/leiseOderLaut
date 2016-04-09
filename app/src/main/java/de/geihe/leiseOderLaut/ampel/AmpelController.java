package de.geihe.leiseOderLaut.ampel;

import static de.geihe.leiseOderLaut.R.drawable.grad_background;
import static de.geihe.leiseOderLaut.R.drawable.grad_background_mute;
import static de.geihe.leiseOderLaut.R.drawable.grad_background_pause;

/**
 * Created by test on 30.09.2015.
 */
public class AmpelController {
    private Ampel ampel;
    private AmpelView ampelView;

    public AmpelController(Ampel ampel, AmpelView ampelView) {
        this.ampel = ampel;
        this.ampelView = ampelView;
    }

    public void pause() {
        ampelView.setBackgroundResource(grad_background_pause);
        ampel.stop();
    }

    public void stop() {
        ampel.stop();
    }

    public void start() {
        resume();
    }

    public void reset() {
        ampel.setzeStatistikZurueck();
        ampelView.setBackgroundResource(grad_background);
        update();
    }

    public void update() {
        ampel.update();
        ampelView.update();
    }

    public void toggleMute() {
        ampel.toggleMute();
        if (ampel.isMute()) {
            ampelView.setBackgroundResource(grad_background_mute);
        } else {
            ampelView.setBackgroundResource(grad_background);
        }
    }

    public void resume() {
        ampelView.setBackgroundResource(grad_background);
        ampel.setMute(false);
        ampel.start();
    }
}
