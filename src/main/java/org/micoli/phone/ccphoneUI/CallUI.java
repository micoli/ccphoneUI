package org.micoli.phone.ccphoneUI;

import javafx.application.Platform;
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
				AnswerFrame answerJFX = new AnswerFrame();
				Stage stage = new Stage();
				answerJFX.start(stage);
				answerJFX.callerNum.setText(message.body.getString("callId"));
				answerJFX.callerId.setText(message.body.getString("fromValue"));
			}
		});
	}
}
