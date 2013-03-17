package org.micoli.phone.ccphoneUI.calls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.micoli.phone.ccphoneUI.tools.DraggableWindow;
import org.micoli.phone.ccphoneUI.tools.FXAutoScene;

public class InCallFrame extends FXAutoScene {
	@FXML
	private Label callerId;
	@FXML
	private Button hangupButton;

	public InCallFrame(final CallUI me) {
		super(me.primaryStage, DraggableWindow.CONSTRAINT_NONE);
		hangupButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				me.hangupClicked(event);
			}
		});
	}

	public void show(String callId) {
		callerId.setText(callId);

		switchScene(primaryStage);
		primaryStage.show();
	}
}