package application;

import java.util.Date;

public class ModelTable {
private int id;
private String ime,prezime,smjestajnaJedinica;
private Date datumDolaska, datumOdlaska;
private double cijena,ukupnoZaduzenje;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getIme() {
	return ime;
}
public void setIme(String ime) {
	this.ime = ime;
}
public String getPrezime() {
	return prezime;
}
public void setPrezime(String prezime) {
	this.prezime = prezime;
}
public String getSmjestajnaJedinica() {
	return smjestajnaJedinica;
}
public void setSmjestajnaJedinica(String smjestajnaJedinica) {
	this.smjestajnaJedinica = smjestajnaJedinica;
}
public Date getDatumDolaska() {
	return datumDolaska;
}
public void setDatumDolaska(Date datumDolaska) {
	this.datumDolaska = datumDolaska;
}
public Date getDatumOdlaska() {
	return datumOdlaska;
}
public void setDatumOdlaska(Date datumOdlaska) {
	this.datumOdlaska = datumOdlaska;
}
public double getCijena() {
	return cijena;
}
public void setCijena(double cijena) {
	this.cijena = cijena;
}
public double getUkupnoZaduzenje() {
	return ukupnoZaduzenje;
}
public void setUkupnoZaduzenje(double ukupnoZaduzenje) {
	this.ukupnoZaduzenje = ukupnoZaduzenje;
}
public ModelTable(int id, String ime, String prezime,Date datumDolaska, Date datumOdlaska,String smjestajnaJedinica,
		double cijena, double ukupnoZaduzenje) {
	super();
	this.id = id;
	this.ime = ime;
	this.prezime = prezime;
	this.smjestajnaJedinica = smjestajnaJedinica;
	this.datumDolaska = datumDolaska;
	this.datumOdlaska = datumOdlaska;
	this.cijena = cijena;
	this.ukupnoZaduzenje = ukupnoZaduzenje;
}

}
