package edu.sdccd.cisc191.template;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.event.*;

public class ChatRoomClient extends Application implements IMessageRecievedCallback {
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Socket _socket = new Socket();
	private TextArea chatRoom;

	public void startClient(String[] args) throws Exception {
		// Start chat application
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// attempts to connect to server
		try {
			System.out.println("Connecting...");
			_socket = new Socket("localhost", 8000);
			System.out.println("Connected");
		} catch (Exception e) {
			System.out.println("Could not connect to server.");
			System.exit(0);
		}
		// create scene to set nickname
		Pane namePane = new Pane();
		// label by enter nickname field
		Text nameLabel = new Text("Nickname:");
		nameLabel.setLayoutX(10);
		nameLabel.setLayoutY(77);
		// input nickname text field
		TextField nickname = new TextField();
		nickname.setPromptText("Type your nickname here");
		nickname.setLayoutX(73);
		nickname.setLayoutY(60);
		// submit nickname button
		Button submitNameBtn = new Button();
		submitNameBtn.setLayoutX(90);
		submitNameBtn.setLayoutY(100);
		submitNameBtn.setText("Submit");
		// add all to nickname scene and show it on stage
		namePane.getChildren().addAll(nameLabel, nickname, submitNameBtn);
		Scene nameScene = new Scene(namePane, 240, 200);
		stage.setTitle("Create Nickname");
		stage.setScene(nameScene);
		stage.show();

		// Create Chat Room
		Pane pane = new Pane();
		// Chat room area and properties
		chatRoom = new TextArea();
		chatRoom.setPromptText("Welcome to the chat room!");
		chatRoom.setEditable(false);
		// chatRoom.setWrapText(true);
		chatRoom.setPrefSize(407, 185);
		chatRoom.setLayoutX(10);
		chatRoom.setLayoutY(10);
		// enter message text field and properties
		TextField chatMessage = new TextField();
		chatMessage.setPromptText("Type your message here");
		chatMessage.setLayoutX(10);
		chatMessage.setLayoutY(200);
		chatMessage.setPrefWidth(360);
		// send message button and properties
		Button sendMessageBtn = new Button();
		sendMessageBtn.setLayoutX(375);
		sendMessageBtn.setLayoutY(200);
		sendMessageBtn.setText("Send");
		// adds chat room to pane
		pane.getChildren().addAll(chatRoom, chatMessage, sendMessageBtn);

		// method for submitNameBtn on click handler
		submitNameHandler(submitNameBtn, pane, nickname, stage);
		// method for sendMesageBtn on click handler
		sendMessageHandler(sendMessageBtn, chatMessage);
		// creates the Thread to listen from the server
		new ClientServerListenerThread(this, inputStream, _socket).start();
	}// end start

	// method for submit name button
	public void sendMessageHandler(Button sendMessageBtn, TextField chatMessage) {
		// on click the send button in the chat room sends message to the server
		sendMessageBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				String sentMessage = chatMessage.getText();
				try {
					outputStream.writeObject(sentMessage);
				} catch (IOException eIO) {
					System.out.println("Problem sending message.");
					return;
				}
				System.out.println("Sent Message");
				chatMessage.setText("");
			}
		});

		// if enter is pressed in the textfield it sends the message also
		chatMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					String sentMessage = chatMessage.getText();
					try {
						outputStream.writeObject(sentMessage);
					} catch (IOException eIO) {
						System.out.println("Problem sending message.");
						return;
					}
					System.out.println("Sent Message");
					chatMessage.setText("");
				}
			}
		});
	}

	// method for submit name button
	public void submitNameHandler(Button submitNameBtn, Pane pane, TextField nickname, Stage stage) {
		// on click the name scene submit button opens the chat scene on the stage
		submitNameBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// send username to server
				String username = nickname.getText();
				try {
					outputStream = new ObjectOutputStream(_socket.getOutputStream());
					outputStream.writeObject(username);
				} catch (IOException eIO) {
					System.out.println("Problem sending username.");
					return;
				}
				// add scene to stage and display it
				Scene chatScene = new Scene(pane, 430, 250, Color.GHOSTWHITE);
				stage.setTitle("Chat Room");
				stage.setScene(chatScene);
				stage.show();
			}
		});
	}

	// method to append received messages to text area
	public void append(String receivedMessage) {
		chatRoom.appendText(receivedMessage);
		chatRoom.positionCaret(chatRoom.getText().length() - 1);
	}

	@Override
	public void OnMessageRecieved(String message) {
		String msg = AppendMessageToGui(message.chars().mapToObj(e -> (char) e).collect(Collectors.toList()));
		append(msg);
	}

	/**
	 * Recursive function.
	 * Why?
	 * Boss man gets what boss man wants.
	 * 
	 * Things you can do with this, add special effects to reverse all text,
	 * capitalize every other character, or auto convert text to hex
	 * 
	 * @return
	 */
	public String AppendMessageToGui(List<Character> message) {
		int index = 0;
		Character character = message.get(index);
		message.remove(index);
		StringBuilder sb = new StringBuilder();
		sb.append(character);
		if (message.size() == 0) {
			return sb.toString();
		}

		return sb.toString() + AppendMessageToGui(message);
	}
}