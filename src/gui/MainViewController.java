package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemVote;
	
	@FXML 
	private MenuItem menuItemNewCandidate;
	
	@FXML
	private MenuItem menuItemListCandidate;
	
	@FXML
	public void onMenuItemVoteAction() {
		System.out.println("MenuVote");
	}
	
	@FXML
	public void onMenuItemNewCandidateAction() {
		System.out.println("MenuCandidate");
	}
	
	@FXML
	public void onMenuItemListCandidateAction() {
		System.out.println("MenuCandidate");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

}
