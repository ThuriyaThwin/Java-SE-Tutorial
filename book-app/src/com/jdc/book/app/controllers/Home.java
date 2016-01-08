package com.jdc.book.app.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jdc.book.app.models.Summary;
import com.jdc.book.app.models.SummaryModel;
import com.jdc.book.app.models.SummaryModel.Type;
import com.jdc.book.app.models.imp.SummaryModelImp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

public class Home implements Initializable{
	
	@FXML
	private PieChart pieCategory;
	@FXML
	private PieChart pieAuthor;
 	
	private SummaryModel model;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = new SummaryModelImp();
		List<Summary> catList = model.getSummary(Type.Category);
		List<Summary> authList = model.getSummary(Type.Author);
		
		pieCategory.getData().addAll(getPieData(catList));
		pieAuthor.getData().addAll(getPieData(authList));
		
	}
	
	private List<Data> getPieData(List<Summary> list) {
		if(null != list) {
			return list.stream().map(a -> a.data()).collect(Collectors.toList());
		}
		return null;
	}
	
}
