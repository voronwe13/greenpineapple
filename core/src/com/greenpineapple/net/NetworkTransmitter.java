package com.greenpineapple.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class NetworkTransmitter {
	private static final int CONNECT_TIMEOUT = 4_000;

	private static final Object CLIENT_SOCKET_LOCK = new Object();
	private static Queue<Socket> clientSockets = new ConcurrentLinkedQueue<>();
	
	private static Queue<NetworkObject> networkObjects = new ConcurrentLinkedQueue<>();

	/**
	 * Adds a client to the list of clients to transmit to.
	 * @param address cannot be null
	 * @param port
	 */
	public static void addClient(String address, int port) {
		Objects.requireNonNull(address);
		
		SocketHints socketHints = new SocketHints();
		socketHints.connectTimeout = CONNECT_TIMEOUT;
		Socket clientSocket = Gdx.net.newClientSocket(Protocol.TCP, address, port, socketHints);

		synchronized (CLIENT_SOCKET_LOCK) {
			clientSockets.add(clientSocket);
		}
	}

	/**
	 * Register an object to be transmitted until it is disposed.
	 * @param object cannot be null
	 */
	public static void register(NetworkObject object) {
		Objects.requireNonNull(object);
		
		networkObjects.add(object);
	}

	/**
	 * Transmits the state of all registered objects to all of the clients.
	 */
	public static void transmit() {
		synchronized (CLIENT_SOCKET_LOCK) {
			for (Socket clientSocket : clientSockets) {
				try (ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {
					for (NetworkObject object : networkObjects) {
						outputStream.writeObject(object);
					}
				} catch (IOException exception) {
					Gdx.app.error("Network", "Failure sending data to client!", exception);
				}
			}
		}
		
		for (NetworkObject object : networkObjects) {
			if (object.isDisposed()) {
				networkObjects.remove(object);
			}
		}
	}

	/**
	 * Remove all clients and registered objects from the transmitter. This will not dispose the objects.
	 */
	public static void dispose() {
		synchronized (CLIENT_SOCKET_LOCK) {
			for (Socket clientSocket : clientSockets) {
				clientSocket.dispose();
			}
			clientSockets.clear();
		}
		networkObjects.clear();
	}
}
