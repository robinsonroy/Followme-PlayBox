package com.Followme.Playbox.Controller;

/**
 * Created by Robinson on 16/02/15.
 */

import java.io.*;
import java.net.*;

class TCPServer implements Runnable {

    private String clientSentence;

    protected volatile boolean running = true;
    private Thread thread;


    public TCPServer() {
        thread = new Thread(this);
    }

    public String getClientSentence() {
        return clientSentence;
    }

    public void setClientSentence(String clientSentence) {
        this.clientSentence = clientSentence;
    }

    public void runServer() {
        thread.start();
    }

    public void stopServer() {
        running = false;
    }

    public void run() {
        System.out.println("Server TCP lance");

        String capitalizedSentence;
        ServerSocket welcomeSocket = null;

        try {
            welcomeSocket = new ServerSocket(6790);
            while (running) {
                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                if (clientSentence != null) {
                    String temp = clientSentence;
                    System.out.println("Received: " + clientSentence);
                    //temp = temp.toUpperCase() + '\n';
                    //outToClient.writeBytes(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}