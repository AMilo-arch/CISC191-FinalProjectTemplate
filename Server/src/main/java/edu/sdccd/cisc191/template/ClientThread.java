package edu.sdccd.cisc191.template;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientThread extends Thread {
    private Socket _socket;
    private ObjectInputStream _inputStream;
    private ObjectOutputStream _outputStream;
    // unique id (easier for disconnection)
    private int _id;
    // the Username of the Client
    private String _username;
    private ChatRoomServer _server;

    // Constructor
    public ClientThread(Socket socket, ChatRoomServer server) {
        _server = server;
        // a unique id
        _id = ++server.uniqueId;
        this._socket = socket;
        // open input/output streams
        try {
            _inputStream = new ObjectInputStream(socket.getInputStream());
            _outputStream = new ObjectOutputStream(socket.getOutputStream());
            // receive username from stream
            _username = (String) _inputStream.readObject();
            _server.broadcast(_username + " has joined the chat.");
            System.out.println(_username + " just connected.");
        } catch (IOException eIO) {
            System.out.println("Exception creating new Input/output Streams: " + eIO);
            return;
        } catch (ClassNotFoundException eIO) {
            System.out.println("Problem retrieving username.");
            return;
        }
    }

    // runs forever
    public void run() {
        boolean keepGoing = true;
        while (keepGoing) {
            // read a String (which is an object)
            try {
                String message = (String) _inputStream.readObject();
                // sends to server and clients
                _server.broadcast(_username + ": " + message);
            } catch (IOException eIO) {
                System.out.println("IO problem receiving message.");
                return;
            } catch (ClassNotFoundException eCNF) {
                System.out.println("CNF problem receiving message.");
                return;
            }
        }
        // attempts to close connections
        close();
    }

    // try to close everything
    private void close() {
        // try to close the connection
        try {
            if (_outputStream != null)
                _outputStream.close();
        } catch (Exception e) {
        }

        try {
            if (_inputStream != null)
                _inputStream.close();
        } catch (Exception e) {
        }

        try {
            if (_socket != null)
                _socket.close();
        } catch (Exception e) {
        }
    }

    // Write a String to the Client output stream
    public boolean writeMsg(String msg) {
        // write the message to the stream
        try {
            _outputStream.writeObject(msg);
        } catch (IOException eIO) {
            System.out.println("Problem sending message.");
        }
        return true;
    }
}
