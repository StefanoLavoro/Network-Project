package version1;

import java.io.*;
import java.net.*;

public class Client {
	private static DataOutputStream dataOutputStream = null;
	private static DataInputStream dataInputStream = null;

	public static void main(String[] args) {
		try(Socket socket = new Socket("localhost",5000)) {
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());

			sendFile("C:\\Users\\maste\\Documents\\20-Coding\\Java\\testdoc.txt"); // 46 KB
			sendFile("C:\\Users\\maste\\Documents\\20-Coding\\Java\\lisbonuni.png"); // 475 KB
			sendFile("C:\\Users\\maste\\Documents\\20-Coding\\Java\\testMP3.mp3"); // 12.521 KB
			sendFile("C:\\Users\\maste\\Documents\\20-Coding\\Java\\bigfile.mp4"); // 2.118.541 KB
			
//			dataInputStream.close();
//			dataInputStream.close();
//			dataInputStream.close();
			dataInputStream.close();
		} catch (Exception e){
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
		byte[] buffer = new byte[4*1024];
		while ((bytes=fileInputStream.read(buffer))!=-1) {
			dataOutputStream.write(buffer,0,bytes);
			dataOutputStream.flush();
		}
		fileInputStream.close();
	}
}
