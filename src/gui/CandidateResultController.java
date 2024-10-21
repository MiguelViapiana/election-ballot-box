package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Candidate;
import model.services.CandidateService;

public class CandidateResultController implements Initializable{
	
	private CandidateService service;
	
	@FXML
	private TableView<Candidate> tableViewCandidate;

	@FXML
	private TableColumn<Candidate, String> tableColumnName;
	
	@FXML
	private TableColumn<Candidate, String> tableColumnParty;
	
	@FXML
	private TableColumn<Candidate, Integer> tableColumnNumber;
	
	@FXML
	private TableColumn<Candidate, Integer> tableColumnNumVotes;
	
	@FXML
	private TableColumn<Candidate, Integer> tableColumnPorcentage;
	
	@FXML
	private Label lbTotalVotes;
	
	private ObservableList<Candidate> obsList;
	
	public void setCandidateService(CandidateService service) {
		this.service = service;
	}
	
	public void updateTableView() {
		if (service == null){
			throw new IllegalStateException("Service was null");
		}
		List<Candidate> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewCandidate.setItems(obsList);
		
		initializeVoteData();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnParty.setCellValueFactory(new PropertyValueFactory<>("party"));
		tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		tableColumnNumVotes.setCellValueFactory(new PropertyValueFactory<>("numVotes"));
		
		Stage stage = (Stage)Main.getMainScene().getWindow();
		tableViewCandidate.prefHeightProperty().bind(stage.heightProperty());
	}
	
	private void initializeVoteData() {
		lbTotalVotes.setText(lbTotalVotes.getText() + " " + countTotalVotes());
	}

	public String countTotalVotes() {
		if (service == null){
			throw new IllegalStateException("Service was null");
		}
		int totalvotes = 0;
		List<Candidate> list = service.findAll();
		for(Candidate obj : list) {
			totalvotes += obj.getNumVotes();
		}
		return String.valueOf(totalvotes);
	}
}
