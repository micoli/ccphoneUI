package org.micoli.phone.ccphoneUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.micoli.phone.ccphoneUI.remote.VertX;
import org.micoli.phone.ccphoneUI.tools.Delta;
import org.micoli.phone.ccphoneUI.tools.DraggableWindow;
import org.micoli.phone.ccphoneUI.tools.FXAutoFrame;
import org.vertx.java.core.json.JsonObject;

// TODO: Auto-generated Javadoc
/**
 * The Class MainTop.
 */
public class MainTop extends FXAutoFrame {

	/** The number. */
	@FXML
	public TextField number;

	/** The microphone. */
	@FXML
	public ToggleButton microphone;

	/**
	 * Instantiates a new main top.
	 */
	public MainTop() {
		constraintWindow = DraggableWindow.CONSTRAINT_TOP;
		draggable = false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.micoli.phone.ccphoneUI.tools.FXAutoFrame#show(javafx.stage.Stage)
	 */
	public void show(Stage stage) {
		start(stage);

		makeDraggableTop(stage, stage.getScene().getRoot());
		number.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				VertX.publishDaemon("callAction", new JsonObject().putString("uri", number.getText()));
			}
		});
		microphone.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("click");
			}
		});
		show();
	}

	/**
	 * Make draggable top.
	 *
	 * @param stage
	 *            the stage
	 * @param byNode
	 *            the by node
	 */
	public static void makeDraggableTop(final Stage stage, final Node byNode) {
		final Delta dragDelta = new Delta();
		stage.setY(25);
		byNode.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				// record a delta distance for the drag and drop operation.
				dragDelta.x = stage.getX() - mouseEvent.getScreenX();
				dragDelta.y = stage.getY() - mouseEvent.getScreenY();
				byNode.setCursor(Cursor.MOVE);
			}
		});
		byNode.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				byNode.setCursor(Cursor.HAND);
			}
		});
		byNode.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				stage.setX(mouseEvent.getScreenX() + dragDelta.x);
				stage.setY(25);
				Main.manageCalls();
			}
		});
		byNode.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				if (!mouseEvent.isPrimaryButtonDown()) {
					byNode.setCursor(Cursor.HAND);
				}
			}
		});
		byNode.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				if (!mouseEvent.isPrimaryButtonDown()) {
					byNode.setCursor(Cursor.DEFAULT);
				}
			}
		});
	}
}