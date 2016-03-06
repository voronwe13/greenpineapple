package com.greenpineapple.net;

import java.io.BufferedInputStream;
import java.io.EOFException;
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
import com.badlogic.gdx.net.Socket;

public class NetworkReceiver {

	private static Queue<NetworkObject> networkObjects = new ConcurrentLinkedQueue<>();

	private static ServerSocket serverSocket;
	private static Queue<Thread> threads = new ConcurrentLinkedQueue<>();
	private static Queue<Socket> sockets = new ConcurrentLinkedQueue<>();

	/**
	 * Create server socket to recieve information.
	 */
	public static void createServer() {
		threads.add(createThread(NetworkReceiver::acceptSockets));
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
	 * Stops all server sockets and threads. Clears any networkObjects from the
	 * queue.
	 */
	public static void dispose() {
		while (!threads.isEmpty()) {
			threads.poll().interrupt();
		}
		serverSocket.dispose();
		while (!sockets.isEmpty()) {
			sockets.poll().dispose();
		}
		networkObjects.clear();
	}

	private static Thread createThread(Runnable runnable) {
		Thread thread = new Thread(runnable);
		thread.start();
		return thread;
	}

	private static void acceptSockets() {
		ServerSocketHints serverSocketHints = new ServerSocketHints();
		serverSocketHints.acceptTimeout = 0; // No timeout.

		serverSocket = Gdx.net.newServerSocket(Protocol.TCP, NetworkConstants.PORT, serverSocketHints);

		while (!Thread.currentThread().isInterrupted()) {
			Socket socket = serverSocket.accept(null);
			sockets.add(socket);

			threads.add(createThread(() -> receiveUpdates(socket)));
		}
	}

	private static void receiveUpdates(Socket socket) {
		try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))) {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					networkObjects.add((NetworkObject) inputStream.readObject());
				} catch (EOFException exception) {
					Gdx.app.error("Network", "A client left?", exception);
					threads.remove(Thread.currentThread());
					Thread.currentThread().interrupt();
					sockets.remove(socket);
					socket.dispose();
				} catch (ClassNotFoundException exception) {
					Gdx.app.error("Network", "Unrecognized data type from server!", exception);
				}

			}
		} catch (IOException exception) {
			Gdx.app.error("Network", "Failure receiving data from server!", exception);
		}
	}
}
