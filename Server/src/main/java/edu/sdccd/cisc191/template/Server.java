package edu.sdccd.cisc191.template;


/**
 * Implements both of the interfaces, in order to update visuals when certain events happen.
 * Server will start the game.
 */

public class Server {

    public static void main(String[] args){

        ChatRoomServer server = new ChatRoomServer(8000);
        server.start();

    }

}
