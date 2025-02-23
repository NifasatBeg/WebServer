import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BackendHeavyService {
    public void doSomething(){
        try {
            System.out.println("Simulating heavy I/O");
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(1000);
            System.out.println("I/O operation completed.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
