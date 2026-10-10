package npcomplete;

public class VertexCover {

    // Brute force minimum vertex cover (educational)
    public static int minVertexCoverSize(boolean[][] graph) {
        int n = graph.length;
        int total = 1 << n;
        int best = n;

        for (int mask = 0; mask < total; mask++) {
            int bits = countBits(mask);
            if (bits >= best) continue;
            if (isVertexCover(graph, mask, n)) best = bits;
        }
        return best;
    }

    private static boolean isVertexCover(boolean[][] graph, int mask, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (graph[i][j]) {
                    boolean covered = ((mask & (1 << i)) != 0)
                                   || ((mask & (1 << j)) != 0);
                    if (!covered) return false;
                }
            }
        }
        return true;
    }

    private static int countBits(int mask) {
        int c = 0;
        while (mask > 0) { c += mask & 1; mask >>= 1; }
        return c;
    }
}