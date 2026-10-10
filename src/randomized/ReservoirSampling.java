package randomized;

import java.util.ArrayList;
import java.util.Random;

public class ReservoirSampling {

    // Sample k items from a stream of n items without knowing n in advance
    public static ArrayList<String> sample(String[] stream, int k) {
        Random rand = new Random();
        ArrayList<String> reservoir = new ArrayList<String>();

        for (int i = 0; i < stream.length; i++) {
            if (i < k) {
                reservoir.add(stream[i]);
            } else {
                int j = rand.nextInt(i + 1);
                if (j < k) {
                    reservoir.set(j, stream[i]);
                }
            }
        }
        return reservoir;
    }
}