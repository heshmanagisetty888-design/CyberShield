package analyzers;

import java.util.ArrayList;
import java.util.HashMap;

import models.SecurityLog;
import models.ThreatSignature;
import stringalgorithms.AhoCorasick;
import stringalgorithms.KMP;
import stringalgorithms.RabinKarp;
import stringalgorithms.ZAlgorithm;

public class LogAnalyzer {

    private ArrayList<SecurityLog> logs;
    private ArrayList<ThreatSignature> signatures;

    public LogAnalyzer(ArrayList<SecurityLog> logs,
                       ArrayList<ThreatSignature> signatures) {
        this.logs = logs;
        this.signatures = signatures;
    }

    // KMP: count occurrences of a keyword across all log messages
    public int countOccurrences(String keyword) {
        int total = 0;
        for (SecurityLog log : logs) {
            String text = log.getMessage().toLowerCase();
            total += KMP.countOccurrences(text, keyword.toLowerCase());
        }
        return total;
    }

    // Z-algorithm: return logs that match a pattern
    public ArrayList<SecurityLog> findByZ(String pattern) {
        ArrayList<SecurityLog> matches = new ArrayList<SecurityLog>();
        for (SecurityLog log : logs) {
            String text = log.getMessage().toLowerCase();
            ArrayList<Integer> pos = ZAlgorithm.search(text, pattern.toLowerCase());
            if (!pos.isEmpty()) matches.add(log);
        }
        return matches;
    }

    // Rabin-Karp: find all logs matching any keyword
    public ArrayList<SecurityLog> findByRabinKarp(String pattern) {
        ArrayList<SecurityLog> matches = new ArrayList<SecurityLog>();
        for (SecurityLog log : logs) {
            String text = log.getMessage().toLowerCase();
            ArrayList<Integer> pos = RabinKarp.search(text, pattern.toLowerCase());
            if (!pos.isEmpty()) matches.add(log);
        }
        return matches;
    }

    // Aho-Corasick: find all threat signatures in all log messages
    public HashMap<String, Integer> detectAllThreats() {
        AhoCorasick ac = new AhoCorasick();
        for (ThreatSignature t : signatures) {
            ac.addPattern(t.getSignature().toLowerCase());
        }
        ac.build();

        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (SecurityLog log : logs) {
            String text = log.getMessage().toLowerCase();
            HashMap<String, ArrayList<Integer>> result = ac.search(text);
            for (String key : result.keySet()) {
                if (!counts.containsKey(key)) counts.put(key, 0);
                counts.put(key, counts.get(key) + result.get(key).size());
            }
        }
        return counts;
    }
}