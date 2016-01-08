package com.jdc.diceGame;

import static com.jdc.diceGame.GameApplication.switchView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jdc.diceGame.user.Player;
import com.jdc.diceGame.user.SystemPlayer;
import com.jdc.diceGame.user.User;
import com.jdc.diceGame.user.UserData;
import com.jdc.diceGame.user.UserDataManagerImp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class UserConfiguration implements Initializable{
	
	@FXML
	private TextField name;
	@FXML
	private RadioButton free;
	@FXML
	private RadioButton premium;
	@FXML
	private CheckBox confirm;
	@FXML
	private Button create;
	@FXML
	private Label amount;

	public static Node getView() throws IOException {
		return FXMLLoader.load(UserConfiguration.class.getResource("UserConfiguration.fxml"));
	}
	
	public void clear() {
		name.clear();
		free.setSelected(true);
		confirm.setSelected(false);
	}
	
	public void create(ActionEvent e) {
		
		try {
			// get UserData from view
			UserData data = getUserData();
			
			if(null != data) {
				UserDataManagerImp.getManager().create(data);
			}
			
			// load game page
			GamePlayGround.setUser(data);
			switchView(GamePlayGround.getView());
			
		} catch (Exception ex) {
			DialogHelper.showMessage(ex.getMessage());
		}
	}
	
	private UserData getUserData() {
		String userName = name.getText();
		if(userName.isEmpty()) {
			throw new RuntimeException("Please set user name.");
		}
		
		
		Player user = new User();
		user.setName(userName);
		user.setAmount(Integer.parseInt(amount.getText()));
		
		Player system = new SystemPlayer();
		system.setName("System Player");
		system.setAmount(user.getAmount());
		
		UserData data = new UserData(user, system);
		
		return data;
	}

	public void acceptCondition() {
		if(confirm.isSelected()) {
			create.setDisable(false);
		} else {
			create.setDisable(true);
		}
	}
	
	public void setAccountType() {
		if(free.isSelected()) {
			amount.setText("5000");
		} else {
			amount.setText("50000");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		free.selectedProperty().addListener((a, b, c) -> {
			setAccountType();
		});
		
		premium.selectedProperty().addListener((a, b, c)->{
			setAccountType();
		});
		
		confirm.selectedProperty().addListener((a, b, c) -> {
			acceptCondition();
		});
		
		create.setOnAction(this::create);
		
		setAccountType();
		acceptCondition();
	}
}
