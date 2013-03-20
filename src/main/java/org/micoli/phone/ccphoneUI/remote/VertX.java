package org.micoli.phone.ccphoneUI.remote;

import org.micoli.phone.ccphoneUI.Main;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

public class VertX {
	private static Vertx vertx;
	public static EventBus eb;
	public static String guiEventAddress = "calls";
	static private String actionAddress = "guiaction";

	public static void publishDaemon(String action, JsonObject jsonObject) {
		System.out.println("publish :" + actionAddress + "." + action);
		eb.publish(actionAddress + "." + action, jsonObject);
	}

	public static void init() {
		vertx = Vertx.newVertx(2551, "localhost");
		eb = vertx.eventBus();

		System.out.println("start vertx client");

		Handler<Message<JsonObject>> myHandler = new Handler<Message<JsonObject>>() {
			public void handle(Message<JsonObject> message) {
				String callId = message.body.getString("callId");
				String eventName = message.body.getString("eventName");
				if (eventName == null) {
					Main.manageCalls();
					return;
				}
				System.out.println("Client event due to registration : [" + message.body.getString("text") + "]\n" + message.body.toString());

				System.out.println("------");
				Main.getCallUI(callId).dispatchMessage(eventName, message);
			}
		};

		vertx.eventBus().registerHandler(guiEventAddress, myHandler);
	}

	public static void run() {
	}
}
