package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
		btConfirm.setOnAction(e -> confirmCandidate(e));
		Constraints.setTextLabelLength(lbNumber, 2);
	}

	private void appendNumber(String number) {
		lbNumber.setText(lbNumber.getText() + number);

		if (lbNumber.getText().length() == 2) {
			Candidate obj = candidateService.findByNumber(lbNumber.getText());
			if (obj != null) {
				lbName.setText(obj.getName());
				lbParty.setText(obj.getParty());
			} else {
				lbError.setText("");
				;
				lbError.setText("Candidato não existe");
			}
		} else {
			lbName.setText("");
			lbParty.setText("");
		}
	}

	private void clearLastDigit() {
		String currentNumberCandidate = lbNumber.getText();
		if (!currentNumberCandidate.isEmpty()) {
			lbNumber.setText(currentNumberCandidate.substring(0, currentNumberCandidate.length() - 1));
		}

		if (lbNumber.getText().length() != 2) {
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
		lbConfirm.setText("");
	}

	public void confirmCandidate(ActionEvent event) {
		String currentNumberCandidate = lbNumber.getText();
		Candidate obj = candidateService.findByNumber(currentNumberCandidate);
		if (obj == null) {
			lbError.setText("");
			lbError.setText("Candidato não existe");
		} else {
			Optional<ButtonType> result = Alerts.showConfirmation("Proof of vote", "On your receipt, do you want it to contain the candidate who received the vote?");
			
			if(result.get()  == ButtonType.OK) {
				candidateService.addVote(currentNumberCandidate);
				Stage parentStage = Utils.currentStage(event);
				createProofVote("/gui/ProofWithCandidateView.fxml", parentStage, (ProofWithCandidateController controller) ->{
					controller.setCandidate(obj);
					controller.initInitialize();
				});
				lbConfirm.setText("Candidato recebeu o seu voto");
				clearAll();	
			}
			else {
				candidateService.addVote(currentNumberCandidate);
				Stage parentStage = Utils.currentStage(event);
				createProofVote("/gui/proofView.fxml", parentStage, x ->{});
				lbConfirm.setText("Candidato recebeu o seu voto");
				clearAll();
			}
		}
	}

	public void setCandidateService(CandidateService service) {
		this.candidateService = service;
	}

	public synchronized <T> void createProofVote(String absoluteName, Stage parentStage,  Consumer<T> initializingAction) {
	    try {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
	        Pane pane = loader.load();
	        
			T controller = loader.getController();
			initializingAction.accept(controller);
	        
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Proof Vote");
	        dialogStage.setScene(new Scene(pane));
	        dialogStage.setResizable(false);
	        dialogStage.initOwner(parentStage);
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        
	        dialogStage.showAndWait();
	    	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
	    }
	}

}
