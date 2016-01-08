package com.jdc.book.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.controllers.utils.CartItem;
import com.jdc.book.app.controllers.utils.CustomerList;
import com.jdc.book.app.controllers.utils.MessageUtils;
import com.jdc.book.app.controllers.utils.Navigator;
import com.jdc.book.app.controllers.utils.Transportable;
import com.jdc.book.app.models.AuthorModel;
import com.jdc.book.app.models.CheckOutModel;
import com.jdc.book.app.models.imp.AuthorModelImp;
import com.jdc.book.app.models.imp.CheckOutModelImp;
import com.jdc.book.db.entity.Author;
import com.jdc.book.db.entity.Customer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.WindowEvent;

public class CheckOut implements Initializable{
	
	private List<CartItem> items;
	
	@FXML
	private TextField name;
	@FXML
	private TextField phone;
	@FXML
	private TextField mail;
	@FXML
	private TextArea address;
	@FXML
	private TextField paid;
	
	@FXML
	private TableView<CartItem> table;
	@FXML
	private TableColumn<CartItem, String> colBook;
	@FXML
	private TableColumn<CartItem, String> colAuthor;
	@FXML
	private TableColumn<CartItem, String> colPrice;
	@FXML
	private TableColumn<CartItem, String> colCount;
	@FXML
	private TableColumn<CartItem, String> colTotal;
	@FXML
	private Label total;
	private DoubleProperty totalProperty = new SimpleDoubleProperty(0);
	
	private CheckOutModel model;
	private AuthorModel authModel;
	
	private Customer selectedCustomer;
	private Transportable<Customer> transporter;
	
	public static Node getView(List<CartItem> items) {
		try {
			FXMLLoader loader = new FXMLLoader(CheckOut.class.getResource("CheckOut.fxml"));
			Node node = loader.load();
			
			CheckOut controller = loader.getController();
			controller.setItems(items);
			
			return node;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BookAppException(e.getMessage(), false);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		total.textProperty().bind(totalProperty.asString());
		paid.textProperty().bind(total.textProperty());
		
		model = new CheckOutModelImp();
		authModel = new AuthorModelImp();
		
		colAuthor.setCellValueFactory(param -> {
			if(null != param) {
				CartItem item = param.getValue();
				Author author = authModel.findById(item.getBook().getAuthor_id());
				if(null != author) {
					return new SimpleStringProperty(author.getName());
				}
			}
			return null;
		});
		
		colBook.setCellValueFactory(param -> {
			if(null != param) {
				CartItem item = param.getValue();
				if(null != item) {
					return new SimpleStringProperty(item.getBook().getName());
				}
			}
			return null;
		});
		
		colCount.setCellValueFactory(param -> {
			if(null != param) {
				CartItem item = param.getValue();
				if(null != item) {
					return item.getCountProperty().asString();
				}
			}
			return null;
		});
		
		colPrice.setCellValueFactory(param -> {
			if(null != param) {
				CartItem item = param.getValue();
				if(null != item) {
					return new SimpleStringProperty(String.valueOf(item.getBook().getPrice()));
				}
			}
			return null;
		});
		
		colTotal.setCellValueFactory(param -> {
			if(null != param) {
				CartItem item = param.getValue();
				if(null != item) {
					return item.getTotalProperty().asString();
				}
			}
			return null;
		});
		
	}
	
	public void save() {
		try {
			
			model.checkOut(this.getCustomer(), items);
			Navigator.navigate("SoldOutList");
			
		} catch (BookAppException e) {
			MessageUtils.handle(e);
		}
	}
	
	public void clear() {
		name.clear();
		phone.clear();
		mail.clear();
		address.clear();
		
		name.setEditable(true);
		phone.setEditable(true);
		mail.setEditable(true);
		address.setEditable(true);
	}

	public void callCustomers() {
		selectedCustomer = null;
		transporter = CustomerList.showView(this::onCloseCustomerList);
	}
	
	private void onCloseCustomerList(WindowEvent event) {
		this.selectedCustomer = transporter.transport();
		if(null != this.selectedCustomer) {
			this.setText(selectedCustomer.getName(), name);
			this.setText(selectedCustomer.getPhone(), phone);
			this.setText(selectedCustomer.getMail(), mail);
			this.setText(selectedCustomer.getAddress(), address);
		}
	}
	
	private Customer getCustomer() {
		if(selectedCustomer == null) {
			selectedCustomer = new Customer();
			selectedCustomer.setName(name.getText());
			if(selectedCustomer.getName() == null || selectedCustomer.getName().isEmpty()) {
				throw new BookAppException("Customer name must be entered!", true);
			}
			selectedCustomer.setMail(mail.getText());
			selectedCustomer.setPhone(phone.getText());
			selectedCustomer.setAddress(address.getText());
		}
		return selectedCustomer;
	}
	
	private void setItems(List<CartItem> items) {
		if(null == items || items.size() == 0) {
			throw new BookAppException("Please select Items to checkout.", true);
		}
		this.items = items;
		
		table.getItems().addAll(this.items);
		items.stream().forEach(a -> totalProperty.set(totalProperty.get() + a.getTotalProperty().get()));
	}
	
	private void setText(String value, TextInputControl control) {
		control.setText(value);
		control.setEditable(false);
	}

}
