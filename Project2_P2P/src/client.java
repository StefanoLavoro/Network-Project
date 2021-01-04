// client.java

import java.net.*;
import java.io.*;

public class client
{
	public static void main(String [] args) throws IOException
	{
//		String serverName = args[0];
		InetAddress serverName = InetAddress.getByName("localhost");
		int port = 4444;
		System.out.println(serverName + ":" + port + "\n");

		//console stream
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		
		try
		{
			//client datagram socket and packet 
			DatagramSocket client=new DatagramSocket();
			DatagramPacket packet = null;

			//input and output streams
			byte[] buffer=new byte[256];
			String outMessage="";
			String inMessage="";

			while(!inMessage.equals("exit")){
				//entering the message to send
				System.out.print("Please type message to send : ");
				outMessage = stdIn.readLine();

				//sending message
				buffer = outMessage.getBytes();
				packet = new DatagramPacket(buffer, buffer.length, serverName, port);
				client.send(packet);

				//receiving message
				packet = new DatagramPacket(buffer, buffer.length);
				client.receive(packet);

				inMessage = new String(packet.getData(), 0, packet.getLength());
					System.out.println("[Received message] -> "+inMessage);
			}

			//closing socket
			client.close();
			System.out.println("Socket closed");

		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
