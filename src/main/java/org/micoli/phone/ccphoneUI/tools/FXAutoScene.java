package org.micoli.phone.ccphoneUI.tools;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class FXAutoScene {
	protected String fxmlDocument;
	public int constraintWindow;
	public Pane root;
	protected Stage primaryStage;

	public FXAutoScene(Stage primaryStage, int constraintWindow) {
		this.primaryStage = primaryStage;
		this.constraintWindow = constraintWindow;
		if (fxmlDocument == null) {
			fxmlDocument = String.format("/%s.fxml", getClass().getCanonicalName().replace(".", "/"));
		}
		System.out.println("Loading " + fxmlDocument);
		FXMLLoader fxmlLoader;
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource(fxmlDocument));
			fxmlLoader.setController(this);
			root = (Pane) fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void switchScene(Stage primaryStage) {
		primaryStage.getScene().setFill(Color.TRANSPARENT);
		primaryStage.getScene().setRoot(root);
		primaryStage.sizeToScene();
		FxTools.skinWindow(primaryStage, constraintWindow);
	}

}