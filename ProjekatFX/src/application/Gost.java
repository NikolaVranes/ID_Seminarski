package application;

public class Gost {
	private String ime, prezime, podatakID, adresa, brTelefona;

	public Gost(String ime, String prezime) {
		this.ime = ime;
		this.prezime = prezime;
		
	}
	public Gost(String ime, String prezime, String podatakID, String adresa,String brTelefona) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.podatakID = podatakID;
		this.adresa = adresa;
		this.brTelefona = brTelefona;
	}
	public String getBrTelefona() {
		return brTelefona;
	}
	public void setBrTelefona(String brTelefona) {
		this.brTelefona = brTelefona;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getpodatakID() {
		return podatakID;
	}

	public void setpodatakID(String podatakID) {
		this.podatakID = podatakID;
	}

	public String getadresa() {
		return adresa;
	}

	public void setadresa(String adresa) {
		this.adresa = adresa;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ime + " " + prezime;
	}


	}

