package org.micoli.phone.ccphoneUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AnswerFrame extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {
		Pane root;
		try {
			root = (Pane) FXMLLoader.load(getClass().getResource("/AnswerFrame.fxml"));
			Scene mainScene = new Scene(root);
			mainScene.setFill(Color.TRANSPARENT);
			primaryStage.setScene(mainScene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
			primaryStage.setOpacity(0.9f);
			DraggableWindow.makeDraggableTop(primaryStage, root, DraggableWindow.CONSTRAINT_NONE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}