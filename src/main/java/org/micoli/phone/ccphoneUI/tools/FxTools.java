package org.micoli.phone.ccphoneUI.tools;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.micoli.phone.ccphoneUI.DraggableWindow;

public class FxTools {
	public static void skinWindow(Stage primaryStage, Pane root, int Constraint) {
		Scene mainScene = new Scene(root);
		mainScene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(mainScene);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setOpacity(0.9f);
		DraggableWindow.makeDraggableTop(primaryStage, root, Constraint);
	}
}
