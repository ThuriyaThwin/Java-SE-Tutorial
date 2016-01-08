package com.jdc.book.app.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ResourceBundle;

import com.jdc.book.app.BookAppException;
import com.jdc.book.app.controllers.utils.AuthorList;
import com.jdc.book.app.controllers.utils.CategoryList;
import com.jdc.book.app.controllers.utils.ImageUtils;
import com.jdc.book.app.controllers.utils.MessageUtils;
import com.jdc.book.app.controllers.utils.Navigator;
import com.jdc.book.app.controllers.utils.Transportable;
import com.jdc.book.app.models.BookModel;
import com.jdc.book.app.models.imp.BookModelImp;
import com.jdc.book.db.entity.Author;
import com.jdc.book.db.entity.Book;
import com.jdc.book.db.entity.Category;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser.ExtensionFilter;

public class AddBook implements Initializable{

	@FXML
	private TextField category;
	@FXML
	private TextField name;
	@FXML
	private TextField price;
	@FXML
	private DatePicker issueDate;
	@FXML
	private TextArea description;
	@FXML
	private TextField authorName;
	@FXML
	private ImageView photo;
	@FXML
	private TextArea biography;
	@FXML
	private Button upload;
	
	private String photoName;
	
	private Category selectedCategory;
	private Author selectedAuthor;
	
	private Transportable<Category> categoryTransporter;
	private Transportable<Author> authorTransporter;
	
	private BookModel model;

	public void clear() {
		try {
			category.clear();
			category.setEditable(true);
			name.clear();
			price.clear();
			issueDate.setValue(null);
			description.clear();
			authorName.clear();
			authorName.setEditable(true);
			biography.clear();
			biography.setEditable(true);
			upload.setDisable(false);
			
			if(selectedAuthor == null && photoName != null) {
				ImageUtils.delete(photoName);
				photoName = null;
			}
			
			photo.setImage(ImageUtils.getImage(photoName));
			selectedAuthor = null;
			selectedCategory = null;			
		} catch (BookAppException ex) {
			MessageUtils.handle(ex);
		}
	}
	
	public void save() {
		
		try {
			Category category = getCataegory();
			Author author = getAuthor();
			Book book = getBook();
			
			model.create(book, category, author);
			
			Navigator.navigate("Book List");
		} catch (BookAppException ex) {
			MessageUtils.handle(ex);
		}
		
	}
	
	private Book getBook() {
		Book b = new Book();
		
		if(name.getText().isEmpty()) {
			throw new BookAppException("Please enter book name!!", true);
		}
		
		b.setName(name.getText());
		
		try {
			b.setPrice(Double.valueOf(price.getText()));
		} catch (Exception e) {
			throw new BookAppException("Please enter the right value to price!!", true);
		}
		
		b.setIssue_date(Date.from(issueDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		b.setShort_description(description.getText());
		return b;
	}

	private Author getAuthor() {
		if(null == selectedAuthor) {
			Author a = new Author();
			if(authorName.getText().isEmpty()) {
				throw new BookAppException("Please enter Author Name!", true);
			}
			
			a.setName(authorName.getText());
			a.setPhoto(photoName);
			a.setBiography(biography.getText());
			return a;
		}
		return selectedAuthor;
	}

	private Category getCataegory() {
		
		if(null == selectedCategory) {
			Category c = new Category();
			if(category.getText().isEmpty()) {
				throw new BookAppException("Please Enter Category Name!!", true);
			}
			c.setCategory(category.getText());
			return c;
		}
		
		return selectedCategory;
	}

	public void searchCategory() {
		selectedCategory = null;
		categoryTransporter = CategoryList.showView(this::handleOnCloseCategory);
	}
	
	public void searchAuthor() {
		selectedAuthor = null;
		authorTransporter = AuthorList.showView(this::handleOnCloseAuthor);
	}
	
	public void uploadPhoto() {
		try {
			try {
				FileChooser fc = new FileChooser();
				fc.setTitle("Select Author's Photo");
				fc.getExtensionFilters().add(new ExtensionFilter("Image", "*.gif", "*.png", "*.jpg"));
				
				File file = fc.showOpenDialog(photo.getScene().getWindow());
				
				if(null != file) {
					Image image = new Image(new FileInputStream(file));
					photo.setImage(image);
					// save image
					photoName = ImageUtils.savePhoto(photo.snapshot(null, null));
				}
				
			} catch(Exception e) {
				e.printStackTrace();
				throw new BookAppException(e.getMessage(), false);
			}
		} catch (BookAppException be) {
			MessageUtils.handle(be);
		}		
	}
	
	private void handleOnCloseCategory(WindowEvent e) {
		selectedCategory = categoryTransporter.transport();
		if(null != selectedCategory) {
			category.setText(selectedCategory.getCategory());
			category.setEditable(false);
		}
	}
	
	private void handleOnCloseAuthor(WindowEvent e) {
		try {
			selectedAuthor = authorTransporter.transport();
			if(null != selectedAuthor) {
				authorName.setText(selectedAuthor.getName());
				authorName.setEditable(false);
				photo.setImage(ImageUtils.getImage(selectedAuthor.getPhoto()));
				upload.setDisable(true);
				biography.setText(selectedAuthor.getBiography());
				biography.setEditable(false);
			}
		} catch (BookAppException ex) {
			MessageUtils.handle(ex);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = new BookModelImp();
	}
}
