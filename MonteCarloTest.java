import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ThreadLocalRandom;

public class MonteCarloTest {

    public static AtomicLong oTotalSuccesses = new AtomicLong(0);
    // public static ThreadLocalRandom oRnd = new ThreadLocalRandom();

    public static void main(String[] args) {
        MonteCarlo m = new MonteCarlo(args[0], args[1]);
    }

    public static class MonteCarlo {

        public MonteCarlo(String pThreads, String pTests) {
            long vNumOfThreads = Long.parseLong(pThreads);
            long vNumOfTests = Long.parseLong(pTests);

            int vIterations = (int) vNumOfThreads;

            long vTestsPerThread = vNumOfTests / vNumOfThreads;

            TestThread[] vThreads = new TestThread[vIterations];

            double vRatio;
            double vPi;

            for(int vIndex = 0; vIndex < vIterations; vIndex++)
            {
                vThreads[vIndex] = new TestThread();
                vThreads[vIndex].run(vTestsPerThread);
            }

            for(int vIndex = 0; vIndex < vIterations; vIndex++)
            {
                try
                {
                    vThreads[vIndex].join();
                }
                catch(InterruptedException pException) {}
            }

            vRatio = (double) oTotalSuccesses.get() / vNumOfTests;
            vPi = vRatio * 4;

            System.out.println("Total: " + vNumOfTests);
            System.out.println("Inside: " + oTotalSuccesses);
            System.out.println("Ratio: " + vRatio);
            System.out.println("Pi: " + vPi);
        }

        public class TestThread extends Thread {

            public void run() {
                run(0);
            }

            public void run(long pNumOfTests) {
                // ThreadLocalRandom vRnd = new ThreadLocalRandom();
                int vSuccesses = 0;
                double vRndX = 0;
                double vRndY = 0;

                for(long vIndex = 0; vIndex < pNumOfTests; vIndex++)
                {
                    vRndX = ThreadLocalRandom.current().nextDouble(0, 1);
                    vRndY = ThreadLocalRandom.current().nextDouble(0, 1);

                    if((vRndX * vRndX) + (vRndY * vRndY) < 1)
                    {
                        vSuccesses++;
                    }
                }

                oTotalSuccesses.addAndGet(vSuccesses);
            }
        }
    }
}