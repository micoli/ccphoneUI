package org.micoli.phone.ccphoneUI.remote;

import org.micoli.phone.ccphoneUI.Main;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

// TODO: Auto-generated Javadoc
/**
 * The Class VertX.
 */
public class VertX {

	/** The vertx. */
	private static Vertx vertx;

	/** The eb. */
	public static EventBus eb;

	/** The gui event address. */
	public static String guiEventAddress = "calls";

	/** The action address. */
	static private String actionAddress = "guiaction";

	/**
	 * Publish daemon.
	 * 
	 * @param action
	 *            the action
	 * @param jsonObject
	 *            the json object
	 */
	public static void publishDaemon(String action, JsonObject jsonObject) {
		System.out.println("publish :" + actionAddress + "." + action);
		eb.publish(actionAddress + "." + action, jsonObject);
	}

	/**
	 * Inits the.
	 */
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

	/**
	 * Run.
	 */
	public static void run() {
	}
}
