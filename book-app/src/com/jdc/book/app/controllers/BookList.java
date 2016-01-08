package com.jdc.book.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.controllers.utils.CartItem;
import com.jdc.book.app.controllers.utils.CartView;
import com.jdc.book.app.controllers.utils.MessageUtils;
import com.jdc.book.app.controllers.utils.CartView.Mode;
import com.jdc.book.app.models.AuthorModel;
import com.jdc.book.app.models.BookModel;
import com.jdc.book.app.models.CategoryModel;
import com.jdc.book.app.models.imp.AuthorModelImp;
import com.jdc.book.app.models.imp.BookModelImp;
import com.jdc.book.app.models.imp.CategoryModelImp;
import com.jdc.book.db.entity.Author;
import com.jdc.book.db.entity.Book;
import com.jdc.book.db.entity.Category;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class BookList implements Initializable{

	@FXML
	private ComboBox<Category> categories;
	@FXML
	private ComboBox<Author> authors;
	@FXML
	private TextField name;
	
	@FXML
	private TableView<Book> books;
	@FXML
	private TableColumn<Book, String> colCategory;
	@FXML
	private TableColumn<Book, String> colAuthor;
	@FXML
	private TableColumn<Book, String> colBook;
	@FXML
	private TableColumn<Book, String> colPrice;
	@FXML
	private HBox cartContainer;
	
	private CartView myCart;
	
	private BookModel bookModel;
	private CategoryModel catModel;
	private AuthorModel autModel;
	
	public void search() {
		books.getItems().clear();
		books.getItems().addAll(bookModel.search(categories.getValue(), authors.getValue(), name.getText()));
	}
	
	public void clear() {
		categories.setValue(null);
		authors.setValue(null);
		name.clear();
	}
	
	public void addToCart(ActionEvent event) {
		try {
			addToCart();
		} catch(BookAppException e) {
			MessageUtils.handle(e);
		}
	}
	
	public void addToCart() {
		Book b = books.getSelectionModel().getSelectedItem();
		if(null != b) {
			CartItem item = new CartItem(b);
			myCart.addToCart(item);
		} else {
			throw new BookAppException("Please select one book to add!!", true);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			bookModel = new BookModelImp();
			catModel = new CategoryModelImp();
			autModel = new AuthorModelImp();
			
			categories.getItems().addAll(catModel.find(null));
			authors.getItems().addAll(autModel.find(null));
			
			colAuthor.setCellValueFactory(param -> {
				if(null != param) {
					Book b = param.getValue();
					Author a = autModel.findById(b.getCategory_id());
					return new SimpleStringProperty(a.getName());
				}
				return null;
			});
			
			colBook.setCellValueFactory(new PropertyValueFactory<>("name"));
			colCategory.setCellValueFactory(param -> {
				if(param != null) {
					Book b = param.getValue();
					Category c = catModel.findById(b.getCategory_id());
					return new SimpleStringProperty(c.getCategory());
				}
				return null;
			});
			colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
			
			categories.valueProperty().addListener((a, b, c) -> search());
			authors.valueProperty().addListener((a, b, c) -> search());
			
			search();
			
			books.setOnMousePressed(event -> {
				if(event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					addToCart();
				}
			});
			
			cartContainer.getChildren().add(getCartView());
		} catch (BookAppException be) {
			MessageUtils.handle(be);
		}

	}

	private Node getCartView() {
		try {
			FXMLLoader loader = new FXMLLoader(CartView.class.getResource("CartView.fxml"));
			Node node = loader.load();
			
			myCart = loader.getController();
			myCart.setMode(Mode.Edit);
			myCart.setAddToCartListener(this::addToCart);
			
			return node;
		} catch (Exception e) {
			throw new BookAppException(e.getMessage(), false);
		}
	}
}
