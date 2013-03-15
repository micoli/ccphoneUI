package org.micoli.phone.ccphoneUI.remote;

import org.micoli.phone.ccphoneUI.CallUI;
import org.micoli.phone.ccphoneUI.Main;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

public class VertX {
	private static Vertx vertx;
	public static EventBus eb;
	public static String topicAddress = "calls";

	public static void init() {
		vertx = Vertx.newVertx(2551, "localhost");
		eb = vertx.eventBus();

		System.out.println("start vertx client");

		Handler<Message<JsonObject>> myHandler = new Handler<Message<JsonObject>>() {
			public void handle(Message<JsonObject> message) {
				String callId = message.body.getString("callid");
				String eventName = message.body.getString("eventName");
				CallUI callUI = null;
				if (eventName == null) {
					return;
				}
				System.out.println("Client event due to registration : [" + message.body.getString("text") + "]\n" + message.body.toString());

				System.out.println("------");
				callUI = Main.getCallUI(callId);
				if (eventName.equalsIgnoreCase("setSipRequest")) {
					// Ext.getCmp('txtsipcallid').setValue(msg.callId);
				} else if (eventName.equalsIgnoreCase("incomingCall")) {
					callUI.displayAnswerFrame(message);
					// Ext.getCmp('txtsipcallid').setValue(msg.callId);
					// Ext.getCmp('txtsipcallidanswer').setValue(msg.callId)
					// Ext.getCmp('txtsipcallidbusy').setValue(msg.callId)
				}
			}
		};

		vertx.eventBus().registerHandler(topicAddress, myHandler);
	}

	public static void run() {
	}
}
