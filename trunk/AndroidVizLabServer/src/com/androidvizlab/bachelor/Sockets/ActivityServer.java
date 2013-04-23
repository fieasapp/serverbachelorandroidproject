package com.androidvizlab.bachelor.Sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityServer {
	private static int PORTNR = 1330;
	private ServerSocket server_socket = null;
        private ClientHandler client_handler = null;
        
	public ActivityServer()
	{

	}
        
        
        public void start()
        {
            try
            {
                server_socket = new ServerSocket(PORTNR);
                while(true)
                {
                    System.out.println("Server: Waiting for client. . .");
                    client_handler = new ClientHandler(server_socket.accept());
                    System.out.println("Server: A Client has connected.");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.err.println("Error: " + e);
                stop();
            }
        }
	
	public void stop()
        {
            try
            {
                server_socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("Found an error in closing server socket...");
            }
        }
		
	public static void main(String args[])
	{
		ActivityServer as = new ActivityServer();
                as.start();
	}
}
