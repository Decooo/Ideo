package com.jakub.controller;

import com.jakub.Util.TreeComparator;
import com.jakub.config.StageManager;
import com.jakub.entity.Tree;
import com.jakub.service.TreeService;
import com.jakub.view.FxmlView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
	@FXML
	private TreeView<String> treeView;

	Map<Integer, TreeItem<String>> itemById = new HashMap<>();
	Map<Integer, Integer> parents = new HashMap<>();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		viewItems();
	}

	public void viewItems() {
		List<Tree> items = treeService.findAll();
		Collections.sort(items, new TreeComparator());
		for (int i = 0; i < items.size(); i++) {
			itemById.put(items.get(i).getIdtree(), new TreeItem<>(items.get(i).getName_leaf()));
			parents.put(items.get(i).getIdtree(), items.get(i).getIdparent());
		}

		TreeItem<String> root = null;
		for (Map.Entry<Integer, TreeItem<String>> entry : itemById.entrySet()) {
			Integer key = entry.getKey();
			Integer parent = parents.get(key);
			if (parent.equals(key)) {
				root = entry.getValue();
			} else {
				TreeItem<String> parentItem = itemById.get(parent);
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
}
