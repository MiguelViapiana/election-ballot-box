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

public class ProofWithCandidateController implements Initializable{
	
    public ProofWithCandidateController(Candidate candidate) {
        this.candidate = candidate;
    }
    
    public ProofWithCandidateController() {
    	
    }

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
	
	@FXML
	private Label lbCandidateName;
	
	@FXML
	private Label lbCandidateNumber;
	
	@FXML
	private Label lbCandidateParty;
	
	private Candidate candidate;
	
	public void initInitialize() {
		initialize(null, null);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	    		
		if(candidate != null) {
	        lbName.setText(lbName.getText() + " " + RandomGenerator.generateRandomName());
	        lbNasc.setText(lbNasc.getText() + " " + RandomGenerator.generateRandomBirthDate());
	        lbEnrollment.setText(lbEnrollment.getText() + " " + RandomGenerator.generateRandomRegistrationNumber());
	        lbSection.setText(lbSection.getText() + " " + RandomGenerator.generateRandomZoneAndSectionNumber());
	        lbZone.setText(lbZone.getText() + " " + RandomGenerator.generateRandomZoneAndSectionNumber());
	        
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        LocalDate today = LocalDate.now();
	        lbTurn.setText(lbTurn.getText() + " " + today.format(formatter));
	        
            lbCandidateName.setText("Candidate Name: \n" + candidate.getName());
            lbCandidateNumber.setText("Number: " + candidate.getNumber());
            lbCandidateParty.setText("Party: " + candidate.getParty());
		} 
	}
	
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	
	public Candidate getCandidate() {
		return candidate;
	}
}
