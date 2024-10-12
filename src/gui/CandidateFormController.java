package gui;

import java.awt.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.cell.TextFieldListCell;
import model.entities.Candidate;
import model.services.CandidateService;

public class CandidateFormController implements Initializable{
	
	private Candidate entity;
	
	private CandidateService service;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtParty;
	
	@FXML
	private TextField txtNumber;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setCandidate(Candidate entity) {
		this.entity = entity;
	}
	
	public void setCandidateService(CandidateService service) {
		this.service = service;
	}
	
	
	@FXML
	public void onBtSaveAction() {
		
	}
	
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		
	}
	
	public void initializeNodes() {
		
	}

}
