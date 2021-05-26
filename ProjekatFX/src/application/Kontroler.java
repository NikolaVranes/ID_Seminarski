package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Kontroler implements Initializable {
	private double xOffsett = 0;
	private double yOffsett = 0;
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Pane okvir;

	@FXML
	private Button btnNovaRezervacija;

	@FXML
	private Button btnPodaci;

	@FXML 
	private Button btnObrisi;

	@FXML
	private Button btnZatvori;

	@FXML
	private DatePicker datePickerDatumDolaska;

	@FXML
	private DatePicker datePickerDatumOdlaska;

	@FXML
	private ComboBox<String> comboBoxSmjestajnaJedinica;

	@FXML
	private TextField txtFieldIme;

	@FXML
	private TextField txtFieldPrezime;

	@FXML
	private TextField txtFieldAdresa;

	@FXML
	private TextField txtFieldBrTelefona;

	@FXML
	private TextField txtFieldBrojNocenja;

	@FXML
	private TextField txtFieldPodatakID;

	@FXML
	private TextField txtFieldCijena;

	@FXML
	private TextField txtFieldAvans;

	@FXML
	private TextField txtFieldUkupnoZaduzenje;

	@FXML
	private TextArea txtAreaZakljucak;


	public void otvoriTabelu(ActionEvent event)  {
		try{
			root = FXMLLoader.load(getClass().getResource("tabela.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("tabela1.css").toExternalForm());
			root.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					xOffsett = event.getSceneX();
					yOffsett = event.getSceneY();
				}
			});
			root.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					stage.setX(event.getScreenX() - xOffsett);
					stage.setY(event.getScreenY() - yOffsett);
				}
			});   

			stage.setScene(scene);		
			stage.show();
		} catch (Exception e) {
			Alert greska = new Alert(AlertType.ERROR);
			greska.setTitle("Greska!");
			greska.setHeaderText("Konekcija sa bazom nije uspjela");
			greska.showAndWait();
		}
	}
	@FXML
	public void handleCloseButtonAction(ActionEvent event) {
		Stage stage = (Stage) btnZatvori.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> sveJedinice = FXCollections.observableArrayList();
		for(int i = 1; i<=10;i++) {
			sveJedinice.add("Apartman " + i);
		}
		comboBoxSmjestajnaJedinica.setItems(sveJedinice);
		btnNovaRezervacija.setOnMouseEntered(e -> btnNovaRezervacija.setStyle("-fx-background-color: #48188C;"));
		btnNovaRezervacija.setOnMouseExited(e -> btnNovaRezervacija.setStyle("-fx-background-color: #5F30A1;"));
		btnPodaci.setOnMouseEntered(e -> btnPodaci.setStyle("-fx-background-color: #48188C;"));
		btnPodaci.setOnMouseExited(e -> btnPodaci.setStyle("-fx-background-color: #5F30A1;"));
		btnObrisi.setOnMouseEntered(e -> btnObrisi.setStyle("-fx-background-color: #48188C;"));
		btnObrisi.setOnMouseExited(e -> btnObrisi.setStyle("-fx-background-color: #5F30A1;"));
		btnZatvori.setOnMouseEntered(e -> btnZatvori.setStyle("-fx-background-color: #48188C;"));
		btnZatvori.setOnMouseExited(e -> btnZatvori.setStyle("-fx-background-color: #5F30A1;"));

	}


	public void reset() {
		txtAreaZakljucak.setText("");
		txtFieldIme.setText("");
		txtFieldPrezime.setText("");
		txtFieldAdresa.setText("");
		txtFieldPodatakID.setText("");
		txtFieldCijena.setText("");
		txtFieldBrojNocenja.setText("");
		txtFieldUkupnoZaduzenje.setText("");
		txtFieldAvans.setText("");
		comboBoxSmjestajnaJedinica.setValue(null);
		datePickerDatumDolaska.setValue(null);
		datePickerDatumOdlaska.setValue(null);
		txtFieldBrTelefona.setText("");

	}
	private String validacijaForme() {
		if(txtFieldIme.getText().length() == 0) return "Morate unijeti Ime";
		if(txtFieldPrezime.getText().length() == 0) return "Morate unijeti Prezime";
		if(comboBoxSmjestajnaJedinica.getValue()== null) return "Morate izabrati smjestajnu jedinicu";
		if(txtFieldCijena.getText().length() == 0) return "Morate unijeti cijenu";
		if(datePickerDatumDolaska.getValue()== null) return "Morate unijeti datum dolaska";
		if(datePickerDatumOdlaska.getValue() == null) return "Morate unijeti datum odlaska";
		if(txtFieldPodatakID.getText().length() == 0) return "Morate unijeti neki ID podatak";
		if(txtFieldAdresa.getText().length() == 0) return "Morate unijeti adresu gosta";
		if(txtFieldBrTelefona.getText().length() == 0) return "Morate unijeti broj telefona gosta";
		try {
			double br = Double.parseDouble(txtFieldCijena.getText());
			if(br < 0) {
				return "Cijena mora biti pozitivan broj";
			}
		} catch (NumberFormatException e) {
			return "Cijena mora biti broj";

		}
		return null;
	}
	public void novaRez() {
		String validacija = validacijaForme();
		if(validacija!=null) {	
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Pogresan unos");
			alert.setHeaderText(validacija);
			alert.showAndWait();
			return;
		}
		String ime = txtFieldIme.getText();
		String prezime = txtFieldPrezime.getText();
		String adresa = txtFieldAdresa.getText();
		String podatakID = txtFieldPodatakID.getText();
		double cijena = Double.parseDouble(txtFieldCijena.getText());
		LocalDate datDol = datePickerDatumDolaska.getValue();
		LocalDate datOdl = datePickerDatumOdlaska.getValue();
		String brTelefona = txtFieldBrTelefona.getText();
		Gost g = new Gost(ime, prezime, podatakID, adresa, brTelefona);
		SmjestajnaJedinica smjJed = new SmjestajnaJedinica(comboBoxSmjestajnaJedinica.getValue().toString(),cijena);
		Rezervacija rez = new Rezervacija(g, smjJed, datDol, datOdl);

		txtFieldBrojNocenja.setText(String.valueOf(rez.getBrojNocenja()));
		txtFieldAvans.setText(String.valueOf(rez.getAvans()));
		txtFieldUkupnoZaduzenje.setText(String.valueOf(rez.getUkupnaCijena()));
		try {
			upisiUBazu(rez);
		} catch (SQLException e) {
			e.printStackTrace();
			Alert greska = new Alert(AlertType.ERROR);
			greska.setTitle("Doslo je do greske");
			greska.setHeaderText("Rezervacija nije upisana u bazu!");
			greska.showAndWait();
		}

	}
	public void upisiUBazu(Rezervacija rez) throws SQLException {
		Connection konekcija = BazaPodataka.getConnection();


		String upitDatuma = "SELECT *  FROM rezervacije WHERE\r\n"
				+ "Smjestajna_Jedinica = ? and(\r\n"
				+ "( ? > Datum_Dolaska AND ? < Datum_Odlaska) \r\n"
				+ "or ( ?  > Datum_Dolaska AND ? < Datum_Odlaska)\r\n"
				+ "or ( ? <= Datum_Dolaska And ? >= Datum_Odlaska))\r\n";
		PreparedStatement stmt = konekcija.prepareStatement(upitDatuma);
		stmt.setString(1, rez.getSmjestajnaJedinica().getIme());
		stmt.setDate(2, Date.valueOf(rez.getDatumDolaska()));
		stmt.setDate(3, Date.valueOf(rez.getDatumDolaska()));
		stmt.setDate(4, Date.valueOf(rez.getDatumOdlaska()));
		stmt.setDate(5, Date.valueOf(rez.getDatumOdlaska()));
		stmt.setDate(6, Date.valueOf(rez.getDatumDolaska()));
		stmt.setDate(7, Date.valueOf(rez.getDatumOdlaska()));
		ResultSet rs = stmt.executeQuery(); 
		boolean provjera = rs.next();

		if (provjera == false) 
		{
			txtAreaZakljucak.setText(rez.toString());
			String query = " insert into rezervacije (Ime, Prezime, Adresa, Br_telefona, Podatak_ID, Datum_Dolaska, Datum_Odlaska, Smjestajna_Jedinica, Broj_Nocenja, Cijena_Nocenja, Avans, Ukupno_Zaduzenje)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = konekcija.prepareStatement(query);
			preparedStmt.setString (1, rez.getGost().getIme());
			preparedStmt.setString (2, rez.getGost().getPrezime());
			preparedStmt.setString  (3, rez.getGost().getadresa());
			preparedStmt.setString (4, rez.getGost().getBrTelefona());
			preparedStmt.setString (5, rez.getGost().getpodatakID());
			preparedStmt.setDate(6, Date.valueOf(rez.getDatumDolaska()));
			preparedStmt.setDate(7, Date.valueOf(rez.getDatumOdlaska()));
			preparedStmt.setString (8, rez.getSmjestajnaJedinica().getIme());
			preparedStmt.setInt(9, rez.getBrojNocenja());
			preparedStmt.setDouble(10, rez.getSmjestajnaJedinica().getCijena());
			preparedStmt.setDouble(11, rez.getAvans());
			preparedStmt.setDouble(12, rez.getUkupnaCijena());
			preparedStmt.execute();
			Alert potvrda = new Alert(AlertType.CONFIRMATION);
			potvrda.setTitle("Potvrda");
			potvrda.setHeaderText("Uspjesno ste izvrsili rezervaciju.");
			potvrda.setContentText("Podaci su upisani u bazu");
			potvrda.showAndWait();
			reset();
			konekcija.close();
		}
		while(provjera) {
			Alert greska = new Alert(AlertType.WARNING);
			greska.setTitle("Upozorenje");
			greska.setHeaderText("Smjestajna jedinica: " + rez.getSmjestajnaJedinica() + " nije slobodna u datom periodu! \n Rezervacija nije izvrsena!");
			greska.showAndWait();
			break;
		}
	}

}
