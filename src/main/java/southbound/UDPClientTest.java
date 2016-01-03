/*
 * Copyright © 2015 Cisco Systems and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package southbound;

import config.Config;
import config.XMLReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Description:
 * Created by Quentin(陈坤) on 15/12/17.
 */
public class UDPClientTest {
    public static void main(String[] args) throws Exception {
        Config config = new XMLReader().loadConfig();

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(config.ipAddress);
        int listenPort = Integer.parseInt(config.port);
//        InetAddress IPAddress = InetAddress.getByName("localhost");
//        int listenPort = 514;
        System.out.println("Waiting for input sending to " + IPAddress + ":" + listenPort);
        byte[] sendData;
        byte[] receiveData = new byte[1024];
        String sentence = inFromUser.readLine();
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, listenPort);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String modifiedSentence = new String(receivePacket.getData());
        System.out.println("FROM SERVER : " + modifiedSentence);
        clientSocket.close();
    }
}
