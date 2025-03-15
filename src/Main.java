import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // seq 500 | xargs -P 50 -I {} curl -o /dev/null -s -w "Request {}: %{time_total} s\n" http://localhost:8010

        SingleThreadedServer singleThreadedServer = new SingleThreadedServer(); // takes around 6 secs
        MultiThreadedServer multiThreadedServer = new MultiThreadedServer();
        ThreadPoolServer threadPoolServer = new ThreadPoolServer();
        try {
             multiThreadedServer.run();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}