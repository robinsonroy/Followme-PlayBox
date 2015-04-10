package com.Followme.Playbox.Controller;

/**
 * Created by Robinson on 16/02/15.
 */

import java.io.*;
import java.net.*;

/**
 * Serveur TCP
 * Listen commande from SmartBox for music gesture
 */
class TCPServer implements Runnable {

    /**
     * Sentence receive from clien (SmartBox)
     */
    private String clientSentence;

    /**
     * Boolean to close thread
     */
    protected volatile boolean running = true;

    /**
     * Thread to listen commande
     */
    private Thread thread;


    /**
     * Thread init
     */
    public TCPServer() {
        thread = new Thread(this);
    }

    /**
     * get client sentence
     * @return client sentence
     */
    public String getClientSentence() {
        return clientSentence;
    }

    /**
     * set clientSentence (need to init at null when there is no sentence from client)
     * @param clientSentence
     */
    public void setClientSentence(String clientSentence) {
        this.clientSentence = clientSentence;
    }

    /**
     * Start thread TCP server
     */
    public void runServer() {
        thread.start();
    }

    /**
     * Stop thread TCP server
     */
    public void stopServer() {
        running = false;
    }

    /**
     * thread's run
     * Listen TCP client
     * share new sentence in String clientSentence
     */
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