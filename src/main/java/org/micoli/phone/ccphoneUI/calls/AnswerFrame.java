package org.micoli.phone.ccphoneUI.calls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.micoli.phone.ccphoneUI.Main;
import org.micoli.phone.ccphoneUI.tools.DraggableWindow;
import org.micoli.phone.ccphoneUI.tools.FXAutoScene;

public class AnswerFrame extends FXAutoScene {
	// @FXML
	// private Label callerNum;
	@FXML
	private Label callerId;
	@FXML
	private Button answerButton;
	@FXML
	private Button declineButton;

	public AnswerFrame(final CallUI me) {
		super(me.primaryStage, DraggableWindow.CONSTRAINT_NONE, false);
		answerButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				me.answerClicked(event);
			}
		});
		declineButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				me.declinedClicked(event);
			}
		});
	}

	public FXAutoScene show(String callId, String fromValue) {
		callerId.setText(callId);
		// callerNum.setText(fromValue);

		switchScene(primaryStage);
		primaryStage.show();
		Main.manageCalls();
		return this;
	}

	public void disableButtons() {
		answerButton.setDisable(true);
		declineButton.setDisable(true);
	}
}