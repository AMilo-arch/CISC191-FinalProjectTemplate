package edu.sdccd.cisc191.template;

import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) throws Exception {

        ChatRoomClient client = new ChatRoomClient();
        client.startClient(args);

    }
} // end class client

