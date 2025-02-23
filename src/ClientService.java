import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientService {
    public void handleClient(Socket socket) {
        try {
            System.out.println("Client connected: " + socket.getInetAddress());
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter toClient = new PrintWriter(socket.getOutputStream()); // Auto-flush enabled

            String clientData = fromClient.readLine();
            if (clientData == null) {
                System.out.println("Client disconnected before sending data.");
                return; // Prevent further processing
            }
            System.out.println("Received from client: " + clientData);

            // Read file contents
            BackendHeavyService backendHeavyService = new BackendHeavyService();
            backendHeavyService.doSomething();

            String response = "Request Processed\n";

            // Send response
            toClient.print(response);
            toClient.flush();
            toClient.close();
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
