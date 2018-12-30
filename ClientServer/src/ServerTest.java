import java.io.IOException;

public class ServerTest {

	public static void main(String[] args) {
		try {
			Server s = new Server(8888);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}