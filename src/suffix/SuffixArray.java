package suffix;

import java.util.Arrays;

public class SuffixArray {

    // Build suffix array using simple sorting (educational)
    public static int[] build(String s) {
        int n = s.length();
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;

        final String text = s;
        Arrays.sort(indices, (a, b) -> {
            // Compare suffixes starting at a and b
            int i = a, j = b;
            while (i < text.length() && j < text.length()) {
                if (text.charAt(i) != text.charAt(j)) {
                    return text.charAt(i) - text.charAt(j);
                }
                i++;
                j++;
            }
            return (text.length() - a) - (text.length() - b);
        });

        int[] sa = new int[n];
        for (int i = 0; i < n; i++) sa[i] = indices[i];
        return sa;
    }

    // Find all occurrences of pattern using suffix array + binary search
    public static java.util.ArrayList<Integer> search(String text, String pattern) {
        java.util.ArrayList<Integer> positions = new java.util.ArrayList<Integer>();
        int[] sa = build(text);
        int low = 0, high = text.length() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = compare(text, sa[mid], pattern);
            if (cmp == 0) {
                positions.add(sa[mid]);
                // check neighbours
                int left = mid - 1;
                while (left >= 0 && compare(text, sa[left], pattern) == 0) {
                    positions.add(sa[left]);
                    left--;
                }
                int right = mid + 1;
                while (right < text.length() && compare(text, sa[right], pattern) == 0) {
                    positions.add(sa[right]);
                    right++;
                }
                break;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return positions;
    }

    // Compare text starting at position with pattern
    private static int compare(String text, int pos, String pattern) {
        int i = pos, j = 0;
        while (i < text.length() && j < pattern.length()) {
            if (text.charAt(i) != pattern.charAt(j)) {
                return text.charAt(i) - pattern.charAt(j);
            }
            i++;
            j++;
        }
        if (j == pattern.length()) return 0;
        return -1;
    }
}