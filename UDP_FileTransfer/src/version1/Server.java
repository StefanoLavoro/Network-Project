package version1;

import java.io.*;
import java.net.*;

public class Server {
	private static DataOutputStream dataOutputStream = null;
	private static DataInputStream dataInputStream = null;

	public static void main(String[] args) {
		try(ServerSocket ss = new ServerSocket(5000)) {
			System.out.println("listening to port: " + ss.getLocalPort());
			Socket cs = ss.accept();
			System.out.println(cs + " connected.");
			dataInputStream = new DataInputStream(cs.getInputStream());
			dataOutputStream = new DataOutputStream(cs.getOutputStream());

			receiveFile("C:\\Users\\maste\\Documents\\20-Coding\\Java\\testdoc_copy.txt"); // 46 KB
			receiveFile("C:\\Users\\maste\\Documents\\20-Coding\\Java\\lisbonuni_copy.png"); // 475 KB
			receiveFile("C:\\Users\\maste\\Documents\\20-Coding\\Java\\testMP3_copy.mp3"); // 12.521 KB
			receiveFile("C:\\Users\\maste\\Documents\\20-Coding\\Java\\bigfile_copy.mp4"); // 2.118.541 KB

			dataInputStream.close();
			dataOutputStream.close();
			cs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void receiveFile(String fileName) throws Exception {
		int bytes = 0;
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);
		
		long size = dataInputStream.readLong();		// read file size
		byte[] buffer = new byte[4*1024];
		while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
			fileOutputStream.write(buffer,0,bytes);
			size -= bytes;		// read upto file size
		}
		fileOutputStream.close();
	}
}
