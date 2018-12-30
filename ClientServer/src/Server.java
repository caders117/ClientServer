import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	ServerSocket ss;
	Socket client;
	PrintWriter out;
	BufferedReader in;
	
	public Server(int port) throws IOException {
		try {
			ss = new ServerSocket(port);
			System.out.println("Server created at port " + port);
			try {
				client = ss.accept();
				out = new PrintWriter(client.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				Thread input = new Thread(new ProcessInput());
				Thread output = new Thread(new ProcessOutput());
				input.start();
				output.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Port " + port + " not available for use.");
		}
	}
	
	private class ProcessOutput implements Runnable {
		Scanner scan = new Scanner(System.in);
		String send = "";

		@Override
		public void run() {
			while(true) {
				send = scan.nextLine();
				out.println("Server: " + send);
			}
		}
	}
	
	private class ProcessInput implements Runnable {
		String input;
		
		@Override
		public void run() {
			while(true) {
				try {
					input = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(input != null)
					System.out.println(input);
			}			
		}
	}
}
