package com.jdc.cinema.reception;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public interface TotalTableHelper {

	TableView<SeatViewAdapter> table();
	Label total();
}
