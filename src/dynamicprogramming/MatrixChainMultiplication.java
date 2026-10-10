package dynamicprogramming;

public class MatrixChainMultiplication {

    // Returns minimum number of multiplications needed
    public static int compute(int[] dims) {
        int n = dims.length;
        int[][] dp = new int[n][n];

        for (int len = 2; len < n; len++) {
            for (int i = 1; i < n - len + 1; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j]
                            + dims[i - 1] * dims[k] * dims[j];
                    if (cost < dp[i][j]) dp[i][j] = cost;
                }
            }
        }
        return dp[1][n - 1];
    }

    // Print optimal parenthesization
    public static String order(int[] dims) {
        int n = dims.length;
        int[][] dp = new int[n][n];
        int[][] split = new int[n][n];

        for (int len = 2; len < n; len++) {
            for (int i = 1; i < n - len + 1; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j]
                            + dims[i - 1] * dims[k] * dims[j];
                    if (cost < dp[i][j]) {
                        dp[i][j] = cost;
                        split[i][j] = k;
                    }
                }
            }
        }
        return buildOrder(split, 1, n - 1);
    }

    private static String buildOrder(int[][] split, int i, int j) {
        if (i == j) return "A" + i;
        return "(" + buildOrder(split, i, split[i][j])
                + " x " + buildOrder(split, split[i][j] + 1, j) + ")";
    }
}