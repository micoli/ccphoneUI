package org.micoli.phone.ccphoneUI.tools;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class FXAutoScene.
 */
public abstract class FXAutoScene {

	/** The fxml document. */
	protected String fxmlDocument;

	/** The draggable. */
	protected boolean draggable;

	/** The constraint window. */
	public int constraintWindow;

	/** The root. */
	public Pane root;

	/** The primary stage. */
	protected Stage primaryStage;

	/**
	 * Instantiates a new fX auto scene.
	 *
	 * @param primaryStage the primary stage
	 * @param constraintWindow the constraint window
	 * @param draggable the draggable
	 */
	public FXAutoScene(Stage primaryStage, int constraintWindow, boolean draggable) {
		this.primaryStage = primaryStage;
		this.constraintWindow = constraintWindow;
		this.draggable = draggable;
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

	/**
	 * Switch scene.
	 *
	 * @param primaryStage the primary stage
	 */
	public void switchScene(Stage primaryStage) {
		System.out.println(">>>>>>>>>>>>>>>>>" + fxmlDocument);
		//root.getTransforms().add( new Rotate(10,Rotate.Y_AXIS));
		primaryStage.getScene().setFill(Color.TRANSPARENT);
		primaryStage.getScene().setRoot(root);
		primaryStage.sizeToScene();
		FxTools.skinWindow(primaryStage, constraintWindow, draggable);
	}

}