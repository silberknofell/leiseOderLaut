package de.geihe.leiseOderLaut;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class CountDownPrefs extends Activity 
     implements OnClickListener, OnTimeChangedListener, 
     			TextWatcher, OnCheckedChangeListener {

	public static final String ENDEZEIT = "endezeit";	
	public static final String EXTRAZEIT = "extrazeit";
	public static final String MODUS = "modus";
	public static final int MODUS_EINFACH =1;
	public static final int MODUS_FORTGESCHRITTEN =2;
	
	public final static int[] STUFEN_IN_MIN = { 
		60*1 + 0,
		60*2 + 0,
		60*3 + 0,
		60*4 + 0,
		60*5 + 0,
		60*6 + 0,
		60*7 + 0,
		60*8 + 0,
		60*8 +45,
		60*9 +30,
		60*10+30,
		60*11+15,
		60*12+20,
		60*13+10,
		60*14+ 0,
		60*14+45,
		60*15+30,		
		60*16+15,		
		60*17+00,		
		60*18+00,		
		60*19+00,		
		60*20+00,		
		60*21+00,		
		60*22+00,		
		60*23+00		
	} ;
	public final static int ANZAHL_STUFEN=STUFEN_IN_MIN.length;
	public static final int FARBE_NORMAL=0x00AAAAAA;
	public static final int FARBE_WARNUNG=0xAAFF0000;
	public static final int FARBE_TOGGLE_SIMPLE=0x88FFFF00;
	public static final int FARBE_TOGGLE_ADVANCED=0x880000FF;
	

	private Button endeButton_m;
	private Button endeButton_p;

	private TimePicker timePicker;
	private ToggleButton toggle;
	private TextView tvEnde;
	
	public void button_Cancel_click(View v) {
		setResult(RESULT_CANCELED);
		finish();
	}

	public void button_OK_click(View v) {
		Intent data=new Intent();
		int extrazeit=Math.max(leseExtraZeit(), 5);
		long endezeit=leseEndeZeit();
		if (einstellungErlaubt(extrazeit, endezeit)) {
		data.putExtra(EXTRAZEIT, extrazeit);
		data.putExtra(ENDEZEIT, endezeit);
		data.putExtra(MODUS, toggle.isChecked());
		setResult(RESULT_OK, data); 
		finish();		
		}
		else {
			button_Cancel_click(v);
		}

	}

	public long leseEndeZeit() {
		int h=timePicker.getCurrentHour();
		int m=timePicker.getCurrentMinute();
		Calendar ende=new GregorianCalendar();
		ende.set(Calendar.HOUR_OF_DAY, h);
		ende.set(Calendar.MINUTE, m);
		ende.set(Calendar.SECOND, 0);
		ende.set(Calendar.MILLISECOND, 0);
		
		return ende.getTimeInMillis();		
	}

	public int leseExtraZeit() {
//		String text=edExtra.getText().toString();
//		if (text.length() == 0) {
//			edExtra.setText("5");
			return 5;
//		}
//		return Integer.parseInt(text);
	}

	private boolean einstellungErlaubt(int extrazeitInMin, long endezeitInMS) {
		if (!toggle.isChecked()) {
			return true; //im einfachen Modus alle Zeiten erlaubt
		}
		long zeit=endezeitInMS-extrazeitInMin*60000;
		return (zeit>System.currentTimeMillis());
	}
	
	public int leseEndeZeitInMin() {
		int h=timePicker.getCurrentHour();
		int m=timePicker.getCurrentMinute();
		return 60*h+m;
	}
	

	
	
	@Override
	public void onClick(View v) {
		int extra;
		switch (v.getId()) {
			case R.id.endeButton_p:
				setzeNaechsteStufe(leseEndeZeitInMin());
				break;
			case R.id.endeButton_m:
				setzeLetzteStufe(leseEndeZeitInMin());
				break;

			default: break;
		}

	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_count_down_prefs);

		tvEnde = (TextView) findViewById(R.id.tvEnde);


		timePicker=(TimePicker) findViewById(R.id.timePicker);
		timePicker.setIs24HourView(true);	
		timePicker.setBackgroundColor(FARBE_NORMAL);
		setzeNaechsteStufe();
		timePicker.setOnTimeChangedListener(this);
			
		endeButton_p = (Button) findViewById(R.id.endeButton_p);
		endeButton_m = (Button) findViewById(R.id.endeButton_m);
		

		endeButton_p.setOnClickListener(this);
		endeButton_m.setOnClickListener(this);		
		
		toggle=(ToggleButton) findViewById(R.id.toggleButtonModus);
		toggle.setOnCheckedChangeListener(this);
		toggle.setChecked(true);	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.count_down_prefs, menu);
		return true;
	}
	
	
	public void setzeLetzteStufe(int minuten) {
		int stufe=0;
		while (STUFEN_IN_MIN[stufe] < minuten  &&  stufe<ANZAHL_STUFEN) {
			stufe++;
		}
		stufe--;
		if (stufe<0) {
			stufe=ANZAHL_STUFEN-1;

		}		
//		setzeTimePicker(stufe);
	}

	public void setzeNaechsteStufe(int minuten) {
		int stufe=0;
		while (STUFEN_IN_MIN[stufe] <= minuten  &&  stufe<ANZAHL_STUFEN) {
			stufe++;
		}
		if (stufe>=ANZAHL_STUFEN) {
			stufe=0;
		}
//		setzeTimePicker(stufe);
	}

	public void setzeNaechsteStufe() { //verwendet aktuelle Zeit
		Calendar calendar=new GregorianCalendar();
		int m=calendar.get(Calendar.MINUTE);
		int h=calendar.get(Calendar.HOUR_OF_DAY);
		setzeNaechsteStufe(60*h+m+leseExtraZeit());
	}


//	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		setViewBackground();		
	}

	
	
	private void setViewBackground(View view) {
		if (einstellungErlaubt(leseExtraZeit(), leseEndeZeit())) {
			view.setBackgroundColor(FARBE_NORMAL);
		}
		else {
			view.setBackgroundColor(FARBE_WARNUNG);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		setViewBackground();
	}

	private void setViewBackground() {
//		setViewBackground(edExtra);
		setViewBackground(timePicker);
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
		setViewBackground();
		if (isChecked) {
			toggle.setBackgroundColor(FARBE_TOGGLE_ADVANCED);
			timePicker.setVisibility(View.VISIBLE);
			endeButton_m.setVisibility(View.VISIBLE);
			endeButton_p.setVisibility(View.VISIBLE);
			tvEnde.setVisibility(View.VISIBLE);
		}
		else {
			toggle.setBackgroundColor(FARBE_TOGGLE_SIMPLE);			
			timePicker.setVisibility(View.INVISIBLE);
			endeButton_m.setVisibility(View.INVISIBLE);
			endeButton_p.setVisibility(View.INVISIBLE);
			tvEnde.setVisibility(View.INVISIBLE);
		}
		
	}
	
}
