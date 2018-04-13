package com.jakub.Util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by jakub on 13.04.2018.
 */
public class ErrorAlerts {
	public static void inncorectName() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Błąd");
		alert.setHeaderText("Nazwa nie może być pusta");
		alert.getButtonTypes().setAll(ButtonType.OK);
		alert.showAndWait();
	}
}
