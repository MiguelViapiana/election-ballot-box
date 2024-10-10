package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Candidate;
import model.services.CandidateService;

public class CandidateListController implements Initializable{
	
	private CandidateService service;

	@FXML
	private TableView<Candidate> tableViewCandidate;
	
	@FXML
	private TableColumn<Candidate, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Candidate, String> tableColumnName;
	
	@FXML
	private TableColumn<Candidate, String> tableColumnParty;
	
	@FXML
	private TableColumn<Candidate, Integer> tableColumnNumber;
	
	private ObservableList<Candidate> obsList;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	public void setCandidateService(CandidateService serivce) {
		this.service = serivce;
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnParty.setCellValueFactory(new PropertyValueFactory<>("party"));
		tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		
		Stage stage = (Stage)Main.getMainScene().getWindow();
		tableViewCandidate.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Candidate> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewCandidate.setItems(obsList);
	}

}
