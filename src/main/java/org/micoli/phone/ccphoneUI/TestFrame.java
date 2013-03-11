package org.micoli.phone.ccphoneUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class TestFrame extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {
		primaryStage.setTitle("Hello World!");
		/*Button btn = new Button();
		btn.setText("Say 'Hello World'");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});*/

		// StackPane root = new StackPane();
		//root.getChildren().add(btn);
		Pane root;
		try {
			root = (Pane) FXMLLoader.load(getClass().getResource("/TestFrame.fxml"));
			// root.setId("#rootPane");
			// mainScene.getStylesheets().add(this.getClass().getResource("/roundedWindow.css").toExternalForm());
			Scene mainScene = new Scene(root);
			mainScene.setFill(Color.TRANSPARENT);
			primaryStage.setScene(mainScene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
			primaryStage.setOpacity(0.9f);
			DraggableWindow.makeDraggableTop(primaryStage, root, DraggableWindow.CONSTRAINT_TOP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}