package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;
import java.sql.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Kontroler2 implements Initializable{
	private double xOffsett = 0;
	private double yOffsett = 0;
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	private Button btnAzuriraj;
	@FXML
	private Button btnNovaRezervacija;
	@FXML
	private Button btnPodaci;
	@FXML
	private Button btnObrisi;
	@FXML
	private Button btnUskladi;
	@FXML
	private Button btnZatvori;
	@FXML
	private TextField txtFieldPodatakID;

	@FXML
	private TextField txtFieldBrTelefona;

	@FXML
	private TextField txtFieldIme;

	@FXML
	private TextField txtFieldPrezime;

	@FXML
	private TextField txtFieldAdresa;

	@FXML
	private TextField txtFieldBrojNocenja;

	@FXML
	private TextField txtFieldCijena;

	@FXML
	private TextField txtFieldAvans;
	@FXML
	private TextField txtFieldPretrazi;

	@FXML
	private TextField txtFieldUkupnoZaduzenje;

	@FXML
	private DatePicker datePickerDatumDolaska;

	@FXML
	private DatePicker datePickerDatumOdlaska;

	@FXML
	private ComboBox<String> comboBoxSmjestajnaJedinica;
	@FXML
	private TableView<ModelTable> tabela;
	@FXML
	private TableColumn<ModelTable, Integer> kolonaID;
	@FXML
	private TableColumn<ModelTable, String> kolonaIme;
	@FXML
	private TableColumn<ModelTable, String> kolonaPrezime;
	@FXML
	private TableColumn<ModelTable, Date> kolonaDatumDolaska;
	@FXML
	private TableColumn<ModelTable, Date> kolonaDatumOdlaska;
	@FXML
	private TableColumn<ModelTable, String> kolonaSmjestajnaJedinica;
	@FXML
	private TableColumn<ModelTable, Double> kolonaCijena;
	@FXML
	private TableColumn<ModelTable, Double> kolonaUkupnoZaduzenje;

	ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
	@FXML
	public void handleCloseButtonAction(ActionEvent event) {
		Stage stage = (Stage) btnZatvori.getScene().getWindow();
		stage.close();
	}

	public void otvoriPocetnu(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("pocetniFrame.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
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
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> sveJedinice = FXCollections.observableArrayList();
		for(int i = 1; i<=10;i++) {
			sveJedinice.add("Apartman " + i);
		}
		comboBoxSmjestajnaJedinica.setItems(sveJedinice);
		btnAzuriraj.setOnMouseEntered(e -> btnAzuriraj.setStyle("-fx-background-color: #5F30A1;"));
		btnAzuriraj.setOnMouseExited(e -> btnAzuriraj.setStyle("-fx-background-color: #48188C;"));
		btnUskladi.setOnMouseEntered(e -> btnUskladi.setStyle("-fx-background-color: #5F30A1;"));
		btnUskladi.setOnMouseExited(e -> btnUskladi.setStyle("-fx-background-color: #48188C;"));
		btnNovaRezervacija.setOnMouseEntered(e -> btnNovaRezervacija.setStyle("-fx-background-color: #48188C;"));
		btnNovaRezervacija.setOnMouseExited(e -> btnNovaRezervacija.setStyle("-fx-background-color: #5F30A1;"));
		btnPodaci.setOnMouseEntered(e -> btnPodaci.setStyle("-fx-background-color: #48188C;"));
		btnPodaci.setOnMouseExited(e -> btnPodaci.setStyle("-fx-background-color: #5F30A1;"));
		btnObrisi.setOnMouseEntered(e -> btnObrisi.setStyle("-fx-background-color: #48188C;"));
		btnObrisi.setOnMouseExited(e -> btnObrisi.setStyle("-fx-background-color: #5F30A1;"));
		btnZatvori.setOnMouseEntered(e -> btnZatvori.setStyle("-fx-background-color: #48188C;"));
		btnZatvori.setOnMouseExited(e -> btnZatvori.setStyle("-fx-background-color: #5F30A1;"));


		try {
			Connection konekcija = BazaPodataka.getConnection();

			ResultSet rs = konekcija.createStatement().executeQuery("SELECT ID,Ime,Prezime,Datum_Dolaska,Datum_Odlaska,Smjestajna_Jedinica,Cijena_Nocenja,Ukupno_Zaduzenje FROM rezervacije");
			while(rs.next()) {
				oblist.add(new ModelTable(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getDate(5),rs.getString(6),rs.getDouble(7),rs.getDouble(8)));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		kolonaID.setCellValueFactory(new PropertyValueFactory<>("id"));
		kolonaIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
		kolonaPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
		kolonaDatumDolaska.setCellValueFactory(new PropertyValueFactory<>("datumDolaska"));
		kolonaDatumOdlaska.setCellValueFactory(new PropertyValueFactory<>("datumOdlaska"));
		kolonaSmjestajnaJedinica.setCellValueFactory(new PropertyValueFactory<>("smjestajnaJedinica"));
		kolonaCijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
		kolonaUkupnoZaduzenje.setCellValueFactory(new PropertyValueFactory<>("ukupnoZaduzenje"));

		tabela.setItems(oblist);	
		FilteredList<ModelTable> filteredData = new FilteredList<>(oblist, b -> true);
		txtFieldPretrazi.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(ModelTable -> {									
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();				
				if (ModelTable.getIme().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; 
				}
				else if(ModelTable.getSmjestajnaJedinica().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				else if(ModelTable.getPrezime().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				}
				else  {
					return false; 
				}
			});
		});
		SortedList<ModelTable> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tabela.comparatorProperty());
		tabela.setItems(sortedData);			
	}

	@FXML
	private void displaySelected(MouseEvent event) throws SQLException {
		ModelTable m = tabela.getSelectionModel().getSelectedItem();
		int id = m.getId();
		String query = " select * from rezervacije where ID = " + String.valueOf(id);
		ResultSet rs = BazaPodataka.getConnection().createStatement().executeQuery(query);
		while(rs.next()) {
			txtFieldIme.setText(rs.getString(2));
			txtFieldPrezime.setText(rs.getString(3));
			txtFieldAdresa.setText(rs.getString(4));
			txtFieldBrTelefona.setText(rs.getString(5));
			txtFieldPodatakID.setText(rs.getString(6));
			datePickerDatumDolaska.setValue(rs.getDate(7).toLocalDate());
			datePickerDatumOdlaska.setValue(rs.getDate(8).toLocalDate());
			comboBoxSmjestajnaJedinica.setValue(rs.getString(9));
			txtFieldBrojNocenja.setText(String.valueOf(rs.getInt(10)));
			txtFieldCijena.setText(String.valueOf(rs.getDouble(11)));
			txtFieldAvans.setText(String.valueOf(rs.getDouble(12)));
			txtFieldUkupnoZaduzenje.setText(String.valueOf(rs.getDouble(13)));
		}
	}
	@FXML
	private void eraseSelected() throws SQLException  {
		ModelTable m = tabela.getSelectionModel().getSelectedItem();
		int id = m.getId();
		Alert pitanje = new Alert(AlertType.CONFIRMATION);
		pitanje.setHeaderText("Da li zelite da izbrisete rezervaciju na ime " + m.getIme() + " " + m.getPrezime());
		pitanje.showAndWait();
		if (pitanje.getResult() == ButtonType.OK){
			String query = " delete from rezervacije where ID = " + String.valueOf(id);
			PreparedStatement preparedStmt;
			preparedStmt = BazaPodataka.getConnection().prepareStatement(query);
			preparedStmt.execute();
			oblist.remove(m);
		}
	}

	@FXML
	private void azuriraj() {
		Connection konekcija = BazaPodataka.getConnection();
		ModelTable m = tabela.getSelectionModel().getSelectedItem();
		int id = m.getId();

		String upitDatuma = "SELECT *  FROM rezervacije WHERE\r\n"
				+ "Smjestajna_Jedinica = ? and(\r\n"
				+ "( ? > Datum_Dolaska AND ? < Datum_Odlaska) \r\n"
				+ "or ( ?  > Datum_Dolaska AND ? < Datum_Odlaska)\r\n"
				+ "or ( ? <= Datum_Dolaska And ? >= Datum_Odlaska)) and ID <> ?";
		PreparedStatement stmt;
		try {
			stmt = konekcija.prepareStatement(upitDatuma);
			stmt.setString(1, comboBoxSmjestajnaJedinica.getValue().toString());
			stmt.setDate(2, java.sql.Date.valueOf(datePickerDatumDolaska.getValue()));
			stmt.setDate(3, java.sql.Date.valueOf(datePickerDatumDolaska.getValue()));
			stmt.setDate(4, java.sql.Date.valueOf(datePickerDatumOdlaska.getValue()));
			stmt.setDate(5, java.sql.Date.valueOf(datePickerDatumOdlaska.getValue()));
			stmt.setDate(6, java.sql.Date.valueOf(datePickerDatumDolaska.getValue()));
			stmt.setDate(7, java.sql.Date.valueOf(datePickerDatumOdlaska.getValue()));
			stmt.setInt(8, id);
			ResultSet rs = stmt.executeQuery(); 
			boolean provjera = rs.next();
			if(provjera == false) {

				String query = " update rezervacije set Ime = ? , Prezime = ?, Adresa = ?, Br_telefona = ?,"
						+ "Podatak_ID = ?, Datum_Dolaska = ?, Datum_Odlaska = ?, Smjestajna_Jedinica = ?,"
						+ "Broj_Nocenja = ?, Cijena_Nocenja = ?, Avans = ?, Ukupno_Zaduzenje = ?"
						+ " where ID = " + id;
				PreparedStatement preparedStmt;
				preparedStmt = konekcija.prepareStatement(query);

				preparedStmt.setString(1, txtFieldIme.getText());
				preparedStmt.setString(2, txtFieldPrezime.getText());
				preparedStmt.setString(3, txtFieldAdresa.getText());
				preparedStmt.setString(4, txtFieldBrTelefona.getText());
				preparedStmt.setString(5, txtFieldPodatakID.getText());
				preparedStmt.setDate(6, java.sql.Date.valueOf(datePickerDatumDolaska.getValue()));
				preparedStmt.setDate(7, java.sql.Date.valueOf(datePickerDatumOdlaska.getValue()));
				preparedStmt.setString(8, comboBoxSmjestajnaJedinica.getValue().toString());
				preparedStmt.setInt(9, Integer.parseInt(txtFieldBrojNocenja.getText()));
				preparedStmt.setDouble(10,Double.parseDouble(txtFieldCijena.getText()));
				preparedStmt.setDouble(11,Double.parseDouble(txtFieldAvans.getText()));
				preparedStmt.setDouble(12,Double.parseDouble(txtFieldUkupnoZaduzenje.getText()));
				preparedStmt.execute();
				Alert potvrda = new Alert(AlertType.CONFIRMATION);
				potvrda.setHeaderText("Uspjesno ste azurirali rezervaciju.");
				potvrda.showAndWait();
			}
			while(provjera) {
				Alert greska = new Alert(AlertType.WARNING);
				greska.setTitle("Upozorenje");
				greska.setHeaderText("Smjestajna jedinica: " + comboBoxSmjestajnaJedinica.getValue().toString() + " nije slobodna u datom periodu! \n Rezervacija nije izvrsena!");
				greska.showAndWait();
				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@FXML
	private void uskladiPodatke() {
		txtFieldBrojNocenja.setText(String.valueOf(ChronoUnit.DAYS.between(datePickerDatumDolaska.getValue(), datePickerDatumOdlaska.getValue())));
		txtFieldUkupnoZaduzenje.setText(String.valueOf((double)Integer.parseInt(txtFieldBrojNocenja.getText()) * Double.parseDouble(txtFieldCijena.getText())));
		txtFieldAvans.setText(String.valueOf(Double.parseDouble(txtFieldUkupnoZaduzenje.getText()) * 0.3));
	}
}

