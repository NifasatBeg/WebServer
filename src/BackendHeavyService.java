import java.util.Random;

public class BackendHeavyService {
    private final Random random = new Random();

    public void doSomething() {
        System.out.println("Starting CPU-intensive operation on " + Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();

        // CPU-intensive operations that can't be optimized away
        double result = 0;
        for (int i = 0; i < 10_000_000; i++) {
            // Math operations that must be computed (can't be optimized away)
            result += Math.sin(i * 0.01) * Math.cos(i * 0.01);

            // Occasional random operations to prevent loop optimization
            if (i % 100_000 == 0) {
                result += Math.sqrt(random.nextDouble() * 100);
            }
        }

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("CPU operation completed on " + Thread.currentThread().getName() +
                " in " + duration + "ms with result: " + result);
    }
}