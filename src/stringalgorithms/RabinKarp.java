package stringalgorithms;

import java.util.ArrayList;

public class RabinKarp {

    private static final int BASE = 256;    // number of characters
    private static final int MOD  = 101;    // a prime number

    public static ArrayList<Integer> search(String text, String pattern) {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        int n = text.length();
        int m = pattern.length();
        if (m == 0 || n < m) return positions;

        int patternHash = 0;
        int textHash = 0;
        int h = 1;

        // h = BASE^(m-1) % MOD
        for (int i = 0; i < m - 1; i++) {
            h = (h * BASE) % MOD;
        }

        // Initial hash values
        for (int i = 0; i < m; i++) {
            patternHash = (BASE * patternHash + pattern.charAt(i)) % MOD;
            textHash    = (BASE * textHash    + text.charAt(i))    % MOD;
        }

        // Slide over text
        for (int i = 0; i <= n - m; i++) {
            if (patternHash == textHash) {
                // Check characters one by one (avoid false positives)
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) positions.add(i);
            }
            // Rolling hash update for next window
            if (i < n - m) {
                textHash = (BASE * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % MOD;
                if (textHash < 0) textHash += MOD;
            }
        }
        return positions;
    }
}