package de.geihe.leiseOderLaut;

import java.io.IOException;

import android.media.MediaRecorder;
import android.util.Log;

public class Lautstaerkemesser {
	private boolean stumm=false;
	private MediaRecorder mr = null;
	private double p_EMAFaktor=0.5;
	private double ema=0.0;
	private final double MAXAMPLITUDE=32768.0;

	public void start() throws IllegalStateException, IOException {
		if (mr == null) {
			mr = new MediaRecorder();
			mr.setAudioSource(MediaRecorder.AudioSource.MIC);
			mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mr.setOutputFile("/dev/null");
			mr.prepare();
			mr.start();
			ema=0.0;
		}
	}

	public void stop() {
		if (mr != null) {
			mr.stop();
			mr.reset();
			mr.release();
			mr=null;
		}
	}

	private double getAmplitude() {
		if ((mr != null) && (stumm==false))
			return mr.getMaxAmplitude();
		else
			return 0;
	}

	public double getAmplitudeEMA(){
		ema=p_EMAFaktor*getAmplitude() + (1.0-p_EMAFaktor)*ema;
		return ema/MAXAMPLITUDE;
	}
	
	public double getSqrtAmplitudeEMA() {		
		return Math.sqrt(getAmplitudeEMA());
	}

	public void setStumm() {
		stumm=true;
	}

	public void setAktiv() {
		stumm = false;
	}

	public void toggleStumm() {
		stumm = ! stumm;
	}
}
