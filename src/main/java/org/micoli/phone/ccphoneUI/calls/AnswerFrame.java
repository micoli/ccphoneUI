package org.micoli.phone.ccphoneUI.calls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.micoli.phone.ccphoneUI.Main;
import org.micoli.phone.ccphoneUI.tools.DraggableWindow;
import org.micoli.phone.ccphoneUI.tools.FXAutoScene;

// TODO: Auto-generated Javadoc
/**
 * The Class AnswerFrame.
 */
public class AnswerFrame extends FXAutoScene {
	// @FXML
	// private Label callerNum;
	/** The caller id. */
	@FXML
	private Label callerId;

	/** The answer button. */
	@FXML
	private Button answerButton;

	/** The decline button. */
	@FXML
	private Button declineButton;

	/**
	 * Instantiates a new answer frame.
	 * 
	 * @param me
	 *            the me
	 */
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

	/**
	 * Show.
	 * 
	 * @param callId
	 *            the call id
	 * @param fromValue
	 *            the from value
	 * @return the fX auto scene
	 */
	public FXAutoScene show(String callId, String fromValue) {
		callerId.setText(callId);
		// callerNum.setText(fromValue);

		switchScene(primaryStage);
		primaryStage.show();
		Main.manageCalls();
		return this;
	}

	/**
	 * Disable buttons.
	 */
	public void disableButtons() {
		answerButton.setDisable(true);
		declineButton.setDisable(true);
	}
}