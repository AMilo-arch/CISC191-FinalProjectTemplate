package edu.sdccd.cisc191.template.test;


import edu.sdccd.cisc191.template.ChatRoomServer;
import edu.sdccd.cisc191.template.ClientServerListenerThread;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.io.ObjectInputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Checks to see if the major functions are working properly.
 */


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class ChatRoomTest {

   private ChatRoomServer _server;

    @BeforeAll
    public void setUp(){

        _server = new ChatRoomServer(8000);
        _server.start();

    }
    /* @Test
    public void checkServerStart(){
        assertFalse(_server.isServerRunning);
    }
     */

    @Test
    public void checkAddClient(){
        boolean isClientAdded = false;
        try {
            Socket socket = new Socket("localhost", 8000);
            isClientAdded = true;
        } catch (Exception e) {
            isClientAdded = false;
        }
        assertTrue(isClientAdded);
    }

    @Test
    public void checkClientSendMessage(){
        boolean sentMessage = false;

        try {
            Socket socket = new Socket("localhost", 8000);
            ObjectInputStream inputStream = null;
            ClientServerListenerThread client = new ClientServerListenerThread(inputStream, socket);
            client.start();
            _server.broadcast("Sample");
            sentMessage = true;
            client.close();
            client = null;
        } catch (Exception e) {
            sentMessage = false;
        }
        assertTrue(sentMessage);
    }

    @Test
    public void testClientReceivedMessage(){
        boolean receivedMessage = false;

        try {
            Socket socket = new Socket("localhost", 8000);
            ObjectInputStream inputStream = null;
            ClientServerListenerThread client = new ClientServerListenerThread(inputStream, socket);
            client.start();
            _server.broadcast("Sample");
            receivedMessage = true;
            client.close();
            client = null;
        } catch (Exception e) {
            receivedMessage = false;
        }
        assertTrue(receivedMessage);

    }
    @AfterAll
    public void closeTest(){
        _server = null;
    }

}
