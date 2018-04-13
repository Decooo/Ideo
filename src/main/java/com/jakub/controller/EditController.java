package com.jakub.controller;

import com.jakub.Util.ErrorAlerts;
import com.jakub.config.StageManager;
import com.jakub.entity.Tree;
import com.jakub.service.TreeService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by jakub on 13.04.2018.
 */
@Controller
public class EditController implements Initializable {
	@Autowired
	TreeService treeService;
	@Lazy
	@Autowired
	StageManager stageManager;
	@Autowired
	TreeController treeController;
	@FXML
	private TextField nameTextField;
	@FXML
	private ComboBox parentComboBox;
	@FXML
	private Button backButton;

	private int indexCurrentParent;

	List<Integer> listIdTree = new ArrayList<>();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		parentComboBox.setItems(doListParent());
		setTreeProperties();
	}

	private void setTreeProperties() {
		nameTextField.setText(TreeController.getTree().get().getName_leaf());
		parentComboBox.getSelectionModel().select(indexCurrentParent);
	}

	private ObservableList doListParent() {
		ObservableList<String> listParent = FXCollections.observableArrayList();
		Tree tree = TreeController.getTree().get();
		listIdTree.clear();
		List<Tree> list = treeService.findAll();
		for (int i = 0; i < list.size(); i++) {
			listParent.add(list.get(i).getName_leaf());
			listIdTree.add(list.get(i).getIdtree());
			if(tree.getIdparent()==list.get(i).getIdtree()){
				indexCurrentParent=i;
			}
		}
		return listParent;
	}

	@FXML
	public void backAction(ActionEvent event) {
		Stage stage = (Stage) backButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void editAction(ActionEvent event) {
		List<Tree> list = treeService.findAll();
		if (nameTextField.getText().length() == 0) {
			ErrorAlerts.inncorectName();
		} else {
			if(parentComboBox.getSelectionModel().getSelectedIndex()==indexCurrentParent){
				treeService.add(indexCurrentParent, nameTextField.getText());
				updatedNodeAlert();
			}else{
				Tree tree = TreeController.getTree().get();
				List<Tree> lists = treeService.findAll();
				Boolean isLeaf = true;
				for(int i=0;i<lists.size();i++){
					if(listIdTree.get(parentComboBox.getSelectionModel().getSelectedIndex())==list.get(i).getIdparent()){
						System.out.println(listIdTree.get(parentComboBox.getSelectionModel().getSelectedIndex()));
						isLeaf=false;
					}
				}
				if(!isLeaf){
					errorAlert();
				}else{
					treeService.moveNode(tree.getIdtree(),listIdTree.get(parentComboBox.getSelectionModel().getSelectedIndex()),nameTextField.getText());
					updatedNodeAlert();
				}
			}
		}
	}

	private void errorAlert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Błąd");
		alert.setHeaderText("Przenoszenie wezłow nie zostalo zaimplementowane, mozna przenosić tylko liście");
		alert.getButtonTypes().setAll(ButtonType.OK);
		alert.showAndWait();
	}

	private void updatedNodeAlert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Zaaktualizowano");
		alert.setHeaderText("Zaaktualizowano poprawnie");
		alert.getButtonTypes().setAll(ButtonType.OK);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			treeController.viewItems();
			Stage stage = (Stage) nameTextField.getScene().getWindow();
			stage.close();
		}
	}
}
