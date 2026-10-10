package npcomplete;

public class Clique {

    // Check whether a clique of size k exists
    public static boolean hasClique(boolean[][] graph, int k) {
        int n = graph.length;
        int total = 1 << n;
        for (int mask = 0; mask < total; mask++) {
            if (countBits(mask) != k) continue;
            if (isClique(graph, mask, n)) return true;
        }
        return false;
    }

    private static boolean isClique(boolean[][] graph, int mask, int n) {
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) == 0) continue;
            for (int j = i + 1; j < n; j++) {
                if ((mask & (1 << j)) == 0) continue;
                if (!graph[i][j]) return false;
            }
        }
        return true;
    }

    public static int maxCliqueSize(boolean[][] graph) {
        int n = graph.length;
        int best = 0;
        int total = 1 << n;
        for (int mask = 0; mask < total; mask++) {
            int bits = countBits(mask);
            if (bits <= best) continue;
            if (isClique(graph, mask, n)) best = bits;
        }
        return best;
    }

    private static int countBits(int mask) {
        int c = 0;
        while (mask > 0) { c += mask & 1; mask >>= 1; }
        return c;
    }
}