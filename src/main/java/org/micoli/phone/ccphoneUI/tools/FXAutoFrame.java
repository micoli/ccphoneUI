package org.micoli.phone.ccphoneUI.tools;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class FXAutoFrame.
 */
public abstract class FXAutoFrame extends Application implements Initializable {

	/** The fxml document. */
	protected String fxmlDocument;

	/** The primary stage. */
	protected Stage primaryStage;

	/**
	 * Gets the primary stage.
	 *
	 * @return the primary stage
	 */
	public final Stage getPrimaryStage() {
		return primaryStage;
	}

	/** The constraint window. */
	protected int constraintWindow;

	/** The draggable. */
	protected boolean draggable;

	/*
	 *
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	public void initialize(URL url, ResourceBundle rb) {
	}

	/**
	 * Show.
	 *
	 * @param stage
	 *            the stage
	 */
	public void show(Stage stage) {
		start(stage);
		show();
	}

	/*
	 *
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
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
			//getAlwaysOnTop(root);
			FxTools.skinWindow(primaryStage, root, constraintWindow, draggable);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Popup getAlwaysOnTop(Node root) {
		Popup xmlWindow = PopupBuilder.create().content(root).width(600).height(400).x(500.0).y(0.0).build();
		return xmlWindow;
	}
	/**
	 * Show.
	 */
	public void show() {
		this.primaryStage.show();
	}

	/**
	 * Close.
	 */
	public void close() {
		this.primaryStage.close();
	}
}