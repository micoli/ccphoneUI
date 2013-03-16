package org.micoli.phone.ccphoneUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

public class CallUI {
	String CallID;

	public CallUI(String callID) {
		this.CallID = callID;
	}

	public void displayAnswerFrame(final Message<JsonObject> message) {
		Platform.runLater(new Runnable() {
			public void run() {
				AnswerFrame answerFrame = new AnswerFrame();
				final Stage stage = new Stage();
				answerFrame.start(stage);

				answerFrame.setCallerNumText(message.body.getString("callId"));
				answerFrame.setCallerIdText(message.body.getString("fromValue"));

				answerFrame.setAnswerButtonHandler(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						System.out.println("answer");
						stage.close();
					}
				});

				answerFrame.setDeclineButtonHandler(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						System.out.println("decline");
						stage.close();
					}
				});
			}
		});
	}
}
