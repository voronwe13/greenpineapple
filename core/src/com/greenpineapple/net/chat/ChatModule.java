package com.greenpineapple.net.chat;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.greenpineapple.net.NetworkObject;
import com.greenpineapple.net.NetworkObjectDescription;
import com.greenpineapple.net.NetworkReceiver;
import com.greenpineapple.net.NetworkTransmitter;
import com.greenpineapple.net.primitive.NetworkString;

public class ChatModule extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Skin skin;
	private Stage stage;
	private Label labelHostIPAddress;
	private TextArea textPeers;
	private TextButton buttonSetPeers;
	private Label labelChatRoom;
	private TextField textMessage;
	private TextButton buttonSendMessage;
	private Label labelDebug;

	// Pick a resolution that is 16:9 but not unreadibly small
	public final static float VIRTUAL_SCREEN_HEIGHT = 960;
	public final static float VIRTUAL_SCREEN_WIDTH = 540;

	private Queue<String> chatRoomMessages = new ConcurrentLinkedQueue<>();
	private NetworkString messageToNetwork = new NetworkString("", NetworkObjectDescription.PLAYER_NAME);

	@Override
	public void create() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();

		// Load our UI skin from file. Once again, I used the files included in
		// the tests.
		// Make sure default.fnt, default.png, uiskin.[atlas/json/png] are all
		// added to your assets
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();
		// Wire the stage to receive input, as we are using Scene2d in this
		// example
		Gdx.input.setInputProcessor(stage);

		// The following code loops through the available network interfaces
		// Keep in mind, there can be multiple interfaces per device, for
		// example
		// one per NIC, one per active wireless and the loopback
		// In this case we only care about IPv4 address ( x.x.x.x format )
		List<String> addresses = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface ni : Collections.list(interfaces)) {
				for (InetAddress address : Collections.list(ni.getInetAddresses())) {
					if (address instanceof Inet4Address) {
						addresses.add(address.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		// Print the contents of our array to a string. Yeah, should have used
		// StringBuilder
		String ipAddress = null;
		try {
			ipAddress = addresses.stream().filter(address -> address.startsWith("192")).findAny().get();
		} catch (NoSuchElementException exception) {
			Gdx.app.error("Network", "Couldn't find local IP address!");
		}

		// Now setup our scene UI

		// Vertical group groups contents vertically. I suppose that was
		// probably pretty obvious
		VerticalGroup vg = new VerticalGroup().space(3).pad(5).fill();// .space(2).pad(5).fill();//.space(3).reverse().fill();
		// Set the bounds of the group to the entire virtual display
		vg.setBounds(0, 0, VIRTUAL_SCREEN_WIDTH, VIRTUAL_SCREEN_HEIGHT);

		// Create our controls
		labelHostIPAddress = new Label(ipAddress, skin);
		textPeers = new TextArea("", skin);
		buttonSetPeers = new TextButton("Set Peers", skin);
		labelChatRoom = new Label("hello world", skin);
		textMessage = new TextArea("", skin);
		buttonSendMessage = new TextButton("Send Message", skin);
		labelDebug = new Label("", skin);

		// Add them to scene
		vg.addActor(labelHostIPAddress);
		vg.addActor(textPeers);
		vg.addActor(buttonSetPeers);
		vg.addActor(labelChatRoom);
		vg.addActor(textMessage);
		vg.addActor(buttonSendMessage);
		vg.addActor(labelDebug);

		// Add scene to stage
		stage.addActor(vg);

		// Setup a viewport to map screen to a 480x640 virtual resolution
		// As otherwise this is way too tiny on my 1080p android phone.
		stage.getCamera().viewportWidth = VIRTUAL_SCREEN_WIDTH;
		stage.getCamera().viewportHeight = VIRTUAL_SCREEN_HEIGHT;
		stage.getCamera().position.set(VIRTUAL_SCREEN_WIDTH / 2, VIRTUAL_SCREEN_HEIGHT / 2, 0);

		// Setup event to set ports.
		NetworkReceiver.createServer();

		// Setup event to set peers.
		createSetPeersEvent();

		// Setup event to send message.
		createSendMessageEvents();
	}

	@Override
	public void dispose() {
		NetworkReceiver.dispose();
		NetworkTransmitter.dispose();
		batch.dispose();
	}

	@Override
	public void render() {
		receiveNetworkMessages();

		displayMessages();

		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		stage.draw();
		batch.end();
	}

	private void createSetPeersEvent() {
		buttonSetPeers.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				NetworkTransmitter.dispose();

				String[] networkAddresses = textPeers.getText().split(";");
				for (String networkAddress : networkAddresses) {
					NetworkTransmitter.addClient(networkAddress);
				}

				NetworkTransmitter.register(messageToNetwork);
			}
		});
	}

	private void createSendMessageEvents() {
		buttonSendMessage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sendMessage();
			}
		});
		textMessage.addListener(new InputListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				if (character == '\r' || character == '\n') {
					sendMessage();
				}
				return false;
			}
		});
	}

	private void sendMessage() {
		String message = textMessage.getText();
		chatRoomMessages.add(message);
		textMessage.setText("");

		messageToNetwork.setMessage(message);
		NetworkTransmitter.transmit();
	}

	private void receiveNetworkMessages() {
		List<NetworkObject> networkObjects = NetworkReceiver.retrieveUpdates();

		for (NetworkObject networkObject : networkObjects) {
			chatRoomMessages.add(((NetworkString) networkObject).getMessage());
		}
	}

	private void displayMessages() {
		while (chatRoomMessages.size() > 10) {
			chatRoomMessages.poll();
		}
		StringBuilder builder = new StringBuilder();
		for (String message : chatRoomMessages) {
			builder.append(message);
			builder.append("\n");
		}
		labelChatRoom.setText(builder.toString());
	}
}
