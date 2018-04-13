package com.jakub.controller;

import com.jakub.Util.ErrorAlerts;
import com.jakub.entity.Tree;
import com.jakub.service.TreeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by jakub on 12.04.2018.
 */
@Controller
public class AddController implements Initializable {
	@Autowired
	TreeService treeService;
	@Autowired
	TreeController treeController;
	@FXML
	private TextField nameTextField;
	@FXML
	private ComboBox parentComboBox;
	@FXML
	private Button backButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		parentComboBox.setItems(doListParent());
		parentComboBox.getSelectionModel().select(0);
	}

	private ObservableList doListParent() {
		ObservableList<String> listParent = FXCollections.observableArrayList();
		List<Tree> list = treeService.findAll();
		for (int i = 0; i < list.size(); i++) {
			listParent.add(list.get(i).getName_leaf());
		}
		return listParent;
	}

	@FXML
	public void backAction(ActionEvent event) {
		Stage stage = (Stage) backButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void addAction(ActionEvent event) {
		List<Tree> list = treeService.findAll();
		if (nameTextField.getText().length() == 0) {
			ErrorAlerts.inncorectName();
		} else {
			treeService.add(list.get(parentComboBox.getSelectionModel().getSelectedIndex()).getIdtree(), nameTextField.getText());
			addedNewNodeAlert();
		}
	}

	private void addedNewNodeAlert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Dodano pomyślnie");
		alert.setHeaderText("Pomyślnie dodano nowy wezeł");
		alert.getButtonTypes().setAll(ButtonType.OK);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			treeController.viewItems();
			Stage stage = (Stage) nameTextField.getScene().getWindow();
			stage.close();
		}
	}

}
