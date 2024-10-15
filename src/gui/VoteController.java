package gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import model.entities.Candidate;
import model.services.CandidateService;

public class VoteController implements Initializable {
    
	private CandidateService candidateService;
	
    @FXML
    private Button btOne;
    @FXML
    private Button btTwo;
    @FXML
    private Button btThree;
    @FXML
    private Button btFour;
    @FXML
    private Button btFive;
    @FXML
    private Button btSix;
    @FXML
    private Button btSeven;
    @FXML
    private Button btEight;
    @FXML
    private Button btNine;
    @FXML
    private Button btZero;
    @FXML
    private Button btWhite;
    @FXML
    private Button btCorrect;
    @FXML
    private Button btConfirm;
    
    @FXML
    private Label lbName;
    
    @FXML
    private Label lbParty;
    
    @FXML
    private Label lbNumber;
    
    @FXML
    private Label lbError;
    
    @FXML
    private Label lbConfirm;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btOne.setOnAction(e -> appendNumber("1"));
        btTwo.setOnAction(e -> appendNumber("2"));
        btThree.setOnAction(e -> appendNumber("3"));
        btFour.setOnAction(e -> appendNumber("4"));
        btFive.setOnAction(e -> appendNumber("5"));
        btSix.setOnAction(e -> appendNumber("6"));
        btSeven.setOnAction(e -> appendNumber("7"));
        btEight.setOnAction(e -> appendNumber("8"));
        btNine.setOnAction(e -> appendNumber("9"));
        btZero.setOnAction(e -> appendNumber("0"));
        
        btCorrect.setOnAction(e -> clearLastDigit());
        btWhite.setOnAction(e -> clearAll());
        btConfirm.setOnAction(e -> confirmCandidate());
        Constraints.setTextLabelLength(lbNumber, 2);
    }

    private void appendNumber(String number) {
        lbNumber.setText(lbNumber.getText() + number);
        
        if(lbNumber.getText().length() == 2) {
        	Candidate obj = candidateService.findByNumber(lbNumber.getText());
        	if(obj != null) {
        		lbName.setText(obj.getName());
        		lbParty.setText(obj.getParty());
        	}else {
        		lbError.setText("");;
        		lbError.setText("Candidato não existe");
        	}
        }else {
        	lbName.setText("");
        	lbParty.setText("");
        }
    }

    private void clearLastDigit() {
        String currentNumberCandidate = lbNumber.getText();
        if (!currentNumberCandidate.isEmpty()) {
            lbNumber.setText(currentNumberCandidate.substring(0, currentNumberCandidate.length() - 1));
        }
        
        if(lbNumber.getText().length() != 2) {
        	lbName.setText("");
        	lbParty.setText("");
        	lbError.setText("");
        	lbConfirm.setText("");
        }
    }

    private void clearAll() {
        lbNumber.setText("");
        lbName.setText("");
        lbParty.setText("");
    }
    
    public void confirmCandidate() {
    	String currentNumberCandidate = lbNumber.getText();
    	
    	Candidate obj = candidateService.findByNumber(currentNumberCandidate);
    	if(obj == null) {
    		
    		lbError.setText("");
    		lbError.setText("Candidato não existe");
    	}else {
    		candidateService.addVote(currentNumberCandidate);
    		lbConfirm.setText("Candidato recebeu o seu voto");
    	}
    }
    
    public void setCandidateService(CandidateService service) {
    	this.candidateService = service;
    }
    
    public void printVote() {
    	Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Print proof of vote?");
    	
    	if(result.get() == ButtonType.OK) {
    		
    	}
    }
}

