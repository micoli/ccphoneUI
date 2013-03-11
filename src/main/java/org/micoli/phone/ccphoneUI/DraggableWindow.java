package org.micoli.phone.ccphoneUI;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DraggableWindow {
	public static int CONSTRAINT_NONE = 0;
	public static int CONSTRAINT_TOP = 1;

	public static void makeDraggableTop(final Stage stage, final Node byNode, final int constraint) {
		final Delta dragDelta = new Delta();
		if (constraint == CONSTRAINT_TOP) {
			stage.setY(5);
		}
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
				if (constraint == CONSTRAINT_TOP) {
					stage.setY(5);
				}
				if (constraint == CONSTRAINT_NONE) {
					stage.setY(mouseEvent.getScreenY() + dragDelta.y);
				}
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
