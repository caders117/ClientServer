import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Client {
	Socket soc;
	PrintWriter out;
	BufferedReader in;
	
	public Client(String addr, int port) {
		try {
			soc = new Socket(addr, port);
			System.out.println("Connected to " + addr + ":" + port);
			try {
				out = new PrintWriter(soc.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
				Thread input = new Thread(new ProcessInput());
				Thread output = new Thread(new ProcessOutput());
				input.start();
				output.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Connection failed");
		}
		
	}
	
	private class ProcessOutput implements Runnable {
		Scanner scan = new Scanner(System.in);
		String send = "";

		@Override
		public void run() {
			while(true) {
				send = scan.nextLine();
				out.println("Client: " + send);
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
