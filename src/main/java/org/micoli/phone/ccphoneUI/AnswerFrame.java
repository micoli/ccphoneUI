package org.micoli.phone.ccphoneUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.micoli.phone.ccphoneUI.tools.FxTools;

public class AnswerFrame extends Application implements Initializable {
	@FXML
	private Label callerNum;
	@FXML
	private Label callerId;
	@FXML
	private Button answerButton;
	@FXML
	private Button declineButton;

	public static void main(String[] args) {
		launch(args);
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@Override
	public void start(final Stage primaryStage) {
		FXMLLoader fxmlLoader;
		Pane root;
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("/AnswerFrame.fxml"));
			fxmlLoader.setController(this);
			root = (Pane) fxmlLoader.load();
			FxTools.skinWindow(primaryStage, root, DraggableWindow.CONSTRAINT_NONE);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCallerNumText(String caption) {
		callerNum.setText(caption);
	}

	public void setCallerIdText(String caption) {
		callerId.setText(caption);
	}

	public void setAnswerButtonHandler(EventHandler<ActionEvent> eventHandler) {
		answerButton.setOnAction(eventHandler);

	}
	public void setDeclineButtonHandler(EventHandler<ActionEvent> eventHandler) {
		declineButton.setOnAction(eventHandler);

	}
}