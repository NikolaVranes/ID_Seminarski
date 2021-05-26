package application;

public class SmjestajnaJedinica {
	private String ime;
	private double cijena;
	
	public SmjestajnaJedinica(String ime) {
		super();
		this.ime = ime;
	}

	public SmjestajnaJedinica(String ime, double cijena) {
		super();
		this.ime = ime;
		this.cijena = cijena;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public double getCijena() {
		return cijena;
	}
	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
	@Override
	public String toString() {
		return this.ime;
	}
	
}
