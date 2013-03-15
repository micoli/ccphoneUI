package org.micoli.phone.ccphoneUI;

import javafx.application.Platform;
import javafx.stage.Stage;

public class CallUI {

	public void displayAnswerFrame() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				AnswerFrame answerJFX = new AnswerFrame();
				Stage stage = new Stage();
				answerJFX.start(stage);
			}
		});
	}

}
