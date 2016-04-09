package de.geihe.leiseOderLaut;

import android.os.Handler;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

public class Blink {

	private Handler handler;
	private int[] farbe = new int[2];
	private int farbIndex = 1;
	private int ticks;
	private final int MAXTICKS = 40;
	private final int DELAYMILLIS = 100;
	private View v;
	private Drawable backgroundColor;

	private Runnable blinkSchleife = new Runnable() {
		@Override
		public void run() {
			v.setBackgroundColor(farbe[farbIndex]);
			farbIndex = 1 - farbIndex;
			ticks++;
			if (ticks < MAXTICKS) {
				handler.postDelayed(blinkSchleife, DELAYMILLIS);
			} 
			else {
				v.setBackgroundColor(Color.TRANSPARENT);
			}
		}
	};
	
	public  Blink(View v) {
		this.v=v;
		backgroundColor=v.getBackground();
		ticks = 0;
		farbIndex=0;
		farbe[0] = Color.RED;
		farbe[1] = Color.TRANSPARENT;
		handler = new Handler();
		handler.post(blinkSchleife);
	}

}
