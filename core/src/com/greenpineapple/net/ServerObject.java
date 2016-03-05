package com.greenpineapple.net;

import java.util.Objects;

import com.badlogic.gdx.net.Socket;

public class ServerObject {

	private Thread thread;
	private Socket socket;

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		Objects.requireNonNull(thread);
		this.thread = thread;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		Objects.requireNonNull(socket);
		this.socket = socket;
	}
}
