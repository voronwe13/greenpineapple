package com.greenpineapple.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

import com.badlogic.gdx.Gdx;

public class Local {

	public static String getIPAddress() {
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
		} catch (SocketException exception) {
			Gdx.app.error("Network", "Couldn't find local IP address!", exception);
		}

		String ipAddress = null;
		try {
			ipAddress = addresses.stream().filter(address -> address.startsWith("192")).findAny().get();
		} catch (NoSuchElementException exception) {
			Gdx.app.error("Network", "Couldn't find local IP address!", exception);
		}
		return ipAddress;
	}
}
