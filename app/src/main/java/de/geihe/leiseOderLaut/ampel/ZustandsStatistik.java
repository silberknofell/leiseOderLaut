package de.geihe.leiseOderLaut.ampel;

public class ZustandsStatistik {

	private int anzahlImZustand;
	private long gesamtZeitImZustand;
	private long aktuelleZeitImZustand;
	private long laengsteZeitImZustand;
	private long betretenZurZeit;
	public static final String LEER_STRING = "0;0;0;0;0";

	public ZustandsStatistik() {
		init();
	}

	public int getAnzahlImZustand() {
		return anzahlImZustand;
	}

	public long getGesamtZeitImZustand() { //in Millisekunden
		return gesamtZeitImZustand + aktuelleZeitImZustand;
	}

	public long getAktuelleZeitImZustand() { //in Millisekunden
		return aktuelleZeitImZustand;
	}

	public long getLaengsteZeitImZustand() { //in Millisekunden
		return laengsteZeitImZustand;
	}

	public boolean imZustand() {
		return (betretenZurZeit >= 0);
	}

	public void init() {
		anzahlImZustand = 0;
		gesamtZeitImZustand = 0;
		aktuelleZeitImZustand = 0;
		laengsteZeitImZustand = 0;
		betretenZurZeit = -1; // negativ - Zustand nicht aktiv		
	}
	
	public void betreteZustand(long zeit) {
		if (! imZustand()) {
			anzahlImZustand++;
			betretenZurZeit = zeit;
			aktuelleZeitImZustand = 0;
		} else {	// Fehler, Zustand schon aktiv
			bleibeInZustand(zeit);
		}
	}

	public void bleibeInZustand(long zeit) {
		if (imZustand()) {
			aktuelleZeitImZustand = zeit - betretenZurZeit;
			if (aktuelleZeitImZustand > laengsteZeitImZustand) {
				laengsteZeitImZustand = aktuelleZeitImZustand;
			}
		} else {
			// Fehler, Zustand wird betreten
			betreteZustand(zeit);
		}

	}

	public void verlasseZustand(long zeit) {
		if (imZustand()) {
			bleibeInZustand(zeit);
			gesamtZeitImZustand += aktuelleZeitImZustand;
			aktuelleZeitImZustand = 0;
			betretenZurZeit = -1;
		} else {
			// Fehler, Zustand gar nicht aktiv, tue nichts
		}
	}
	@Override
	public String toString() {
		return Integer.toString(anzahlImZustand)
				+";" + Long.toString(gesamtZeitImZustand)
				+";" + Long.toString(aktuelleZeitImZustand)
				+";" + Long.toString(laengsteZeitImZustand)
				+";" + Long.toString(betretenZurZeit);
	}

	public void readFromString(String string) {
		String[] values = string.split(";");
		if (values.length == 5) {
			anzahlImZustand = Integer.parseInt(values[0]);
			gesamtZeitImZustand = Long.parseLong(values[1]);
			aktuelleZeitImZustand = Long.parseLong(values[2]);
			laengsteZeitImZustand = Long.parseLong(values[3]);
			betretenZurZeit = Long.parseLong(values[4]);
		}

	}
}
