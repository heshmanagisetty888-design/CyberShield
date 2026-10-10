package dynamicprogramming;

public class EditDistance {

    public static int compute(String a, String b) {
        int n = a.length();
        int m = b.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int replace = dp[i - 1][j - 1] + 1;
                    int delete  = dp[i - 1][j] + 1;
                    int insert  = dp[i][j - 1] + 1;
                    dp[i][j] = Math.min(replace, Math.min(delete, insert));
                }
            }
        }
        return dp[n][m];
    }

    // Similarity as percentage
    public static double similarity(String a, String b) {
        int maxLen = Math.max(a.length(), b.length());
        if (maxLen == 0) return 100.0;
        int dist = compute(a, b);
        return (1.0 - (double) dist / maxLen) * 100.0;
    }
}