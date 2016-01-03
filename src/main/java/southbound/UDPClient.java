/*
 * Copyright © 2015 Cisco Systems and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package southbound;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:UDPClients with multi-thread
 * Created by Quentin(陈坤) on 12/21/15.
 */
public class UDPClient {
    public static void main(String[] args) throws Exception{
        if (args.length != 2) {
            System.out.println("Wrong input!");
            System.out.println("USAGE: java -tar target/udp-test-1.0-SNAPSHOT.jar [client number] [sleep time(ms)]");
            return;
        }
        System.out.println(args[0] + " UDP clients will be started.");
        Thread.sleep(5000);
        InetAddress IPAddress = InetAddress.getByName("localhost");
        int listenPort = 9876;
        int clientNum = Integer.parseInt(args[0]);
        int sleepTime = Integer.parseInt(args[1]);
        Client clients[] = new Client[clientNum];
        for (int i = 0; i < clientNum; i++) {
            clients[i] = new Client("Client " + (i+1), IPAddress, listenPort, sleepTime);
            clients[i].start();
        }
    }
}

class Client extends Thread {
    private int sleepTime;
    private InetAddress IPAddress;
    private int listenPort;
    private String name;
    private byte[] sendData;
    private String sentence;
    private DatagramSocket clientSocket;


    public Client(String name, InetAddress IPAddress, int listenPort, int sleepTime) throws Exception{
        this.clientSocket = new DatagramSocket();
        setName(name);
        this.name = name;
        this.IPAddress = IPAddress;
        this.listenPort = listenPort;
        this.sleepTime = sleepTime;
        System.out.println(name + "is prepared to sending syslog message.");
    }

    public void run() {
        while (true)
        {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sentence = "[" + df.format(new Date()) + "]|[" + name + "]|MESSAGE";
                sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, listenPort);
                clientSocket.send(sendPacket);
                System.out.println("SENDING DONE: " + sentence);
                Thread.sleep(50);
            } catch (Exception e) {
                clientSocket.close();
            }
        }
    }
}
