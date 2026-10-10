package suffix;

// Simplified SA-IS educational version: builds suffix array via
// suffix doubling (O(n log n)). SA-IS proper is O(n) but very
// complex. This version is correct and understandable.
public class SAIS {

    public static int[] build(String s) {
        int n = s.length();
        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] temp = new int[n];

        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = s.charAt(i);
        }

        for (int k = 1; k < n; k *= 2) {
            final int kk = k;
            final int[] r = rank;
            Integer[] idx = new Integer[n];
            for (int i = 0; i < n; i++) idx[i] = sa[i];

            java.util.Arrays.sort(idx, (a, b) -> {
                if (r[a] != r[b]) return r[a] - r[b];
                int ra = a + kk < n ? r[a + kk] : -1;
                int rb = b + kk < n ? r[b + kk] : -1;
                return ra - rb;
            });

            for (int i = 0; i < n; i++) sa[i] = idx[i];

            temp[sa[0]] = 0;
            for (int i = 1; i < n; i++) {
                int a = sa[i - 1], b = sa[i];
                int ra = a + kk < n ? rank[a + kk] : -1;
                int rb = b + kk < n ? rank[b + kk] : -1;
                if (rank[a] != rank[b] || ra != rb) {
                    temp[b] = temp[a] + 1;
                } else {
                    temp[b] = temp[a];
                }
            }
            for (int i = 0; i < n; i++) rank[i] = temp[i];
        }
        return sa;
    }
}