package edu.sdccd.cisc191.template;

import java.io.*;
import java.net.*;
import java.util.*;

// creates a thread to constantly listen for messages from server
public class ClientServerListenerThread extends Thread {
    private ObjectInputStream _inputStream;
    private Socket _socket;
    private IMessageRecievedCallback _messageRecievedCallback;

    public boolean hasReceivedMessage = false;
    private boolean isRunning = true;

    public ClientServerListenerThread(IMessageRecievedCallback messageRecievedCallback, ObjectInputStream inputStream,
            Socket socket) {
        _messageRecievedCallback = messageRecievedCallback;
        _inputStream = inputStream;
        _socket = socket;
    }
    public ClientServerListenerThread(ObjectInputStream inputStream,
                                      Socket socket) {
        _messageRecievedCallback = null;
        _inputStream = inputStream;
        _socket = socket;
    }

    public void run() {
        try {
           if(_socket == null)
               return;
            _inputStream = new ObjectInputStream(_socket.getInputStream());

            while (isRunning) {
                if(_inputStream == null)break;
                String receivedMessage = (String) _inputStream.readObject();
                if(receivedMessage != null || receivedMessage != "") {
                    hasReceivedMessage = true;
                }
                System.out.println("Received meesage " + receivedMessage);
                // calls the append method to append received messages to text area
               if(_messageRecievedCallback != null){
                   _messageRecievedCallback.OnMessageRecieved(receivedMessage);
               }
            }
        } catch (IOException eIO) {
            System.out.println("IO Problem recieving message from server.");
            return;
        } catch (ClassNotFoundException eCNF) {
            System.out.println("CNF Problem recieving message from server.");
            return;
        }
    }

    public void close(){
        isRunning = false;
        _inputStream = null;
        _socket = null;
    }
}
