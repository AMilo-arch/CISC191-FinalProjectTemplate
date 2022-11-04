package edu.sdccd.cisc191.template;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatRoomServer {
	// a unique ID for each connection
	public int uniqueId = 0;
	// an ArrayList to keep the list of the Client
	public ArrayList<ClientThread> clientList;
	private int _port;
	private boolean _keepGoing;
	public boolean isServerRunning = false;

	// constructor
	public ChatRoomServer(int port) {
		this._port = port;
		clientList = new ArrayList<ClientThread>();
	}
	// opens a thread allows clients to connect to server
	public void start() {
		_keepGoing = true;
		isServerRunning = false;
		new ListenForClientsThread(this).start();
	}

	// send messages to server and all clients
	public synchronized void broadcast(String msg) {
		String message = msg + "\n";
		// print in server
		System.out.println(message);
		// loop in reverse order in case we have to remove a Client due to
		// disconnection
		for (int i = clientList.size(); --i >= 0;) {
			ClientThread ct = clientList.get(i);
			// try to write to the Client. if it fails remove it from the list
			if (ct.writeMsg(message) == false) {
				clientList.remove(i);
			}
		}
	}

	public void AddClient(ServerSocket serverSocket, ChatRoomServer server) {
		try {
			// format message saying we are waiting
			System.out.println("Server waiting for Clients on port " + _port + ".");
			Socket socket = serverSocket.accept();
			System.out.println("Client connected");
			// creates thread for new client and adds them to arraylist
			ClientThread t = new ClientThread(socket, server);
			clientList.add(t);
			t.start();
	   	} catch (IOException e) {
			System.out.println("Exception on new ServerSocket: " + e + "\n");
			return;
		}
	}

	class ListenForClientsThread extends Thread {

		private ChatRoomServer _server;

		public ListenForClientsThread(ChatRoomServer server) {
			_server = server;
		}

		public void run() {
			// create socket server and wait for connection requests
			try {
				ServerSocket serverSocket = new ServerSocket(_port);

				// infinite loop to wait for connections
				while (_keepGoing) {
					isServerRunning = true;
					AddClient(serverSocket, _server);
				}
				isServerRunning = false;
				serverSocket.close();
			} catch (IOException e) {
				System.out.println("Exception on new ServerSocket: " + e + "\n");
				return;
			}
		}
		public void AddClient(ServerSocket serverSocket, ChatRoomServer server) {
			try {

				// format message saying we are waiting
				System.out.println("Server waiting for Clients on port " + _port + ".");
				Socket socket = serverSocket.accept();
				System.out.println("Client connected");
				// creates thread for new client and adds them to arraylist
				ClientThread t = new ClientThread(socket, server);
				clientList.add(t);
				t.start();
			} catch (IOException e) {
				System.out.println("Exception on new ServerSocket: " + e + "\n");
				return;
			}
		}
	}
}
