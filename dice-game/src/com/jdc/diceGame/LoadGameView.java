package com.jdc.diceGame;

import static com.jdc.diceGame.GameApplication.switchView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jdc.diceGame.user.UserData;
import com.jdc.diceGame.user.UserDataManager;
import com.jdc.diceGame.user.UserDataManagerImp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoadGameView implements Initializable{
	
	@FXML
	private TextField name;
	@FXML
	private Label userPoint;
	@FXML
	private Label systemPoint;
	@FXML
	private Button loadGameBtn;
	
	private UserData userData;
	
	
	public static Node getView() throws IOException {
		return FXMLLoader.load(LoadGameView.class.getResource("LoadGameView.fxml"));
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		name.focusedProperty().addListener((a, b, c) -> {
			
			userData = null;
			loadGameBtn.setDisable(true);
			
			if(c) {
				// focus in
				name.setStyle("-fx-background-color:white;");
				userPoint.setText("");
				systemPoint.setText("");
			} else {
				// focus out
				String userName = name.getText();
				UserDataManager manager = UserDataManagerImp.getManager();
				userData = manager.find(userName);
				
				if(userData == null) {
					// show alert dialog
					DialogHelper.showMessage("Please enter valid user name!");
					// color red to text field
					name.setStyle("-fx-background-color:red;");
				} else {
					// load data to user view
					loadGameBtn.setDisable(false);
					userPoint.setText(String.valueOf(userData.getUser().getAmount()));
					systemPoint.setText(String.valueOf(userData.getSystem().getAmount()));
				}
			}
			
		});
	}

	public void welcome() {
		try {
			Node node = WelcomeView.getView();
			GameApplication.switchView(node);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startGame() {
		try {
			
			GamePlayGround.setUser(userData);
			switchView(GamePlayGround.getView());

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
