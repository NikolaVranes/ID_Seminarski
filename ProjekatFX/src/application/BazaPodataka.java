package application;
import java.sql.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
public class BazaPodataka {

	public static Connection getConnection(){
		try {
			Connection konekcija = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekat","root","nikola");
			return konekcija;

		} catch (SQLException e) {
			Alert greska = new Alert(AlertType.ERROR);
			greska.setContentText("Konekcija sa bazom nije uspjela");
		}
		return null;

	}

	
}


