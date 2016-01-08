package com.jdc.calc;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Calculator extends Application implements Initializable{
	
	private static final String FXML_FILE = "Calculator.fxml";
	
	@FXML
	private Label result;
	@FXML
	private Label tempResult;
	@FXML
	private GridPane grid;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// load fxml
		Parent root = FXMLLoader.load(getClass().getResource(FXML_FILE));

		// create scene
		Scene scene = new Scene(root);

		// set scene to stage
		primaryStage.setScene(scene);

		// show stage
		primaryStage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		grid.getChildren().forEach(a -> {
			if(a instanceof Button) {
				Button b = (Button) a;
				b.setOnAction(this::pressButton);
			}
		});
		
		this.clear();
	}
	
	private void pressButton(ActionEvent action) {
		Button b = (Button) action.getSource();
		switch (b.getText()) {
		case "C":
			clear();
			break;
		case "+/-":
			setPlusMinus();
			break;
		case "=":
			calculate();
			break;
		case ".":
			setFloatingPoint();
			break;
		case "SQR":
			calSqR();
			break;
		case "+":
		case "-":
		case "*":
		case "/":
			calculate(b.getText());
			break;

		default:
			pressNumber(b.getText());
			break;
		}
	}

	private void pressNumber(String text) {
		String tmp = result.getText();
		if("0".equals(tmp)) {
			result.setText(text);
		} else {
			result.setText(tmp + text);
		}
	}

	private void calculate(String text) {
		
		// tmp
		TempData tmpData = new TempData(tempResult.getText());
		// result
		double data2 = getDoubleResult();
		
		if(tmpData.isNew()) {
			tempResult.setText(String.valueOf(data2) + " " + text);
			result.setText("0");
		} else {
			Calculatable calc = Operators.getCalculator(tmpData.getOperator());
			double data1 = tmpData.getData();
			double dataRes = calc.calculate(data1, data2);

			tempResult.setText(String.valueOf(dataRes) + " " + text);
			result.setText("0");
		}
	}

	private double getDoubleResult() {
		String res = result.getText();
		if(res.endsWith(".")) {
			res += "0";
		}
		return Double.valueOf(res);
	}

	private void calSqR() {
		// tmp
		TempData tmpData = new TempData(tempResult.getText());
		// result
		double data2 = getDoubleResult();
		double rootResult = 0;
		
		if(tmpData.isNew()) {
			rootResult = Math.sqrt(data2);
		} else {
			Calculatable calc = Operators.getCalculator(tmpData.getOperator());
			double data1 = tmpData.getData();
			double dataRes = calc.calculate(data1, data2);
			rootResult = Math.sqrt(dataRes);
		}
		tempResult.setText("");
		result.setText(String.valueOf(rootResult));
	}

	private void calculate() {
		// tmp
		TempData tmpData = new TempData(tempResult.getText());
		// result
		double data1 = tmpData.getData();
		double data2 = getDoubleResult();

		Calculatable calc = Operators.getCalculator(tmpData.getOperator());

		double dataRes = calc.calculate(data1, data2);
		
		result.setText(String.valueOf(dataRes));
		tempResult.setText("");

	}

	private void setFloatingPoint() {
		String tmp = result.getText();
		if(!tmp.contains(".")) {
			result.setText(tmp + "."); 
		}
	}

	private void setPlusMinus() {
		
		String tmp = result.getText();
		
		if(tmp.startsWith("-")) {
			result.setText(tmp.substring(1));
		} else {
			result.setText("-" + tmp);
		}

	}

	private void clear() {
		tempResult.setText("");
		result.setText("0");
	}
	
	class TempData {
		private String operator;
		private double data;
		private boolean isNew;
		
		public TempData(String str) {
			String [] arrays = str.split(" ");
			if(arrays.length == 2) {
				operator = arrays[1];
				data = Double.valueOf(arrays[0]);
			} else {
				isNew = true;
			}
		}
		
		public boolean isNew() {
			return isNew;
		}
		
		public double getData() {
			return data;
		}
		
		public String getOperator() {
			return operator;
		}
	}

}
