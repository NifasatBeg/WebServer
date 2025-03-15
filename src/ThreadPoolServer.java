import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolServer {
    ExecutorService es;
    private static final Integer PORT = 8010;
    private static final Integer TIMEOUT_IN_MILLIS = 10*1000;
    private static final int NUM_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public ThreadPoolServer() {
        // Use a more optimal configuration
        es = new ThreadPoolExecutor(
                NUM_PROCESSORS,              // Core threads equal to CPU cores
                NUM_PROCESSORS * 4,          // Max threads scaled based on CPU cores
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),    // Immediate handoff, no queuing
                r -> {                       // Custom thread factory
                    Thread t = new Thread(r);
                    t.setDaemon(true);       // Use daemon threads to avoid blocking JVM shutdown
                    return t;
                },
                new ThreadPoolExecutor.CallerRunsPolicy() // Throttles by making submitter run the task
        );
        ((ThreadPoolExecutor)es).prestartAllCoreThreads();

        // Allow core threads to time out when idle to reclaim resources
        ((ThreadPoolExecutor)es).allowCoreThreadTimeOut(true);
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
