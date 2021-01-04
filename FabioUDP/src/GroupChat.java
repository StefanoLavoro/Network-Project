import java.net.*;
import java.io.*;
import java.util.*; 
import java.text.*; // MY CODE

public class GroupChat {
	
	static DateFormat fordate = new SimpleDateFormat("dd/MM/yy"); // Added timestamp
	static DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); // Added timestamp
	private static final String TERMINATE = "Exit";	// Redundant Fabio code
	private static final String PREFIX = "/"; // Added prefix for commands
	static String name;
	static volatile boolean finished = false;

	public static void main(String[] args) {

		if (args.length != 2)
			System.out.println("Two arguments required: <multicast-host> <port-number>");
		else {
			try {
				InetAddress group = InetAddress.getByName(args[0]);
				int port = Integer.parseInt(args[1]);
				Scanner sc = new Scanner(System.in);
				System.out.print("Enter your name: ");
				name = sc.nextLine();
				MulticastSocket socket = new MulticastSocket(port);

				socket.setTimeToLive(0);

				socket.joinGroup(group);
				Thread t = new Thread(new ReadThread(socket, group, port));

				boolean listenMode = false;	// Initaliase boolean listenMode

				t.start();

				System.out.println("Start typing messages...\n");
				loop: while (true) {	// labeled the while loop to 'loop'

					String message;
					message = sc.nextLine();
					
					Date date = new Date();	// Added timestamp
					String timestamp = fordate.format(date) + " " + fortime.format(date);	// Added timestamp
					
					if (message.contains(PREFIX)) {

						switch(message) {
							case PREFIX + "help":
								System.out.println("Help commands:");
								System.out.println("- [ /help ] (displays list of commands)");
								System.out.println("- [ /stop ] (activates Listen-Mode)");
								System.out.println("- [ /quit ] (ends connection)");
								break;

							case PREFIX + "quit":
								System.out.println("Goodbye!");
								finished = true;
								socket.leaveGroup(group);
								socket.close();
								break loop;

							case PREFIX + "stop":
								if (listenMode == false) {
									listenMode = true;
									System.out.println("Listen-Mode on");
								}
								else {
									listenMode = false;
									System.out.println("Listen-Mode off");
								}
								break;

							default:
								System.out.println("Command does not exist.");
								break;
						}
					}

					if (listenMode == false) {
						//					message = name + ": " + message;
						message = String.format("[%s] %s: %s", timestamp, name, message);	// Added timestamp
						byte[] buffer = message.getBytes();
						DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port);
						socket.send(datagram);
					}
				}
				sc.close(); 
			}
			catch(SocketException se) {
				System.err.println("Error creating socket");	// Changed "out" to "err"
				se.printStackTrace();
			}
			catch(IOException ie) {
				System.err.println("Error reading/writing from/to socket");	// Changed "out" to "err"
				ie.printStackTrace();
			}
		}
	}
}

class ReadThread implements Runnable {
	
	private MulticastSocket socket;
	private InetAddress group;
	private int port;
	private static final int MAX_LEN = 1000;
	
	ReadThread(MulticastSocket socket,InetAddress group,int port) {
		this.socket = socket;
		this.group = group;
		this.port = port;
	}
	
	@Override
	public void run() {
		while(!GroupChat.finished) {
			byte[] buffer = new byte[ReadThread.MAX_LEN];
			DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port);
			String message;
			try {
				socket.receive(datagram);
				message = new String(buffer,0,datagram.getLength(),"UTF-8");
				if(!message.contains(GroupChat.name))	// Changed "startsWith()" to "contains()"
					System.out.println(message);
			}
			catch(IOException e) {
				System.out.println("Socket closed!");
			}
		}
	}
}