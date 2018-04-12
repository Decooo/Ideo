package com.jakub.controller;

import com.jakub.Util.TreeComparator;
import com.jakub.config.StageManager;
import com.jakub.entity.Tree;
import com.jakub.service.TreeService;
import com.jakub.view.FxmlView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

/**
 * Created by jakub on 12.04.2018.
 */
@Controller
public class TreeController implements Initializable {
	@Autowired
	TreeService treeService;
	@Lazy
	@Autowired
	StageManager stageManager;
	Map<Integer, TreeItem<Tree>> itemById = new HashMap<>();
	Map<Integer, Integer> parents = new HashMap<>();
	@FXML
	private TreeView<Tree> treeView;
	private TreeItem<Tree> root;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		viewItems();
	}

	public void viewItems() {
		itemById.clear();
		parents.clear();
		List<Tree> items = treeService.findAll();
		Collections.sort(items, new TreeComparator());
		for (int i = 0; i < items.size(); i++) {
			itemById.put(items.get(i).getIdtree(), new TreeItem<>(items.get(i)));
			parents.put(items.get(i).getIdtree(), items.get(i).getIdparent());
		}

		root = null;
		for (Map.Entry<Integer, TreeItem<Tree>> entry : itemById.entrySet()) {
			Integer key = entry.getKey();
			Integer parent = parents.get(key);
			if (parent.equals(key)) {
				root = entry.getValue();
			} else {
				TreeItem<Tree> parentItem = itemById.get(parent);
				if (parentItem == null) {
					root = entry.getValue();
				} else {
					parentItem.getChildren().add(entry.getValue());
				}
			}
		}
		treeView.setRoot(root);
	}

	@FXML
	public void exitAction(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	public void addAction(ActionEvent event) throws IOException {
		stageManager.switchSceneAndWait(FxmlView.ADD);
	}

	@FXML
	public void expandWholeAction(ActionEvent event) {
		displayTreeView(root, true);
	}

	@FXML
	public void hideWholeAction(ActionEvent event) {
		displayTreeView(root, false);
	}

	private void displayTreeView(TreeItem<?> item, boolean visibility) {
		if (item != null && !item.isLeaf()) {
			item.setExpanded(visibility);
			for (TreeItem<?> child : item.getChildren()) {
				displayTreeView(child, visibility);
			}
		}
	}

	@FXML
	public void deleteAction(ActionEvent event) {
		TreeItem<Tree> node = treeView.getSelectionModel().getSelectedItem();
		alertDeleteNode(node.getValue().getIdtree());
		viewItems();
		displayTreeView(root,true);
	}


	private void alertDeleteNode(int nodes) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie usuwania");
		alert.setHeaderText(null);
		alert.setContentText("Czy napewno chcesz usunąć wybrany wezeł?");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) treeService.delete(nodes);
	}

}

