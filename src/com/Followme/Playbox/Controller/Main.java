package com.Followme.Playbox.Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        CommandeLine cmd = null;
        TCPServer server = new TCPServer();
        String rep;

        server.runServer();

        Pattern pattern = Pattern.compile("^(\\d+):(.+)$");
        Matcher matcher;
        Pattern musicStatePattern = Pattern.compile("\\[(paused|playing)\\]");
        Matcher matcher2;
        Pattern volumePattern = Pattern.compile("(\\d{1,3})%");

        boolean finish = false;
        while (!finish) {

            System.out.println("Check is running");
            for (; ; ) {

                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (server.getClientSentence() != null) {
                    matcher = pattern.matcher(server.getClientSentence());
                    if (matcher.find()) {
                        if (matcher.group(1).equals("1")) {

                            String url = matcher.group(2);
                            System.out.println("Start new song ..");
                            cmd = new CommandeLine("mpc clear");
                            cmd.exec();
                            cmd = new CommandeLine("mpc add " + url);
                            System.out.println("mpc add " + url);
                            rep = cmd.exec();
                            cmd = new CommandeLine("mpc play");
                            System.out.println("mpc play");
                            rep = rep + cmd.exec();
                            System.out.println(rep);

                        } else if (matcher.group(1).equals("2")) {

                            System.out.println("mpc");
                            cmd = new CommandeLine("mpc");
                            rep = cmd.exec();
                            System.out.println(rep);

                            matcher2 = musicStatePattern.matcher(rep);
                            if (matcher2.find()) {
                                System.out.println("Find");
                                if (matcher2.group(1).equals("playing")) {

                                    System.out.println("Pause ...");
                                    cmd = new CommandeLine("mpc pause");
                                    cmd.exec();

                                } else if (matcher2.group(1).equals("paused")) {

                                    System.out.println("Play ...");
                                    cmd = new CommandeLine("mpc play");
                                    cmd.exec();
                                }
                            }
                        } else if (matcher.group("1").equals("3")) {

                            System.out.println("mpc");
                            cmd = new CommandeLine("mpc");
                            rep = cmd.exec();
                            System.out.println(rep);
                            if (matcher.group(2).equals("+")) { //Volume up

                                matcher2 = volumePattern.matcher(rep);
                                String volume = matcher2.group(1);
                                int volumeStat = Integer.parseInt(volume);
                                if (volumeStat > 95)
                                    volumeStat = 100;
                                else
                                    volumeStat = volumeStat + 5;

                                cmd = new CommandeLine("mpc volume " + volumeStat);
                                cmd.exec();

                            } else if (matcher.group(2).equals("-")) { //Volume Down

                                matcher2 = volumePattern.matcher(rep);
                                String volume = matcher2.group(1);
                                int volumeState = Integer.parseInt(volume);
                                if (volumeState < 5)
                                    volumeState = 0;
                                else
                                    volumeState = volumeState + 5;
                                cmd = new CommandeLine("mpc volume " + volumeState);
                                cmd.exec();
                            } else { // Change volume with int between 0 and 10
                                String volume = matcher.group(2);
                                try {
                                    int volumeState = Integer.parseInt(volume);
                                    cmd = new CommandeLine("mpc volume " + volumeState);
                                    cmd.exec();
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                server.setClientSentence(null);
            }
        }
    }
}