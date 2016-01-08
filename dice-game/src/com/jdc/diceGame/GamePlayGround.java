package com.jdc.diceGame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jdc.diceGame.game.Dice;
import com.jdc.diceGame.user.UserData;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class GamePlayGround implements Initializable{
	
	private static UserData CURRENT_USER;
	
	@FXML
	private Text userName;
	@FXML
	private Text userAmount;
	@FXML
	private Slider slider;
	@FXML
	private Label voteMoney;
	@FXML
	private ImageView userDice1;
	@FXML
	private ImageView userDice2;
	@FXML
	private ImageView sysDice1;
	@FXML
	private ImageView sysDice2;
	
	@FXML
	private Text systemAmount;
	@FXML
	private Button start;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userName.setText(CURRENT_USER.getUser().getName());
		userAmount.setText(String.valueOf(CURRENT_USER.getUser().getAmount()));
		slider.setMin(0);
		slider.setMax(CURRENT_USER.getUser().getAmount());
		
		systemAmount.setText(String.valueOf(CURRENT_USER.getSystem().getAmount()));
	
		start.setDisable(true);
		
		slider.valueProperty().addListener((a,b,c) -> {
			voteMoney.setText(String.valueOf(c.intValue()));
			start.setDisable(c.intValue() <= 0);
		});
		
		start.setOnAction(a -> {
			
			try {
				// vote money
				int voteMoney = ((Double)slider.getValue()).intValue();
				
				// play user
				CURRENT_USER.getUser().getDiceSet().play();
				
				// play system
				CURRENT_USER.getSystem().getDiceSet().play();
				
				// compare result
				int intResult = CURRENT_USER.getUser().getDiceSet().compare(
						CURRENT_USER.getSystem().getDiceSet());
				
				String message  = "";
				
				if(intResult > 0) {
					// win
					CURRENT_USER.getUser().setBalance(voteMoney);
					CURRENT_USER.getSystem().setBalance(0 - voteMoney);
					message = "You win!!";
				} else if(intResult < 0) {
					// loose
					CURRENT_USER.getUser().setBalance(0 - voteMoney);
					CURRENT_USER.getSystem().setBalance(voteMoney);
					message = "You loose!!";
				} else {
					// draw
					message = "Draw!!";
				}
				
				userDice1.setImage(getImage(CURRENT_USER.getUser().getDiceSet().getD1()));
				userDice2.setImage(getImage(CURRENT_USER.getUser().getDiceSet().getD2()));
				
				sysDice1.setImage(getImage(CURRENT_USER.getSystem().getDiceSet().getD1()));
				sysDice2.setImage(getImage(CURRENT_USER.getSystem().getDiceSet().getD2()));

				userAmount.setText(String.valueOf(CURRENT_USER.getUser().getAmount()));
				systemAmount.setText(String.valueOf(CURRENT_USER.getSystem().getAmount()));
				
				int max = (CURRENT_USER.getUser().getAmount() <= CURRENT_USER.getSystem().getAmount())
						?CURRENT_USER.getUser().getAmount():CURRENT_USER.getSystem().getAmount();
				slider.setMax(max);
				slider.setMin(0);
				
				DialogHelper.showMessage(message);
				
			} catch(Exception e) {
				e.printStackTrace();
			}

		});
	}
	
	private Image getImage(Dice d1) {
		String imageName = String.format("%d.png", d1.getPoint().getValue());
		return new Image(this.getClass().getResourceAsStream(imageName));
	}

	public static Node getView() throws IOException {
		return FXMLLoader.load(GamePlayGround.class.getResource("GamePlayGound.fxml"));
	}
	
	public static void setUser(UserData data) {
		CURRENT_USER = data;
	}

}
