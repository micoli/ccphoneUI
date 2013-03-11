package org.micoli.phone.ccphoneUI;

import java.awt.SystemTray;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Date;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import javax.swing.SwingUtilities;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

public class Main {
	static String topicAddress = "topic";
	static Vertx vertx;

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
						AnswerFrame answerJFX = new AnswerFrame();
						Stage stage2 = new Stage();
						answerJFX.start(stage2);
					}
				});
			}
		});
		// This method is invoked on the EDT thread
		/*JFrame frame = new JFrame("Swing and JavaFX");
		final JFXPanel fxPanel = new JFXPanel();
		frame.add(fxPanel);
		frame.setSize(300, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
		SystemTray st;
		st = java.awt.SystemTray.isSupported() ? java.awt.SystemTray.getSystemTray() : null;
		if (st != null && st.getTrayIcons().length == 0) {
			// final String imageName = st.getTrayIconSize().width > 16 ?
			// st.getTrayIconSize().width > 64 ? "128" : "64" : "16";
			try {
				URL url = new URL("http://www.veryicon.com/icon/16/System/Palm/Settings%20Phone.png");
				final java.awt.Image image = Toolkit.getDefaultToolkit().getImage(url);
				;
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
	private static void vertxClient(){
		vertx = Vertx.newVertx(2551, "localhost");
		System.out.println("start vertx client");

		Handler<Message<JsonObject>> myHandler = new Handler<Message<JsonObject>>() {
			public void handle(Message<JsonObject> message) {
				System.out.println("Client event due to registration : ["+message.body.getString("text") +"]\n"
						+ message.body.toString());// + " "+ event.replyAddress);
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
												"ping "+ (new Date()).toString())
										)
								);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}