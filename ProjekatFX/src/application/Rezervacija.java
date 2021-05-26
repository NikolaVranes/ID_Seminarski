package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;


public class Rezervacija {
		private Gost gost;
		private SmjestajnaJedinica smjestajnaJedinica;
		private double ukupnaCijena, avans;
		private int brojNocenja;
		private LocalDate datumDolaska, datumOdlaska;

		public Rezervacija(Gost gost, SmjestajnaJedinica smjestajnaJedinica,LocalDate datumDolaska, LocalDate datumOdlaska, int brojNocenja, double ukupnaCijena) {
			super();
			this.gost = gost;
			this.smjestajnaJedinica = smjestajnaJedinica;
			this.datumDolaska = datumDolaska;
			this.datumOdlaska = datumOdlaska;
			this.brojNocenja = brojNocenja;
			this.ukupnaCijena = ukupnaCijena;
		}

		public Rezervacija(Gost gost, SmjestajnaJedinica smjestajnaJedinica, LocalDate datumDolaska, LocalDate datumOdlaska) {
			super();
			this.gost = gost;
			this.smjestajnaJedinica = smjestajnaJedinica;
			this.datumDolaska = datumDolaska;
			this.datumOdlaska = datumOdlaska;
			this.brojNocenja = brojNocenja();
			this.ukupnaCijena = (double)brojNocenja() * smjestajnaJedinica.getCijena();
			this.avans = this.ukupnaCijena * 0.3;	
		}
		
		public static int brojNocenja1(LocalDate d1, LocalDate d2) {
			long razlika = ChronoUnit.DAYS.between(d1, d2);
			return (int)razlika;
		}
		private int brojNocenja() {
			LocalDate datum1 = this.datumDolaska;
			LocalDate datum2 = this.datumOdlaska;
			long razlika = ChronoUnit.DAYS.between(datum1, datum2);
			return (int)razlika;
		}
		public Gost getGost() {
			return gost;
		}
		public void setGost(Gost gost) {
			this.gost = gost;
		}
		public SmjestajnaJedinica getSmjestajnaJedinica() {
			return smjestajnaJedinica;
		}
		public void setSmjestajnaJedinica(SmjestajnaJedinica smjestajnaJedinica) {
			this.smjestajnaJedinica = smjestajnaJedinica;
		}
		public double getUkupnaCijena() {
			return ukupnaCijena;
		}
		public void setUkupnaCijena(double ukupnaCijena) {
			this.ukupnaCijena = ukupnaCijena;
		}
		public int getBrojNocenja() {
			return brojNocenja;
		}
		public void setBrojNocenja(int brojNocenja) {
			this.brojNocenja = brojNocenja;
		}
		public LocalDate getDatumDolaska() {
			return this.datumDolaska;
		}

		public void setDatumDolaska(LocalDate datumDolaska) {
			this.datumDolaska = datumDolaska;
		}
		public LocalDate getDatumOdlaska() {
			return this.datumOdlaska;
		}
		public void setDatumOdlaska(LocalDate datumOdlaska) {
			this.datumOdlaska = datumOdlaska;
		}
		public double getAvans() {
			return 0.3 * ukupnaCijena;
		}
		public void setAvans(double ukupnaCijena) {
			this.avans = ukupnaCijena * 0.3;
		}

		@Override
		public String toString() {
			return "Uspjesno ste izvrsili rezervaciju na ime " + this.gost.getIme() + " " + this.gost.getPrezime() + "\n" + "Podaci o gostu:--------------------------- \n " +
					"Adresa: " + this.gost.getadresa() + " Broj telefona: " + this.gost.getBrTelefona() + " ID Podatak: " + this.gost.getpodatakID() + "\n Rezervisao je smjestajnu jedinicu " + this.smjestajnaJedinica.getIme() + " u periodu od: \n"
					+ this.getDatumDolaska().toString() + " - " + this.getDatumOdlaska().toString() + "\n Broj nocenja: " + this.brojNocenja + "\n Cijena nocenja: " + this.smjestajnaJedinica.getCijena() + "\n Ukupno zaduzenje: " + this.ukupnaCijena;
		}
	}

