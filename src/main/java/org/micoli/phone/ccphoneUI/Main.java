package org.micoli.phone.ccphoneUI;

import java.awt.SystemTray;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import javax.swing.SwingUtilities;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

public class Main {
	static String topicAddress = "calls";
	static Vertx vertx;
	static HashMap<String,CallUI> calls;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initAndShowGUI();
			}
		});
		Thread thread = new Thread(new Runnable() {
			public void run() {
				vertxClient();
			}
		});
		thread.start();
	}

	private static void initAndShowGUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new JFXPanel();
				Platform.runLater(new Runnable() {
					public void run() {
						TestFrame mainJFX = new TestFrame();
						Stage stage1 = new Stage();
						mainJFX.start(stage1);
					}
				});
			}
		});
		HashMap<String,CallUI> calls = new HashMap<String,CallUI>();

		SystemTray st;
		st = java.awt.SystemTray.isSupported() ? java.awt.SystemTray
				.getSystemTray() : null;
		if (st != null && st.getTrayIcons().length == 0) {
			try {
				URL url = new URL(
						"http://www.veryicon.com/icon/16/System/Palm/Settings%20Phone.png");
				final java.awt.Image image = Toolkit.getDefaultToolkit()
						.getImage(url);
				final java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);
				// UGateKeeper.DEFAULT.
				trayIcon.setToolTip("UGate");
				st.add(trayIcon);
			} catch (final java.io.IOException e) {
				System.out.println("Unable to add system tray icons");
			} catch (java.awt.AWTException e) {
				System.out.println("Unable to add system tray icons");
			}
		}
	}

	private static CallUI getCallUI(String callId){
		CallUI callUI;
		if(calls.containsKey(callId)){
			callUI= new CallUI();
			calls.put(callId,callUI);
		}else{
			callUI = calls.get(callId);
		}
		return callUI;
	}

	private static void vertxClient() {
		vertx = Vertx.newVertx(2551, "localhost");
		System.out.println("start vertx client");

		Handler<Message<JsonObject>> myHandler = new Handler<Message<JsonObject>>() {
			public void handle(Message<JsonObject> message) {
				String callId = message.body.getString("callid") ;
				String eventName = message.body.getString("eventName");
				CallUI callUI = null;;

				System.out.println("Client event due to registration : ["
						+ message.body.getString("text") + "]\n"
						+ message.body.toString());

				System.out.println("------");
				if(callId != null){
					System.out.println("addddddd");
					callUI = getCallUI(callId);
				}
				if(eventName.equalsIgnoreCase("setSipRequest")){
					// Ext.getCmp('txtsipcallid').setValue(msg.callId);
				}else if(eventName.equalsIgnoreCase("incomingCall")){
					callUI.displayAnswerFrame();
					// Ext.getCmp('txtsipcallid').setValue(msg.callId);
					// Ext.getCmp('txtsipcallidanswer').setValue(msg.callId)
					// Ext.getCmp('txtsipcallidbusy').setValue(msg.callId)
				}
			}
		};

		vertx.eventBus().registerHandler(topicAddress, myHandler);

		Thread thread = new Thread(new Runnable() {
			public void run() {
			}
		});
		thread.start();

		while (true) {
			try {
				Thread.sleep(10000);
				vertx.eventBus().publish(
						topicAddress,
						new JsonObject()
								.putString("type", "publish")
								.putString("adress", topicAddress)
								.putObject(
										"body",
										new JsonObject().putString(
												"text",
												"ping "
														+ (new Date())
																.toString())));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}