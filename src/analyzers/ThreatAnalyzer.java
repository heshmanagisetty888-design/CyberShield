package analyzers;

import java.util.HashMap;

public class ThreatAnalyzer {

    public static int severityScore(String severity) {
        if (severity == null) return 0;
        severity = severity.toUpperCase();
        if (severity.equals("CRITICAL")) return 100;
        if (severity.equals("HIGH"))     return 80;
        if (severity.equals("MEDIUM"))   return 50;
        if (severity.equals("LOW"))      return 20;
        return 0;
    }

    public static String scoreToLevel(int score) {
        if (score >= 85) return "CRITICAL";
        if (score >= 65) return "HIGH";
        if (score >= 40) return "MEDIUM";
        if (score >= 15) return "LOW";
        return "SAFE";
    }

    // Combine: severity + frequency * 2 + signature count * 3
    public static int computeThreatScore(String severity, int frequency,
                                         int signatureMatches) {
        int base = severityScore(severity);
        return base + (frequency * 2) + (signatureMatches * 3);
    }

    // Sum up all scores into an overall risk level
    public static String overallRisk(HashMap<String, Integer> threats) {
        int total = 0;
        for (String key : threats.keySet()) {
            int freq = threats.get(key);
            total += computeThreatScore(key.toUpperCase(), freq, 1);
        }
        if (total >= 400) return "CRITICAL";
        if (total >= 250) return "HIGH";
        if (total >= 120) return "MEDIUM";
        if (total >= 40)  return "LOW";
        return "SAFE";
    }
}