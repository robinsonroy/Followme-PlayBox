package com.Followme.Playbox.Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Robinson Roy on 25/03/15.
 */

/**
 * Class use to execute commande for music gesture
 * use mpc to command music player deamon on PlayBox
 */
public class CommandeLine {

    /**
     * come back from mpc commande
     */
    private char[] buffer = new char[2000];

    /**
     * command to execute
     */
    private String cmd;


    /**
     * init command
     * @param cmd
     */
    public CommandeLine(String cmd) {
        this.cmd = cmd;
        for (int i = 0; i < 200; i++) {
            buffer[i] = ' ';
        }
    }

    /**
     * execute command
     * @return terminal return
     */
    public String exec() {

        Runtime r = Runtime.getRuntime();
        Process p = null;
        try {
            p = r.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream myStream = p.getInputStream();

        int i;
        int j = 0;
        try {
            while ((i = myStream.read()) != -1) {

                buffer[j] = (char) i;
                j++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String output = new String(buffer);

        System.out.println(output);

        return output;
    }

}
