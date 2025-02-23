import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {
    private static final Integer PORT = 8010;
    private static final Integer TIMEOUT_IN_MILLIS = 10*1000;
    int counter = 0;
    public void run() throws Exception {
        ServerSocket socket = new ServerSocket(PORT);
        while(true){
            System.out.println("System is listening at port : " + PORT);
            Socket clientSocket = socket.accept();
            counter++;
            System.out.println("Counter: " + counter);
            ClientService clientService = new ClientService();
            Thread thread = new Thread(()->clientService.handleClient(clientSocket));
            thread.start();
        }
    }
}
