package dynamicprogramming;

public class WeightedEditDistance {

    public static int compute(String a, String b,
                              int insertCost, int deleteCost, int replaceCost) {
        int n = a.length();
        int m = b.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) dp[i][0] = i * deleteCost;
        for (int j = 0; j <= m; j++) dp[0][j] = j * insertCost;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int replace = dp[i - 1][j - 1] + replaceCost;
                    int delete  = dp[i - 1][j]     + deleteCost;
                    int insert  = dp[i][j - 1]     + insertCost;
                    dp[i][j] = Math.min(replace, Math.min(delete, insert));
                }
            }
        }
        return dp[n][m];
    }
}