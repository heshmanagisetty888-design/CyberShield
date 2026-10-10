package randomized;

import java.util.Random;

public class MonteCarlo {

    // Simulate: given probability p of a suspicious event per trial,
    // what's the chance at least k events happen in n trials?
    public static double simulate(int trials, int eventsPerTrial,
                                  double probability, int requiredSuccesses) {

        Random rand = new Random();
        int successTrials = 0;

        for (int t = 0; t < trials; t++) {
            int successes = 0;
            for (int e = 0; e < eventsPerTrial; e++) {
                if (rand.nextDouble() < probability) {
                    successes++;
                }
            }
            if (successes >= requiredSuccesses) successTrials++;
        }
        return (double) successTrials / trials;
    }
}