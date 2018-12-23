package com.demo;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BioMain {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket=new ServerSocket(9999);
		while (true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("connect from:" + clientSocket.getRemoteSocketAddress());
			InputStream inputStream = clientSocket.getInputStream();
			try(Scanner scaner = new Scanner(inputStream)){
				while (true) {
					String request = scaner.nextLine();
					System.out.println(request);
					if ("quit".equals(request)) {
						break;
					}
					String response = "from bio request:" + request + "\n";
					clientSocket.getOutputStream().write(response.getBytes());
				} 
			}
		}
	}
}