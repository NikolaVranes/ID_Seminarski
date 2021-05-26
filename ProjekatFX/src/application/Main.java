package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;


public class Main extends Application {
	private double xOffsett = 0;
	private double yOffsett = 0;
	public void start(Stage primaryStage) {
		try {
			Parent root;
			root = FXMLLoader.load(getClass().getResource("pocetniFrame.fxml"));	
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED); 
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
					primaryStage.setX(event.getScreenX() - xOffsett);
					primaryStage.setY(event.getScreenY() - yOffsett);
				}
			});   
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	public static void main(String[] args) throws SQLException {
		launch(args);
		BazaPodataka.getConnection();

	}

}
