package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import gui.util.RandomGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.entities.Candidate;

public class ProofController implements Initializable{

	@FXML
	private Label lbName;
	
	@FXML
	private Label lbEnrollment;
	
	@FXML
	private Label lbNasc;
	
	@FXML
	private Label lbZone;
	
	@FXML
	private Label lbSection;
	
	@FXML
	private Label lbTurn;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
        lbName.setText(lbName.getText() + " " + RandomGenerator.generateRandomName());
        lbNasc.setText(lbNasc.getText() + " " + RandomGenerator.generateRandomBirthDate());
        lbEnrollment.setText(lbEnrollment.getText() + " " + RandomGenerator.generateRandomRegistrationNumber());
        lbSection.setText(lbSection.getText() + " " + RandomGenerator.generateRandomZoneAndSectionNumber());
        lbZone.setText(lbZone.getText() + " " + RandomGenerator.generateRandomZoneAndSectionNumber());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        lbTurn.setText(lbTurn.getText() + " " + today.format(formatter));
	   
	}
}
