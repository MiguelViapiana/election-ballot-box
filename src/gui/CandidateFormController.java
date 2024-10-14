package gui;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Candidate;
import model.exceptions.ValidateException;
import model.services.CandidateService;

public class CandidateFormController implements Initializable{
	
	private Candidate entity;
	
	private CandidateService service;
	
	private MainViewController mainController;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private DatePicker dpBirthDate;
	
	@FXML
	private TextField txtParty;
	
	@FXML
	private TextField txtNumber;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorParty;
	
	@FXML
	private Label labelErrorNumber;
	
	@FXML
	private Label labelErrorBirthDate;
	
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
	
	public void setControllerMain(MainViewController controller) {
		this.mainController = controller;
	}
	
	@FXML
	public void onBtSaveAction() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			Alerts.showConfirmation("Candidate added successfully", null);
			mainController.onMenuItemListCandidateAction();
			
		}
		catch(DbException e) {
			Alerts.showAlert("Errors saving object", null, e.getMessage(), AlertType.ERROR);
		}
		catch(ValidateException e) {
			setErrorsMessages(e.getErrors());
		}	
	}
	
	@FXML
	public void onBtCancelAction() {
		mainController.backToMainView();
	}
	
	@FXML
	private Candidate getFormData() {
		Candidate obj = new Candidate();
		ValidateException exception = new ValidateException("Validaton error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addErrors("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		
		if(txtNumber.getText() == null || txtNumber.getText().trim().equals("")) {
			exception.addErrors("number", "Field cant't be empty");
		}
		obj.setNumber(Utils.tryParseToInt(txtNumber.getText()));
		
		if(txtParty.getText() == null || txtParty.getText().trim().equals("")) {
			exception.addErrors("party", "Field cant't be empty");
		}
		obj.setParty(txtParty.getText());
		
		if(dpBirthDate.getValue() == null) {
			exception.addErrors("birthDate", "Field cant't be empty");
		}else {
			Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setBirthDate(Date.from(instant));
		}
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		
		return obj;	
	}
	
	private void setErrorsMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		labelErrorName.setText(fields.contains("name") ? errors.get("name") : ""); 	
		labelErrorNumber.setText(fields.contains("number") ? errors.get("number") : "");		
		labelErrorParty.setText(fields.contains("party") ? errors.get("party") : "");		
		labelErrorBirthDate.setText(fields.contains("birthDate") ? errors.get("birthDate") : "");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 70);
		Constraints.setTextFieldMaxLength(txtParty, 10);
		Constraints.setTextFieldInteger(txtNumber);
		Constraints.setTextFieldMaxLength(txtNumber, 2);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
	}
}
