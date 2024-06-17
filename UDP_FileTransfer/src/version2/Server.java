package version2;

import java.io.*;
import java.net.*;

public class Server {
	
	public static DataOutputStream dataOutputStream = null;
	public static DataInputStream dataInputStream = null;
	public static int port = 4444;
	
	public static byte[] buffer = new byte[4*2048];
	
	public static String symbol = "+"; // loading icon symbol
	public static int interval = 2; // 1, 2, 4 or 5
	public static int temp = -1;
	

	public static void main(String[] args) {
		
		try (ServerSocket ss = new ServerSocket(port)) {
			System.out.println("Listening to port: " + ss.getLocalPort());

			Socket cs = ss.accept();
			
			System.out.println("Connected to - " + cs.getInetAddress());
			
			dataInputStream = new DataInputStream(cs.getInputStream());
			dataOutputStream = new DataOutputStream(cs.getOutputStream());
			
//			receiveFile("C:\\Users\\maste\\Documents\\20-Coding\\Java\\testdoc_copy.txt"); // 46 KB
//			receiveFile("C:\\Users\\maste\\Documents\\20-Coding\\Java\\lisbonuni_copy.png"); // 475 KB
//			receiveFile("C:\\Users\\maste\\Documents\\20-Coding\\Java\\testMP3_copy.mp3"); // 12.521 KB
			receiveFile("C:\\Users\\maste\\Documents\\20-Coding\\Java\\bigfile_copy.mp4"); // 2.118.541 KB
			
			dataInputStream.close();
			dataOutputStream.close();
			cs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void displayProgress(int packetsSent, long size, long fSize, double completion) {
//		System.out.println("Details - Size:" + size + " - FileSize:" + fSize);
		System.out.print(String.format("[ %6.02f %% ] - ", completion).toString());
		System.out.println(packetsSent + " - bytes left: " + (size / 1000) + " KB");
	}
	
	
	public static void fileSent(String fileName, long exeTime, int packetsSent) {
		System.out.println("\n\n-------- File sent successfully --------");
		System.out.printf("To: %s\n", fileName);
		System.out.println("\nTime elapsed: " + (exeTime) + " ms (" + (exeTime/1000) + " s)");
		System.out.println("Packets sent: " + packetsSent + " (Buffer Size: " + buffer.length + ")");
	}

	
	public static void progressBar() {
		System.out.println("\nProgress:\n");
		
		switch(interval) {
		case 1:
			System.out.println("0%   5%  10%  15%  20%  25%  30%  35%  40%  45%  50%  55%  60%  65%  70%  75%  80%  85%  90%  95%  100%");
			System.out.println("|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|");
			break;
		case 2:
			System.out.println("0%  10%  20%  30%  40%  50%  60%  70%  80%  90%  100%");
			System.out.println("|----|----|----|----|----|----|----|----|----|----|");
			break;
		case 4:
			System.out.println("0%  20%  40%  60%  80%  100%");
			System.out.println("|----|----|----|----|----|");
			break;
		case 5:
		default:
			System.out.println("0%  25%  50%  75%  100%");
			System.out.println("|----|----|----|----|");
			break;
		}
	}
	
	
	public static void receiveFile(String fileName) {
		
		try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
			long start = System.currentTimeMillis();
			int packetsSent = 0;
			int bytes = 0;
			
			// read file size
			long size = dataInputStream.readLong();
			long fSize = size;
			
			System.out.println("\nReceiving file... " + fileName);
			System.out.println("File Size: " + fSize + " Bytes");			
			progressBar();
			
			while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
				fileOutputStream.write(buffer,0,bytes);
				size -= bytes;
				packetsSent += 1;

				double completion = (100 - (100.0 * size / fSize));
//				displayProgress(packetsSent, size, fSize, completion);	
				
				if ((int)completion % interval == 0) {
					if (temp != (int)completion) {
						System.out.print(symbol);
					}
				} 
				temp = (int)completion;
			}
			
			long end = System.currentTimeMillis();
			long exeTime = (end - start);
			
			fileSent(fileName, exeTime, packetsSent);
			
			fileOutputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
