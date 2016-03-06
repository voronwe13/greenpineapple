package com.greenpineapple.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class NetworkTransmitter {
	private static final Object CLIENT_SOCKET_LOCK = new Object();
	private static Queue<SocketData> clientSockets = new ConcurrentLinkedQueue<>();

	private static Queue<NetworkObject> networkObjects = new ConcurrentLinkedQueue<>();

	/**
	 * Adds a client to the list of clients to transmit to.
	 * 
	 * @param address
	 *            cannot be null
	 * @param port
	 * @return true if the connection is successful
	 */
	public static boolean addClient(String address) {
		Objects.requireNonNull(address);

		SocketHints socketHints = new SocketHints();
		socketHints.connectTimeout = NetworkConstants.CONNECTION_TIMEOUT;
		Socket clientSocket;
		try {
			clientSocket = Gdx.net.newClientSocket(Protocol.TCP, address, NetworkConstants.PORT, socketHints);
			Gdx.app.log("Network", "Transmitting to " + clientSocket.getRemoteAddress());
		} catch (GdxRuntimeException exception) {
			Gdx.app.error("Network", "Couldn't connect to client at " + address, exception);
			return false;
		}

		try {
			synchronized (CLIENT_SOCKET_LOCK) {
				clientSockets.add(new SocketData(clientSocket));
			}
		} catch (IOException exception) {
			Gdx.app.error("Network", "Error creating the socket data for a client socket", exception);
		}
		return true;
	}

	/**
	 * Register an object to be transmitted until it is disposed.
	 * 
	 * @param object
	 *            cannot be null
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
			for (SocketData clientSocket : clientSockets) {
				try {
					ObjectOutputStream outputStream = clientSocket.getOutputStream();
					for (NetworkObject object : networkObjects) {
						if (clientSocket.getChangeMap().updateChangeMap(object)) {
							Gdx.app.log("Network", "Transmitting object " + object);
							outputStream.writeObject(object);
						}
					}
					outputStream.flush();
					outputStream.reset();
				} catch (SocketException exception) {
					clientSockets.remove(clientSocket);
					clientSocket.getSocket().dispose();
					try {
						clientSocket.getOutputStream().close();
					} catch (IOException ioException) {
						Gdx.app.error("Network", "Error closing output stream", ioException);
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
	 * Remove all clients and registered objects from the transmitter. This will
	 * not dispose the objects.
	 */
	public static void dispose() {
		synchronized (CLIENT_SOCKET_LOCK) {
			for (SocketData clientSocket : clientSockets) {
				clientSocket.getSocket().dispose();
				try {
					clientSocket.getOutputStream().close();
				} catch (IOException exception) {
					Gdx.app.error("Network", "Error closing output stream", exception);
				}
			}
			clientSockets.clear();
		}
		networkObjects.clear();
	}

	private static class SocketData {
		private final Socket socket;
		private final ObjectOutputStream outputStream;
		private final ChangeMap changeMap = new ChangeMap();
		
		public SocketData(Socket socket) throws IOException {
			this.socket = socket;
			this.outputStream = new ObjectOutputStream(socket.getOutputStream());
		}

		public Socket getSocket() {
			return socket;
		}

		public ObjectOutputStream getOutputStream() {
			return outputStream;
		}

		public ChangeMap getChangeMap() {
			return changeMap;
		}
	}
}
