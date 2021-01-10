package version2;

import java.io.*;
import java.net.*;

public class Client {
	
	private static DataOutputStream dataOutputStream = null;
	private static DataInputStream dataInputStream = null;
	private static int port = 4444;

	public static void main(String[] args) {
		
		try (Socket socket = new Socket("localhost",port)) {
			System.out.println("Sending file...");
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());

//			sendFile("E:\\s.jpg"); 				// 1 KB
//			sendFile("E:\\testdoc.txt"); 		// 46 KB
//			sendFile("E:\\lisbonuni.png"); 		// 475 KB
//			sendFile("E:\\testMP3.mp3"); 		// 12.521 KB
//			sendFile("E:\\testAOE3.iso");		// 2.552.842 KB 
			sendFile("C:\\Users\\maste\\Downloads\\spiderman2019.mp4");
			
			dataInputStream.close();
			
			System.out.println("File successfully sent.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void sendFile(String path) throws Exception {
		
		int bytes = 0;
		File file = new File(path);
		FileInputStream fileInputStream = new FileInputStream(file);
		
		// send file size
		dataOutputStream.writeLong(file.length()); 
		
		// break file into chunks
		byte[] buffer = new byte[4*2048]; // 8192 bytes
//		byte[] buffer = new byte[4*1024]; // 4096 bytes
		
		while ((bytes = fileInputStream.read(buffer)) != -1) {
			dataOutputStream.write(buffer,0,bytes);
			dataOutputStream.flush();
		}
		fileInputStream.close();
	}
}
