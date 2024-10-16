package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Candidate;
import model.services.CandidateService;

public class CandidateListController implements Initializable, DataChangeListener{
	
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
	
	@FXML
	private TableColumn<Candidate, Integer> tableColumnNumVotes;
	
	@FXML
	private TableColumn<Candidate, Date> tableColumnBirthDate;
	
	@FXML
	private TableColumn<Candidate, Candidate> tableColumnEDIT;

	@FXML
	private TableColumn<Candidate, Candidate> tableColumnREMOVE;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Candidate> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Candidate obj = new Candidate();
		createDialogForm(obj, "/gui/CandidateForm.fxml", parentStage);
	}
	
	
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
		tableColumnNumVotes.setCellValueFactory(new PropertyValueFactory<>("numVotes"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		
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
		initEditButtons();
	}
	
	private void createDialogForm(Candidate obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CandidateFormController controller = loader.getController();
			controller.setCandidate(obj);
			controller.setCandidateService(new CandidateService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Candidate data");
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
	
	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Candidate, Candidate>(){
			private final Button button = new Button("edit");
			
			@Override
			protected void updateItem(Candidate obj, boolean empty) {
				super.updateItem(obj, empty);
				if(obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/CandidateForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	@Override
	public void onDataChanged() {
		updateTableView();
		
	}

}
