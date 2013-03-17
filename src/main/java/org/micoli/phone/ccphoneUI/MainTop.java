package org.micoli.phone.ccphoneUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.micoli.phone.ccphoneUI.remote.VertX;
import org.micoli.phone.ccphoneUI.tools.DraggableWindow;
import org.micoli.phone.ccphoneUI.tools.FXAutoFrame;
import org.vertx.java.core.json.JsonObject;

public class MainTop extends FXAutoFrame {
	@FXML
	public TextField number;

	public MainTop() {
		constraintWindow = DraggableWindow.CONSTRAINT_TOP;
	}

	public void show(Stage stage) {
		start(stage);
		number.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				VertX.publishDaemon("callAction", new JsonObject().putString("uri", number.getText()));
			}
		});
		show();
	}

}