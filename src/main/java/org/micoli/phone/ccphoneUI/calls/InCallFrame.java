package org.micoli.phone.ccphoneUI.calls;

import java.util.Date;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import org.micoli.phone.ccphoneUI.Main;
import org.micoli.phone.ccphoneUI.tools.DraggableWindow;
import org.micoli.phone.ccphoneUI.tools.FXAutoScene;

// TODO: Auto-generated Javadoc
/**
 * The Class InCallFrame.
 */
public class InCallFrame extends FXAutoScene {

	/** The caller id. */
	@FXML
	private Label callerId;

	/** The timer. */
	@FXML
	private Label timer;

	/** The hangup button. */
	@FXML
	private Button hangupButton;

	/** The elapsed timer. */
	public Timeline elapsedTimer;

	/**
	 * Stop.
	 */
	public void stop(){
		elapsedTimer.stop();
	}

	/**
	 * Instantiates a new in call frame.
	 *
	 * @param me
	 *            the me
	 */
	public InCallFrame(final CallUI me) {
		super(me.primaryStage, DraggableWindow.CONSTRAINT_NONE, false);
		hangupButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				me.hangupClicked(event);
			}
		});
	}
	/**
	 * Show.
	 *
	 * @param callId
	 *            the call id
	 * @return the fX auto scene
	 */
	public FXAutoScene show(final String callId) {
		final long startDate = new Date().getTime();
		if(elapsedTimer!=null){
			elapsedTimer.stop();
		}
		elapsedTimer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				long dif = ((new Date()).getTime()-startDate)/1000;

				int hours = (int) dif / 3600;
				int remainder = (int) dif - hours * 3600;
				int mins = remainder / 60;
				remainder = remainder - mins * 60;
				int secs = remainder;

				timer.setText(String.format("%d:%02d:%02d",hours,mins,secs));
				// System.out.println(String.format("%s %d %s %d",callId,Thread.currentThread().getId(),Thread.currentThread().getName(),
				// dif/1000));
			}
		}));
		elapsedTimer.setCycleCount(Timeline.INDEFINITE);
		elapsedTimer.play();
		callerId.setText(callId);
		switchScene(primaryStage);
		primaryStage.show();
		Main.manageCalls();
		return this;
	}
}