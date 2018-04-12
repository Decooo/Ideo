package com.jakub.view;

import java.util.ResourceBundle;

/**
 * Created by jakub on 12.04.2018.
 */
public enum FxmlView {
	TREE{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("tree.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Tree.fxml";
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
