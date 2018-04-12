package com.jakub.controller;

import com.jakub.Util.TreeComparator;
import com.jakub.entity.Tree;
import com.jakub.service.TreeService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.*;

/**
 * Created by jakub on 12.04.2018.
 */
@Controller
public class TreeController implements Initializable {
	@Autowired
	TreeService treeService;

	@FXML
	private TreeView<String> treeView;
	Map<Integer, TreeItem<String>> itemById = new HashMap<>();
	Map<Integer, Integer> parents = new HashMap<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		viewItems();
	}

	private void viewItems() {
		List<Tree> items = treeService.findAll();
		Collections.sort(items,new TreeComparator());
		for (int i = 0; i < items.size(); i++) {
			itemById.put(items.get(i).getIdtree(),new TreeItem<>(items.get(i).getName()));
			parents.put(items.get(i).getIdtree(),items.get(i).getIdparent());
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


}
