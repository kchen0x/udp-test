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

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Description:
 * Created by Quentin(陈坤) on 15/12/17.
 */
public class UDPServerTest {
    public static void main(String[] args) throws Exception {
        Config config = new XMLReader().loadConfig();

        int listenPort = Integer.parseInt(config.port);
//        int listenPort = 514;
        DatagramSocket serverSocket = new DatagramSocket(listenPort);
        byte[] receiveData = new byte[1024];
        byte[] sendData;
        //noinspection InfiniteLoopStatement
        System.out.println("Server is listening the port: " + listenPort);
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            System.out.println("RECEIVED: " + sentence);
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }
}
