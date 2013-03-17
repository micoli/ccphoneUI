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

import org.micoli.phone.ccphoneUI.tools.DraggableWindow;
import org.micoli.phone.ccphoneUI.tools.FXAutoScene;

public class InCallFrame extends FXAutoScene {
	@FXML
	private Label callerId;
	@FXML
	private Label timer;
	@FXML
	private Button hangupButton;
	public Timeline elapsedTimer;

	public void stop(){
		elapsedTimer.stop();
	}
	public InCallFrame(final CallUI me) {
		super(me.primaryStage, DraggableWindow.CONSTRAINT_NONE);
		hangupButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				me.hangupClicked(event);
			}
		});
	}

	public FXAutoScene show(final String callId) {
		final long startDate = new Date().getTime();
		if(elapsedTimer!=null){
			elapsedTimer.stop();
		}
		elapsedTimer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				long dif = (new Date()).getTime()-startDate;
				timer.setText(String.format("%d",dif/1000));
				System.out.println(String.format("%s %d %s %d",callId,Thread.currentThread().getId(),Thread.currentThread().getName(), dif/1000));
			}
		}));
		elapsedTimer.setCycleCount(Timeline.INDEFINITE);
		elapsedTimer.play();
		callerId.setText(callId);
		switchScene(primaryStage);
		primaryStage.show();
		return this;
	}
}