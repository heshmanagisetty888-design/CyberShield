package stringalgorithms;

import java.util.ArrayList;

public class ZAlgorithm {

    // Build Z array: Z[i] = length of longest substring starting at i that
    // matches prefix of s
    public static int[] buildZ(String s) {
        int n = s.length();
        int[] z = new int[n];
        int l = 0, r = 0;

        for (int i = 1; i < n; i++) {
            if (i <= r) {
                z[i] = Math.min(r - i + 1, z[i - l]);
            }
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }
        return z;
    }

    // Search pattern in text using Z algorithm
    // Combine pattern + "$" + text and look for Z values == pattern length
    public static ArrayList<Integer> search(String text, String pattern) {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        if (pattern.isEmpty() || text.isEmpty()) return positions;

        String combined = pattern + "$" + text;
        int[] z = buildZ(combined);
        int m = pattern.length();

        for (int i = 0; i < z.length; i++) {
            if (z[i] == m) {
                // Position in original text: i - m - 1
                positions.add(i - m - 1);
            }
        }
        return positions;
    }
}