package org.micoli.phone.ccphoneUI;

import java.awt.SystemTray;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import javax.swing.SwingUtilities;

import org.micoli.phone.ccphoneUI.calls.CallUI;
import org.micoli.phone.ccphoneUI.remote.VertX;
import org.micoli.phone.ccphoneUI.tools.FxTools;
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
						MainTop mainTop = new MainTop();
						mainTop.show(new Stage());
					}
				});
			}
		});

		SystemTray st;
		st = java.awt.SystemTray.isSupported() ? java.awt.SystemTray.getSystemTray() : null;
		if (st != null && st.getTrayIcons().length == 0) {
			try {
				URL url = Main.class.getResource("/org/micoli/phone/phone-icon-blue.png");
				//final java.awt.Image image = Toolkit.getDefaultToolkit().getImage(url);
				final java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(Toolkit.getDefaultToolkit().getImage(url));
				trayIcon.setToolTip("ccPhoneUI");
				st.add(trayIcon);
			} catch (java.awt.AWTException e) {
				System.out.println("Unable to add system tray icons");
			}
		}
	}

	public static CallUI getCallUI(final String callId) {
		if(calls.containsKey(callId)){
			return calls.get(callId);
		} else {
			final CallUI callUI = new CallUI(callId,calls);
			calls.put(callId, callUI);
			try {
				FxTools.runAndWait(new Runnable() {
					public void run() {
						try {
							callUI.start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return callUI;
		}
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
				VertX.eb.publish(VertX.guiEventAddress,
						new JsonObject()
.putString("type", "publish").putString("adress", VertX.guiEventAddress).putObject(
										"body",
										new JsonObject().putString(
"text", "ping " + (new Date()).toString())));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
