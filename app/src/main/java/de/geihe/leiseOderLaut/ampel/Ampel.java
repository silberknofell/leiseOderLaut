package de.geihe.leiseOderLaut.ampel;

import java.io.IOException;

import de.geihe.leiseOderLaut.Lautstaerkemesser;

public class Ampel extends ReadOnlyAmpel {

	private Lautstaerkemesser lautstaerkeMesser;
	private boolean mute;

	public Ampel(Lautstaerkemesser lautstaerkeMesser) {
		this.lautstaerkeMesser = lautstaerkeMesser;

		mute = false;
		zustand = ZUSTANDGRUEN;
		for (int i = 1; i <= 3; i++) {
			statistik[i] = new ZustandsStatistik();
			istAktiv = false;
		}
		setzeStatistikZurueck();
	}


	public void update() {
		if (istAktiv) {
			if (mute) {
				lautstaerke = 0;
			} else {
				lautstaerke = lautstaerkeMesser.getSqrtAmplitudeEMA();
			}
			aktualisiereZustand();
		}
	}

	public void setzeStatistikZurueck() {
		for (int i = 1; i <= 3; i++) {
			statistik[i].init();
		}
	}

	public boolean isMute() {
		return mute;
	}

	public void setMute(boolean mute){
		this.mute = mute;
	}

	public void toggleMute(){
		mute = ! mute;
	}

	public void start() {
		istAktiv = true;
		letzterZustand = 0; // damit ist jeder echte Zustand anders
		lautstaerke = 0.0;
		try {
			lautstaerkeMesser.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		statistik[zustand].verlasseZustand(System.currentTimeMillis());
		lautstaerkeMesser.stop();
		istAktiv = false;
	}
}
