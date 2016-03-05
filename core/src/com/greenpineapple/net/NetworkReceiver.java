package com.greenpineapple.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;

public class NetworkReceiver {

	private static ServerObject serverObject;

	private static Queue<NetworkObject> networkObjects = new ConcurrentLinkedQueue<>();

	/**
	 * Create server socket to recieve information.
	 */
	public static void createServer() {
		serverObject = createServerObject(NetworkConstants.PORT);
	}

	/**
	 * @return all objects updated by the servers since the last
	 *         retrieveUpdates() call.
	 */
	public static List<NetworkObject> retrieveUpdates() {
		List<NetworkObject> updatedObjects = new ArrayList<>();
		while (networkObjects.peek() != null) {
			updatedObjects.add(networkObjects.poll());
		}
		return updatedObjects;
	}

	/**
	 * Stops all server sockets and threads.
	 */
	public static void dispose() {
		serverObject.getThread().interrupt();
		if (serverObject.getSocket() != null) {
			serverObject.getSocket().dispose();
		}
	}

	private static ServerObject createServerObject(int port) {
		ServerObject serverObjects = new ServerObject();
		serverObjects.setThread(new Thread(new Runnable() {
			@Override
			public void run() {
				ServerSocketHints serverSocketHint = new ServerSocketHints();
				// 0 means no timeout. Probably not the greatest idea in
				// production!
				serverSocketHint.acceptTimeout = 0;

				ServerSocket serverSocket = Gdx.net.newServerSocket(Protocol.TCP, port, serverSocketHint);

				while (!Thread.currentThread().isInterrupted()) {
					serverObjects.setSocket(serverSocket.accept(null));

					try {
						ObjectInputStream inputStream = new ObjectInputStream(
								new BufferedInputStream(serverObjects.getSocket().getInputStream()));
						networkObjects.add((NetworkObject) inputStream.readObject());
					} catch (IOException exception) {
						Gdx.app.error("Network", "Failure receiving data from server!", exception);
					} catch (ClassNotFoundException exception) {
						Gdx.app.error("Network", "Unrecognized data type from server!", exception);
					}
				}
			}
		}));
		serverObjects.getThread().start();

		return serverObjects;
	}
}
