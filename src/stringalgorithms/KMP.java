package stringalgorithms;

import java.util.ArrayList;

public class KMP {

    // Build the LPS (longest proper prefix that is also suffix) array
    public static int[] buildLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    // Returns all starting positions of pattern inside text
    public static ArrayList<Integer> search(String text, String pattern) {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        if (pattern.isEmpty() || text.isEmpty()) return positions;

        int[] lps = buildLPS(pattern);
        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }
            if (j == pattern.length()) {
                positions.add(i - j);
                j = lps[j - 1];
            } else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return positions;
    }

    public static int countOccurrences(String text, String pattern) {
        return search(text, pattern).size();
    }
}