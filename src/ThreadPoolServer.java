import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolServer {
    ExecutorService es;
    private static final Integer PORT = 8010;
    private static final Integer TIMEOUT_IN_MILLIS = 10*1000;

    public ThreadPoolServer(){
        es = new ThreadPoolExecutor(16, // Core thread pool size
                50, // Maximum thread pool size
                60L, TimeUnit.SECONDS, // Keep-alive time for idle threads
                new ArrayBlockingQueue<>(50), // Request queue with 50 capacity
                new ThreadPoolExecutor.CallerRunsPolicy() // Rejection policy (explained below)
        );
    }
    int counter = 0;
    public void run() throws Exception {
        ServerSocket socket = new ServerSocket(PORT);
        while(true){
            System.out.println("System is listening at port : " + PORT);
            Socket clientSocket = socket.accept();
            ClientService clientService = new ClientService();
            counter++;
            System.out.println("Counter: " + counter);
            es.submit(()->clientService.handleClient(clientSocket));
        }
    }

}
