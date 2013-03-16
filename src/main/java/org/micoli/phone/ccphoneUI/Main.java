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

import org.micoli.phone.ccphoneUI.remote.VertX;
import org.vertx.java.core.json.JsonObject;

public class Main {
	static HashMap<String,CallUI> calls;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initAndShowGUI();
			}
		});
		Thread thread = new Thread(new Runnable() {
			public void run() {
				calls = new HashMap<String, CallUI>();
				VertX.init();
				VertX.run();
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

		SystemTray st;
		st = java.awt.SystemTray.isSupported() ? java.awt.SystemTray
				.getSystemTray() : null;
		if (st != null && st.getTrayIcons().length == 0) {
			try {
				URL url = new URL("http://www.veryicon.com/icon/16/System/Palm/Settings%20Phone.png");
				final java.awt.Image image = Toolkit.getDefaultToolkit().getImage(url);
				final java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);
				trayIcon.setToolTip("UGate");
				st.add(trayIcon);
			} catch (final java.io.IOException e) {
				System.out.println("Unable to add system tray icons");
			} catch (java.awt.AWTException e) {
				System.out.println("Unable to add system tray icons");
			}
		}
	}

	public static CallUI getCallUI(String callId) {
		CallUI callUI;
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		if(calls.containsKey(callId)){
			System.out.println("get one");
			callUI = calls.get(callId);
		} else {
			System.out.println("create new");
			callUI = new CallUI(callId);
			calls.put(callId,callUI);
		}
		return callUI;
	}

	private static void vertxClient() {

		Thread thread = new Thread(new Runnable() {
			public void run() {
			}
		});
		thread.start();

		while (true) {
			try {
				Thread.sleep(10000);
				VertX.eb.publish(VertX.topicAddress,
						new JsonObject()
.putString("type", "publish").putString("adress", VertX.topicAddress).putObject(
										"body",
										new JsonObject().putString(
"text", "ping " + (new Date()).toString())));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}