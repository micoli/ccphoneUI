package org.micoli.phone.ccphoneUI.calls;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.micoli.phone.ccphoneUI.remote.VertX;
import org.micoli.phone.ccphoneUI.tools.FXAutoScene;
import org.micoli.phone.ccphoneUI.tools.FxTools;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

public class CallUI extends Application implements Initializable {
	protected String fxmlDocument;
	String CallID;
	AnswerFrame answerFrame;
	InCallFrame inCallFrame;
	Pane root;
	Stage primaryStage;
	Stage anchorStage;
	private boolean isActive = true;
	private FXAutoScene currentScene = null;


	public void initialize(URL url, ResourceBundle rb) {
	}

	public CallUI(String CallID, Stage anchor) {
		this.CallID = CallID;
		this.anchorStage = anchor;
	}

	private void closeUI(){
		if(inCallFrame!=null){
			inCallFrame.stop();
		}
		primaryStage.close();
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		root = new Pane();
		Scene appScene = new Scene(root);
		primaryStage.setScene(appScene);
		appScene.setFill(Color.TRANSPARENT);
	}

	public void position(int n) {
		primaryStage.setX(anchorStage.getX() + anchorStage.getWidth() + (n * primaryStage.getWidth()));
		primaryStage.setY(anchorStage.getY());
	}

	public boolean isActive() {
		return isActive;
	}

	private void initAnswerFrame() {
		if (answerFrame == null) {
			answerFrame = new AnswerFrame(this);
		}
	}

	private void initInCallFrame() {
		if (inCallFrame == null) {
			inCallFrame = new InCallFrame(this);
		}
	}

	public FXAutoScene getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(FXAutoScene currentScene) {
		this.currentScene = currentScene;
	}

	public void dispatchMessage(String eventName, Message<JsonObject> message) {
		/*if (eventName.equalsIgnoreCase("ping")) {
			System.out.println(String.format("ALL %d",calls.size()));
		} else*/ if (eventName.equalsIgnoreCase("ringing")) {
				displayInCallFrame(message);
		} else if (eventName.equalsIgnoreCase("calleePickup")) {
			displayInCallFrame(message);
		} else if (eventName.equalsIgnoreCase("incomingCall")) {
			displayAnswerFrame(message);
		} else if (eventName.equalsIgnoreCase("remoteHangup")) {
			try {
				FxTools.runAndWait(new Runnable() {
					public void run() {
						System.out.println(String.format("current THREAD %d", Thread.currentThread().getId()));
						System.out.println("ask to quit");
						closeUI();
						Platform.exit();
					}
				});
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			isActive = false;
			//calls.remove(this.CallID);
		}
	}

	public void answerClicked(ActionEvent event) {
		VertX.publishDaemon("pickupAction", new JsonObject().putString("sipcallid", CallID));
		System.out.println("answer");
		closeUI();
		displayInCallFrame(null);
	}

	public void declinedClicked(ActionEvent event) {
		VertX.publishDaemon("busyHereAction", new JsonObject().putString("sipcallid", CallID));
		System.out.println("declined");
		closeUI();
	}


	public void displayAnswerFrame(final Message<JsonObject> message) {
		initAnswerFrame();
		Platform.runLater(new Runnable() {
			public void run() {
				System.out.println(String.format("current THREAD %d", Thread.currentThread().getId()));
				setCurrentScene(answerFrame.show(message.body.getString("callId"), message.body.getString("fromValue")));
			}
		});
	}

	public void displayInCallFrame(Message<JsonObject> message) {
		final String callId = message==null?this.CallID:message.body.getString("callId");
		initInCallFrame();
		Platform.runLater(new Runnable() {
			public void run() {
				System.out.println(String.format("current THREAD %d", Thread.currentThread().getId()));
				setCurrentScene(inCallFrame.show(callId));
			}
		});
	}

	public void hangupClicked(ActionEvent event) {
		VertX.publishDaemon("hangupAction", new JsonObject().putString("sipcallid", CallID));
		System.out.println("hangup");
		setCurrentScene(null);
		isActive = false;
		closeUI();
	}
}
