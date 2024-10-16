package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class Alerts {

	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
	
	public static Optional<ButtonType> showConfirmation(String title, String content) {
		 Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		return alert.showAndWait();
		} 
	
	public static Optional<ButtonType> showConfirmationProofVote(String title, String content) {
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(content);

	    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
	    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

	    alert.getButtonTypes().setAll(yesButton, noButton);

	    return alert.showAndWait();
	}

}