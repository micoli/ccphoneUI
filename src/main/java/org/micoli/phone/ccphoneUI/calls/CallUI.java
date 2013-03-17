package org.micoli.phone.ccphoneUI.calls;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.micoli.phone.ccphoneUI.remote.VertX;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

public class CallUI extends Application implements Initializable {
	protected String fxmlDocument;
	String CallID;
	AnswerFrame answerFrame;
	InCallFrame inCallFrame;
	Pane root;
	Stage primaryStage;

	public void initialize(URL url, ResourceBundle rb) {
	}

	public CallUI(String CallID) {
		this.CallID = CallID;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		root = new Pane();
		Scene appScene = new Scene(root);
		primaryStage.setScene(appScene);
		appScene.setFill(Color.TRANSPARENT);
	}

	private void setAnswerFrame() {
		if (answerFrame == null) {
			answerFrame = new AnswerFrame(this);
		}
	}

	private void setInCallFrame() {
		if (inCallFrame == null) {
			inCallFrame = new InCallFrame(this);
		}
	}

	public void dispatchMessage(String eventName, Message<JsonObject> message) {
		if (eventName.equalsIgnoreCase("setSipRequest")) {
			// displayInCallFrame(message);
		} else if (eventName.equalsIgnoreCase("incomingCall")) {
			displayAnswerFrame(message);
		}
	}

	public void answerClicked(ActionEvent event) {
		VertX.publishDaemon("pickupAction", new JsonObject().putString("sipcallid", CallID));
		System.out.println("answer");
		primaryStage.close();
		displayInCallFrame();
	}

	public void declinedClicked(ActionEvent event) {
		VertX.publishDaemon("busyHereAction", new JsonObject().putString("sipcallid", CallID));
		System.out.println("declined");
		primaryStage.close();
	}


	public void displayAnswerFrame(final Message<JsonObject> message) {
		setAnswerFrame();
		Platform.runLater(new Runnable() {
			public void run() {
				answerFrame.show(message.body.getString("callId"), message.body.getString("fromValue"));
			}
		});
	}

	public void displayInCallFrame() {
		final String callId = this.CallID;
		setInCallFrame();
		Platform.runLater(new Runnable() {
			public void run() {
				inCallFrame.show(callId);
			}
		});
	}

	public void hangupClicked(ActionEvent event) {
		VertX.publishDaemon("hangupAction", new JsonObject().putString("sipcallid", CallID));
		System.out.println("hangup");
		primaryStage.close();
	}
}
