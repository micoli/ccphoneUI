package org.micoli.phone.ccphoneUI.calls;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.micoli.phone.ccphoneUI.Main;
import org.micoli.phone.ccphoneUI.remote.VertX;
import org.micoli.phone.ccphoneUI.tools.FXAutoScene;
import org.micoli.phone.ccphoneUI.tools.FxTools;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

// TODO: Auto-generated Javadoc
/**
 * The Class CallUI.
 */
public class CallUI extends Application implements Initializable {

	/** The fxml document. */
	protected String fxmlDocument;

	/** The Call id. */
	String CallID;

	/** The answer frame. */
	AnswerFrame answerFrame;

	/** The in call frame. */
	InCallFrame inCallFrame;

	/** The root. */
	Pane root;

	/** The primary stage. */
	Stage primaryStage;

	/** The anchor stage. */
	Stage anchorStage;

	/** The is active. */
	private boolean isActive = true;

	/** The current scene. */
	private FXAutoScene currentScene = null;


	/*
	 * (non-Javadoc)
	 *
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	public void initialize(URL url, ResourceBundle rb) {
	}

	/**
	 * Instantiates a new call ui.
	 *
	 * @param CallID
	 *            the call id
	 * @param anchor
	 *            the anchor
	 */
	public CallUI(String CallID, Stage anchor) {
		this.CallID = CallID;
		this.anchorStage = anchor;
	}

	/**
	 * Close ui.
	 */
	private void closeUI(){
		if(inCallFrame!=null){
			inCallFrame.stop();
		}
		primaryStage.close();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		root = new Pane();
		Scene appScene = new Scene(root);
		primaryStage.setScene(appScene);
		appScene.setFill(Color.TRANSPARENT);
		primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				Main.manageCalls();
			}
		});
	}

	/**
	 * Position.
	 *
	 * @param n
	 *            the n
	 */
	public void position(int n) {
		primaryStage.setX(anchorStage.getX() + anchorStage.getWidth() + (n * primaryStage.getWidth()));
		primaryStage.setY(anchorStage.getY());
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Inits the answer frame.
	 */
	private void initAnswerFrame() {
		if (answerFrame == null) {
			answerFrame = new AnswerFrame(this);
		}
	}

	/**
	 * Inits the in call frame.
	 */
	private void initInCallFrame() {
		if (inCallFrame == null) {
			inCallFrame = new InCallFrame(this);
		}
	}

	/**
	 * Gets the current scene.
	 *
	 * @return the current scene
	 */
	public FXAutoScene getCurrentScene() {
		return currentScene;
	}

	/**
	 * Sets the current scene.
	 *
	 * @param currentScene
	 *            the new current scene
	 */
	public void setCurrentScene(FXAutoScene currentScene) {
		this.currentScene = currentScene;
	}

	/**
	 * Dispatch message.
	 *
	 * @param eventName
	 *            the event name
	 * @param message
	 *            the message
	 */
	public void dispatchMessage(String eventName, Message<JsonObject> message) {
		if (eventName.equalsIgnoreCase("ping")) {
			System.out.println("ping");
		} else if (eventName.equalsIgnoreCase("ringing")) {
				displayInCallFrame(message);
		} else if (eventName.equalsIgnoreCase("calleePickup")) {
			displayInCallFrame(message);
		} else if (eventName.equalsIgnoreCase("incomingCall")) {
			displayAnswerFrame(message);
		} else if (eventName.equalsIgnoreCase("remoteHangup") || eventName.equalsIgnoreCase("close") ) {
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

	/**
	 * Answer clicked.
	 *
	 * @param event
	 *            the event
	 */
	public void answerClicked(ActionEvent event) {
		VertX.publishDaemon("pickup", new JsonObject().putString("sipcallid", CallID));
		System.out.println("answer");
		closeUI();
		displayInCallFrame(null);
	}

	/**
	 * Declined clicked.
	 *
	 * @param event
	 *            the event
	 */
	public void declinedClicked(ActionEvent event) {
		VertX.publishDaemon("busyHere", new JsonObject().putString("sipcallid", CallID));
		System.out.println("declined");
		closeUI();
	}


	/**
	 * Display answer frame.
	 *
	 * @param message
	 *            the message
	 */
	public void displayAnswerFrame(final Message<JsonObject> message) {
		initAnswerFrame();
		Platform.runLater(new Runnable() {
			public void run() {
				System.out.println(String.format("current THREAD %d %s", Thread.currentThread().getId(),message.body.toString()));
				setCurrentScene(answerFrame.show(message.body.getString("callId"), message.body.getString("fromValue")));
			}
		});
	}

	/**
	 * Display in call frame.
	 *
	 * @param message
	 *            the message
	 */
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

	/**
	 * Hangup clicked.
	 *
	 * @param event
	 *            the event
	 */
	public void hangupClicked(ActionEvent event) {
		VertX.publishDaemon("hangup", new JsonObject().putString("sipcallid", CallID));
		System.out.println("hangup");
		setCurrentScene(null);
		isActive = false;
		closeUI();
	}

	/**
	 * @return the primaryStage
	 */
	public final Stage getPrimaryStage() {
		return primaryStage;
	}
}
