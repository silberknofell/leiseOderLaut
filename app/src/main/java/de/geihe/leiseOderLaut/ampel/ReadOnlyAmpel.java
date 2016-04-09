package de.geihe.leiseOderLaut.ampel;

/**
 * Created by test on 03.10.2015.
 */
public abstract class ReadOnlyAmpel {
	public static final int ZUSTANDGELB = 2;
	public static final int ZUSTANDGRUEN = 1;
	public static final int ZUSTANDROT = 3;

	protected float p_grenzeGelb;
	protected float p_grenzeGruen;
	protected boolean istAktiv;
	protected double lautstaerke;
	protected int letzterZustand;
	protected ZustandsStatistik[] statistik = new ZustandsStatistik[4];
	protected int zustand;

	protected void aktualisiereZustand() {

		if (istAktiv) {
			long zeit = System.currentTimeMillis();
			if (lautstaerke < p_grenzeGruen) {
				zustand = ZUSTANDGRUEN;
			} else if (lautstaerke < p_grenzeGelb) {
				zustand = ZUSTANDGELB;
			} else {
				zustand = ZUSTANDROT;
			}
			if (letzterZustand == zustand) {
				statistik[zustand].bleibeInZustand(zeit);
			} else { // Zustand gewechselt

				statistik[zustand].betreteZustand(zeit);
				if (letzterZustand > 0) {
					statistik[letzterZustand].verlasseZustand(zeit);
				}
				letzterZustand = zustand;
			}
		}

	}

	public float getGrenzeGelb() {
		return p_grenzeGelb;
	}

	public float getGrenzeGruen() {
		return p_grenzeGruen;
	}

	public double getLautstaerke() {
		return lautstaerke;
	}

	public ZustandsStatistik[] getStatistik() {
		return statistik;
	}

	public int getZustand() {
		return zustand;
	}

	public boolean istAktiv() {
		return istAktiv;
	}

	public void setGrenzeGelb(float grenzeGelb) {
		if (p_grenzeGruen < grenzeGelb) {
			this.p_grenzeGelb = grenzeGelb;
			aktualisiereZustand();
		}
	}

	public void setGrenzeGruen(float grenzeGruen) {
		if (grenzeGruen < p_grenzeGelb) {
			this.p_grenzeGruen = grenzeGruen;
			aktualisiereZustand();
		}
	}

	public long getGruenzeit() {
		return statistik[Ampel.ZUSTANDGRUEN]
				.getGesamtZeitImZustand();
	}

	public long getGelbzeit() {
		return statistik[Ampel.ZUSTANDGELB]
				.getGesamtZeitImZustand();
	}

	public long getRotzeit() {
		return statistik[Ampel.ZUSTANDROT]
				.getGesamtZeitImZustand();
	}

	public long getLautzeit() {
		return getGelbzeit() + getRotzeit();
	}
}

