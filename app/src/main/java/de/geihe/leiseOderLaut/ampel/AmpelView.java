package de.geihe.leiseOderLaut.ampel;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import de.geihe.leiseOderLaut.preferences.PrefHelper;
import de.geihe.leiseOderLaut.timeView.TimeView;

public class AmpelView extends View {
	public static final float SKALATEXTGROESSE = 20f;
	public static final float ZEITTEXTGROESSE = 150f;

	private ReadOnlyAmpel ampel = null;
	private Paint backgroundPaint = new Paint();
	private int breiteView;
	int clGelb = Color.YELLOW;
	int clGruen = Color.GREEN;
	int clRot = Color.RED;

	private int hoeheView;

	private Paint linePaint = new Paint();

	private static final double LINIENBREITE = 0.005;
	private boolean modGrenzenVerschieben;
	private int p_padding_left_right;

	private boolean p_skalaAnzeigen;
	private int padding_top;
	private Paint paint = new Paint();
	private Paint skalaPaint = new Paint();
	int textFarbe = Color.GRAY;
	private Paint textPaint = new Paint();

	private Paint zeitPaint = new Paint();
	int zeitPaintHoehe;

	List<TimeView> timeViewList;

	public AmpelView(final Context context, AttributeSet attrs) {
		super(context, attrs);
		timeViewList = new ArrayList<TimeView>();
		setPaints();

		modGrenzenVerschieben = false;

		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if ((event.getY() > padding_top) && (modGrenzenVerschieben)) {
					float y = 1 - (event.getY() - padding_top)
							/ (hoeheView - padding_top);
					y = Math.round(y * 20) / 20f; // auf 5%-Schritte normieren
					if (Math.abs(y - ampel.getGrenzeGelb()) > Math.abs(y
							- ampel.getGrenzeGruen())) {
						ampel.setGrenzeGruen(y);
					} else {
						ampel.setGrenzeGelb(y);
					}
					int mitte = breiteView >> 1;
					p_padding_left_right = mitte
							- Math.abs((int) event.getX() - mitte);
					invalidate();
				}

				if (event.getAction() == MotionEvent.ACTION_UP) {
					modGrenzenVerschieben = false;
//					return true;
				}

				return false;
			}
		});

		setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				modGrenzenVerschieben = true;
				return true;
			}
		});
	}

	@Override
	public void onDraw(Canvas canvas) {
		// Maße
		hoeheView = getHeight();
		breiteView = getWidth();

//		canvas.drawPaint(backgroundPaint);
		double lautstaerkeAmpel = ampel.getLautstaerke();
		double grenzeGruen = ampel.getGrenzeGruen();
		double grenzeGelb = ampel.getGrenzeGelb();
		switch (ampel.getZustand()) {

		// Balken zeichnen
		case Ampel.ZUSTANDGRUEN: {
			paint.setColor(clGruen);
			zeichneRundenBalken(0.0, lautstaerkeAmpel, canvas, paint);
			break;
		}
		case Ampel.ZUSTANDGELB: {
			paint.setColor(clGruen);
			zeichneRundenBalken(0.0, grenzeGruen, canvas, paint);
			paint.setColor(clGelb);
			zeichneRundenBalken(grenzeGruen, lautstaerkeAmpel, canvas, paint);
			break;
		}
		case Ampel.ZUSTANDROT: {
			paint.setColor(clGruen);
			zeichneRundenBalken(0.0, grenzeGruen, canvas, paint);
			paint.setColor(clGelb);
			zeichneRundenBalken(grenzeGruen, grenzeGelb, canvas, paint);
			paint.setColor(clRot);
			zeichneRundenBalken(grenzeGelb, lautstaerkeAmpel, canvas, paint);
		}
		} // ende switch

		// Grenzen zeichnen
		linePaint.setColor(Color.GRAY);
		zeichneRundenBalken(grenzeGruen - LINIENBREITE, grenzeGruen
				+ LINIENBREITE, canvas, linePaint);
		textPaint.setAntiAlias(true);
		textPaint.setColor(textFarbe);
		textPaint.setTextSize(20f);
		canvas.drawText(text(grenzeGruen), 1f,
				y(grenzeGruen + LINIENBREITE * 2), textPaint);

		linePaint.setColor(Color.LTGRAY);
		zeichneRundenBalken(grenzeGelb - LINIENBREITE, grenzeGelb
				+ LINIENBREITE, canvas, linePaint);
		canvas.drawText(text(grenzeGelb), 1f, y(grenzeGelb + LINIENBREITE * 2),
				textPaint);

		// Skala zeichnen
		if (p_skalaAnzeigen) {
			for (double y = 0.; y <= 1.0; y += 0.2) {
				if (y <= grenzeGruen) {
					skalaPaint.setColor(clGruen);
				} else if (y <= grenzeGelb) {
					skalaPaint.setColor(clGelb);
				} else {
					skalaPaint.setColor(clRot);
				}

				int b = y(y);
				// Log.d("y", Integer.toString(b));

				canvas.drawText(text(y), breiteView, b - 5, skalaPaint);
				canvas.drawLine((float) (breiteView * 0.85), b, breiteView, b,
						skalaPaint);
			}
		}
	}

	@Override
	protected void onSizeChanged(int height, int width, int height_old,
			int width_old) {
		this.hoeheView = height;
		this.breiteView = width;
		// padding
		padding_top = Math.round(SKALATEXTGROESSE);
		readPrefs();
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		if (visibility != View.VISIBLE) {
			Editor editor = PrefHelper.getEditor();
			editor.putInt("padding_left_right", p_padding_left_right);
			editor.apply();
		}
	}

	public void readPrefs() {
		p_skalaAnzeigen = PrefHelper.getIsSkalaVisible();
		p_padding_left_right = PrefHelper.getPaddingLeftRight(getWidth() / 4);

	}

	public void setAmpel(ReadOnlyAmpel ampel) {
		this.ampel = ampel;
	}

	@Override
	public void setBackgroundColor(int farbe) {
		backgroundPaint.setColor(farbe);
		invalidate();
	}

	private void setPaints() {
		paint.setAntiAlias(true);
		skalaPaint.setAntiAlias(true);
		textPaint.setAntiAlias(true);
		linePaint.setAntiAlias(true);
		skalaPaint.setTextAlign(Paint.Align.RIGHT);
		skalaPaint.setAntiAlias(true);
		skalaPaint.setTextSize(SKALATEXTGROESSE);
		backgroundPaint.setColor(Color.BLACK);
		zeitPaint.setAntiAlias(true);
		zeitPaint.setColor(Color.WHITE);
		zeitPaint.setTextSize(ZEITTEXTGROESSE);
		zeitPaint.setTextAlign(Paint.Align.CENTER);
		Rect bounds = new Rect();
		zeitPaint.getTextBounds("0", 0, 1, bounds);
		zeitPaintHoehe = bounds.height();
	}

	private String text(double doubleZahl) {
		return Long.toString(Math.round(doubleZahl * 100)) + "%";
	}

	private int y(double wert) {
		return (int) Math.round((1 - wert) * (hoeheView - padding_top)
				+ padding_top) - 1;
	}

	private void zeichneBalken(double von, double bis, Canvas canvas,
			Paint paint) {
		canvas.drawRect(p_padding_left_right, y(bis), breiteView
				- p_padding_left_right, y(von), paint);
	}

	private void zeichneRundenBalken(double von, double bis, Canvas canvas,
			Paint paint) {
		RectF rect = new RectF(p_padding_left_right, y(bis), breiteView
				- p_padding_left_right, y(von));
		canvas.drawRoundRect(rect, 10, 20, paint);
	}

	public void update() {
		this.invalidate();
	}
}
