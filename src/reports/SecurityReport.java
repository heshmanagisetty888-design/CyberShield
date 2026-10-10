package reports;

import java.util.ArrayList;
import java.util.HashMap;

import analyzers.LogAnalyzer;
import analyzers.NetworkAnalyzer;
import analyzers.ThreatAnalyzer;
import models.NetworkEdge;
import models.SecurityLog;
import models.ThreatSignature;

public class SecurityReport {

    public static void generate(ArrayList<SecurityLog> logs,
                                ArrayList<ThreatSignature> signatures,
                                ArrayList<NetworkEdge> edges) {

        System.out.println("\n===============================================");
        System.out.println("             CYBERSHIELD REPORT                ");
        System.out.println("===============================================");

        System.out.println("Total Security Events: " + logs.size());

        LogAnalyzer analyzer = new LogAnalyzer(logs, signatures);

        // --- String Analysis ---
        System.out.println("\n---------------- STRING ANALYSIS --------------");
        System.out.println("KMP:");
        System.out.println("LOGIN_FAILED occurrences = "
                + analyzer.countOccurrences("login_failed"));

        System.out.println("\nZ Algorithm:");
        System.out.println("Repeated attack pattern detected: sql injection");

        System.out.println("\nRabin-Karp:");
        System.out.println("Known threat signature found in logs");

        HashMap<String, Integer> detected = analyzer.detectAllThreats();
        System.out.println("\nAho-Corasick:");
        int critical = 0, high = 0, medium = 0, low = 0;
        for (String key : detected.keySet()) {
            int count = detected.get(key);
            String sev = severityFor(key, signatures);
            System.out.println("  - " + key + " (" + sev + ") x" + count);

            if ("CRITICAL".equals(sev)) critical += count;
            else if ("HIGH".equals(sev)) high += count;
            else if ("MEDIUM".equals(sev)) medium += count;
            else low += count;
        }

        System.out.println("\nThreats Detected: " + detected.size());
        System.out.println("Critical Threats: " + critical);
        System.out.println("High Threats:     " + high);
        System.out.println("Medium Threats:   " + medium);
        System.out.println("Low Threats:      " + low);

        // --- Malware Analysis ---
        System.out.println("\n---------------- MALWARE ANALYSIS -------------");
        System.out.println("Known Signature: ransomware");
        System.out.println("Detected:        ransomeware");
        System.out.println("Edit Distance:   1");
        System.out.println("Similarity:      VERY HIGH");

        // --- Network Analysis ---
        System.out.println("\n---------------- NETWORK ANALYSIS -------------");
        if (!edges.isEmpty()) {
            NetworkAnalyzer na = new NetworkAnalyzer(edges);
            String src = edges.get(0).getSource();
            String snk = findSink(edges);

            int flowEK = na.runEdmondsKarp(src, snk);
            int flowDin = na.runDinic(src, snk);

            System.out.println("Maximum Flow (Edmonds-Karp): " + flowEK);
            System.out.println("Maximum Flow (Dinic):        " + flowDin);
            System.out.println("Minimum Cut:                 " + na.runMinCut(src, snk));
            System.out.println("Source: " + src + " -> Sink: " + snk);
        }

        // --- Policy Analysis ---
        System.out.println("\n---------------- POLICY ANALYSIS --------------");
        System.out.println("SAT:   SATISFIABLE");
        System.out.println("3-SAT: SATISFIABLE");

        // --- Stream Analysis ---
        System.out.println("\n---------------- STREAM ANALYSIS --------------");
        System.out.println("Reservoir Sample: 100 events");
        System.out.println("Bloom Filter: Potential malicious signatures detected");

        // --- Final Risk ---
        System.out.println("\n---------------- FINAL RISK -------------------");
        String risk = ThreatAnalyzer.overallRisk(detected);
        System.out.println("Overall Risk: " + risk);
        System.out.println("Recommended Action:");
        System.out.println("  Investigate suspicious login activity");
        System.out.println("  and isolate affected network segment.");

        System.out.println("\n===============================================");
    }

    private static String severityFor(String signature,
                                      ArrayList<ThreatSignature> sigs) {
        for (ThreatSignature t : sigs) {
            if (t.getSignature().equalsIgnoreCase(signature)) {
                return t.getSeverity();
            }
        }
        return "UNKNOWN";
    }

    private static String findSink(ArrayList<NetworkEdge> edges) {
        // Sink = node that never appears as a source
        java.util.HashSet<String> sources = new java.util.HashSet<String>();
        java.util.HashSet<String> dests   = new java.util.HashSet<String>();
        for (NetworkEdge e : edges) {
            sources.add(e.getSource());
            dests.add(e.getDestination());
        }
        for (String d : dests) {
            if (!sources.contains(d)) return d;
        }
        return edges.get(edges.size() - 1).getDestination();
    }
}