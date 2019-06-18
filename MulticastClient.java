/* Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved. */ 

import java.io.*;
import java.net.*;

public class MulticastClient {

    public static void main(String[] args) {
    	InetAddress address = null;
    	int port = -1;
    	int line = 24; // <- How many time do you want?
		try {
			address = InetAddress.getByName(args[0]); // join MulticastAddress
			port = Integer.parseInt(args[1]);
		} catch (Exception ex) {
			System.err.println(
					"Usage: java Classname multicast_address port");
			System.exit(1);
		}
		
		try {
			MulticastSocket socket = new MulticastSocket(port);
			socket.joinGroup(address);
			System.out.println("joined "+address);
			
			for(int i=0;i<line;i++) {
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				
				socket.receive(packet);
				
				String received = new String(packet.getData(), 0, packet.getLength());
				
				System.out.println("TTL "+socket.getTimeToLive()+", Received: "+received);
			}
			
			socket.leaveGroup(address);
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}
    }
}
