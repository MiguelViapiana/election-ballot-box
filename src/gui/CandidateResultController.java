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
import javafx.scene.control.TableCell;
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
	private TableColumn<Candidate, Double> tableColumnPercentage;
	
	@FXML
	private Label lbTotalVotes;
	
	@FXML
	private Label lbCandidateName;
	
	private ObservableList<Candidate> obsList;
	
	public void setCandidateService(CandidateService service) {
		this.service = service;
	}
	
	public void updateTableView() {
		if (service == null){
			throw new IllegalStateException("Service was null");
		}
		List<Candidate> list = service.findAll();
		
		int totalVotes = list.stream().mapToInt(Candidate::getNumVotes).sum();
		for(Candidate candidate : list) {
			double percentage = (double) candidate.getNumVotes() / totalVotes * 100;
			candidate.setPercentage(percentage);
		}
		obsList = FXCollections.observableArrayList(list);
		tableViewCandidate.setItems(obsList);
		
		initializeVoteData();
		getPercentage();
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
		lbCandidateName.setText(lbCandidateName.getText() + " " + candidateWithMostVotes());
	}

	public String countTotalVotes() {
		if (service == null){
			throw new IllegalStateException("Service was null");
		}
		List<Candidate> list = service.findAll();
		int totalVotes = list.stream().mapToInt(Candidate::getNumVotes).sum();
		return String.valueOf(totalVotes);
	}
	
	public String candidateWithMostVotes() {
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		Integer highestQtdVotes = 0;
		String candidateName = "";
		List<Candidate> list = service.findAll();
		for(Candidate obj : list) {
			if(obj.getNumVotes() > highestQtdVotes) {
				highestQtdVotes = obj.getNumVotes();
				candidateName = obj.getName();
			}
		}
		for(Candidate obj : list) {
			if(highestQtdVotes == obj.getNumVotes() && candidateName != obj.getName()) {
				candidateName = candidateName + " and " + obj.getName();
			}
		}
		return candidateName;
	}
	
	public void getPercentage() {
		tableColumnPercentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
		tableColumnPercentage.setCellFactory(column -> new TableCell<Candidate, Double>	(){
			@Override
			protected void updateItem(Double percentage, boolean empty) {
				super.updateItem(percentage, empty);
				if(  empty || percentage == null) {
					setText(null);
				}else {
					setText(String.format("%.2f%%", percentage));
				}
			}
		});
	}	
}
