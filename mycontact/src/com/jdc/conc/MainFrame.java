package com.jdc.conc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;

public class MainFrame implements Initializable{
	
	@FXML
	private RadioButton memory;
	
	public static boolean isMemory() {
		return MAINFRAME.memory.isSelected();
	}
	
	private static MainFrame MAINFRAME;
	
	public void addContact() {
		try {
			AddContact.showView();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void contactList() {
		try {
			ContactList.showView();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MAINFRAME = this;
	}
	
}
