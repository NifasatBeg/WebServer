import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadedServer {
    private static final Integer PORT = 8010;
//    private static final Integer TIMEOUT_IN_MILLIS = 10*1000;
    public void run() throws Exception {
        int counter = 0;
        ServerSocket socket = new ServerSocket(PORT);
        while(true){
            System.out.println("System is listening at port : " + PORT);
            Socket clientSocket = socket.accept();
            counter++;
            System.out.println("Counter: " + counter);
            ClientService clientService = new ClientService();
            clientService.handleClient(clientSocket);
        }
    }
}
