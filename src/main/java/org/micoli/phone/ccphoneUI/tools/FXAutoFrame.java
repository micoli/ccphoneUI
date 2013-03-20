package org.micoli.phone.ccphoneUI.tools;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class FXAutoFrame extends Application implements Initializable {
	protected String fxmlDocument;
	protected Stage primaryStage;

	public final Stage getPrimaryStage() {
		return primaryStage;
	}

	protected int constraintWindow;
	protected boolean draggable;

	public void initialize(URL url, ResourceBundle rb) {
	}

	public void show(Stage stage) {
		start(stage);
		show();
	}

	public void start(final Stage primaryStage) {
		this.primaryStage = primaryStage;
		if (fxmlDocument == null) {
			fxmlDocument = String.format("/%s.fxml", getClass().getCanonicalName().replace(".", "/"));
		}
		System.out.println("Loading " + fxmlDocument);
		FXMLLoader fxmlLoader;
		Pane root;
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource(fxmlDocument));
			fxmlLoader.setController(this);
			root = (Pane) fxmlLoader.load();
			FxTools.skinWindow(primaryStage, root, constraintWindow, draggable);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void show() {
		this.primaryStage.show();
	}

	public void close() {
		this.primaryStage.close();
	}
}