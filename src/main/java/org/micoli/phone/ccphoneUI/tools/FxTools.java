package org.micoli.phone.ccphoneUI.tools;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FxTools {
	public static void skinWindow(Stage primaryStage, Pane root, int Constraint, boolean draggable) {
		Scene mainScene = new Scene(root);
		mainScene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(mainScene);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setOpacity(0.9f);
		if (draggable) {
			DraggableWindow.makeDraggableTop(primaryStage, root, Constraint);
		}
	}

	public static void skinWindow(Stage primaryStage, int Constraint, boolean draggable) {
		try {
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setOpacity(0.9f);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		if (draggable) {
			DraggableWindow.makeDraggableTop(primaryStage, primaryStage.getScene().getRoot(), Constraint);
		}
	}

	private static class ThrowableWrapper {
		Throwable t;
	}

	/**
	 * Invokes a Runnable in JFX Thread and waits while it's finished. Like
	 * SwingUtilities.invokeAndWait does for EDT.
	 *
	 * from http://www.guigarage.com/2013/01/invokeandwait-for-javafx/
	 *
	 * @param run
	 *            The Runnable that has to be called on JFX thread.
	 * @throws InterruptedException
	 *             f the execution is interrupted.
	 * @throws ExecutionException
	 *             If a exception is occurred in the run method of the Runnable
	 */
	public static void runAndWait(final Runnable run) throws InterruptedException, ExecutionException {
		if (Platform.isFxApplicationThread()) {
			try {
				run.run();
			} catch (Exception e) {
				throw new ExecutionException(e);
			}
		} else {
			final Lock lock = new ReentrantLock();
			final Condition condition = lock.newCondition();
			final ThrowableWrapper throwableWrapper = new ThrowableWrapper();
			lock.lock();
			try {
				Platform.runLater(new Runnable() {
					public void run() {
						lock.lock();
						try {
							run.run();
						} catch (Throwable e) {
							throwableWrapper.t = e;
						} finally {
							try {
								condition.signal();
							} finally {
								lock.unlock();
							}
						}
					}
				});
				condition.await();
				if (throwableWrapper.t != null) {
					throw new ExecutionException(throwableWrapper.t);
				}
			} finally {
				lock.unlock();
			}
		}
	}
}
