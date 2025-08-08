package com.example.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class WebServer {

	private static final int PORT = 8080;
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Server started at http://localhost:" + PORT);
			
			while (true) {
				Socket clientSocket = serverSocket.accept();
				threadPool.execute(() -> handleClient(clientSocket));
			}
		} catch (IOException e) {
			System.out.println("Error starting the server: " + e.getMessage());
		}
	}
	
	private static void handleClient(Socket clientSocket) {
		try (
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			OutputStream out = clientSocket.getOutputStream();
		) {
			String requestLine = in.readLine();
			System.out.println("Reduced request:" + requestLine);
		
			while (in.readLine() != null && !in.readLine().isEmpty()) {				
				String responseBody = "Hello from the web server";
				String httpResponse =
				"HTTP/1.1 200 OK/r/n" +
				"Content-Type: text/plain; charset=UTF-8\r\n" +
				"Content-Length: " + responseBody.getBytes().length + "\r\n" +
				"\r\n" +
				responseBody;
			out.write(httpResponse.getBytes());
			out.flush();
		}

	} catch (IOException e) {
			System.err.println("Error handling client: " + e.getMessage());
		} finally {
			try {
				clientSocket.close();
			} catch (IOException ignored) {}
		}
	}
}