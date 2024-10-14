package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.CandidateService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemVote;

	@FXML
	private MenuItem menuItemNewCandidate;

	@FXML
	private MenuItem menuItemListCandidate;

	@FXML
	public void onMenuItemVoteAction() {

	}

	@FXML
	public void onMenuItemNewCandidateAction() {
		loadView("/gui/CandidateForm.fxml", (CandidateFormController controller) -> {
			controller.setCandidateService(new CandidateService());
			controller.setControllerMain(this);
		});
	}

	@FXML
	public void onMenuItemListCandidateAction() {
		loadView("/gui/CandidateList.fxml", (CandidateListController controller) -> {
			controller.setCandidateService(new CandidateService());
			controller.updateTableView();
		});
	}
	
	public void backToMainView() {
	    try {
	        Scene mainScene = Main.getMainScene();
	        ScrollPane scrollPane = (ScrollPane) mainScene.getRoot();
	        VBox mainVbox = (VBox) scrollPane.getContent();

	        // Recupera o menu principal
	        Node mainMenu = mainVbox.getChildren().get(0);
	        
	        // Limpa e reconfigura o conteúdo da VBox para garantir que está usando a instância original
	        mainVbox.getChildren().clear();
	        mainVbox.getChildren().add(mainMenu);
	        
	        mainVbox.setFillWidth(true);
	    } catch (Exception e) {
	        Alerts.showAlert("Exception", "Error returning to MainView", e.getMessage(), AlertType.ERROR);
	    }
	}


	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();

			Scene mainScene = Main.getMainScene();
			VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVbox.getChildren());

			T controller = loader.getController();
			initializingAction.accept(controller);
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
