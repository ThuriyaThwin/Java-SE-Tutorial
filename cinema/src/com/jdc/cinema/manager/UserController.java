package com.jdc.cinema.manager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jdc.cinema.entity.Role;
import com.jdc.cinema.entity.User;
import com.jdc.cinema.main.AllertManager;
import com.jdc.cinema.main.UserInputException;
import com.jdc.cinema.service.UserService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserController implements Initializable{
	
	// search region
	@FXML
	private TextField schName;
	@FXML
	private ComboBox<Role> schRoles;
	
	// new user region
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtLogin;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private ComboBox<Role> txtRoles;
	
	
	// user table region
	@FXML
	private TableView<User> userTable;
	@FXML
	private TableColumn<User, String> colName;
	@FXML
	private TableColumn<User, String> colLogin;
	@FXML
	private TableColumn<User, String> colRole;
	
	
	private UserService service;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		service = UserService.getInstance();
		final List<Role> roles = service.getRoles();
		txtRoles.getItems().addAll(roles);
		schRoles.getItems().addAll(roles);
		
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colLogin.setCellValueFactory(new PropertyValueFactory<>("loginId"));
		colRole.setCellValueFactory(new PropertyValueFactory<>("roleId"));
		
		schName.textProperty().addListener((a, b, c) -> {
			search();
		});
		
		schRoles.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
			search();
		});
		
		search();
	}
	
	public void addUser() {
		try {
			User user = getViewData();
			service.create(user);
			clear();
			clearSearch();
		} catch (UserInputException e) {
			AllertManager.showAllert(e.getMessage());
		}
	}
	
	
	private User getViewData() {
		
		User user = new User();
		user.setName(getAndCheckString(txtName.getText(), "Please enter user name."));
		user.setLoginId(getAndCheckString(txtLogin.getText(), "Please enter login id."));
		user.setPassword(getAndCheckString(txtPassword.getText(), "Please enter password."));

		Role role = txtRoles.getSelectionModel().getSelectedItem();
		if(null == role || role.getId().isEmpty()) {
			throw new UserInputException("Please select role.");
		}
		user.setRoleId(role.getId());
		
		return user;
	}
	
	private String getAndCheckString(String text, String message) {
		if(null == text || text.isEmpty()) {
			throw new UserInputException(message);
		}
		
		return text;
	}

	public void clearSearch() {
		schName.clear();
		schRoles.getSelectionModel().clearSelection();
		
		search();
	}
	
	public void clear() {
		
		txtName.clear();
		txtLogin.clear();
		txtPassword.clear();
		txtRoles.getSelectionModel().clearSelection();
		
	}
	
	public void search() {
		final List<User> data = service.search(schName.getText(), 
				schRoles.getSelectionModel().getSelectedItem());
		
		userTable.getItems().clear();
		userTable.getItems().addAll(data);
	}

}
