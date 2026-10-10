package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import analyzers.LogAnalyzer;
import analyzers.MalwareAnalyzer;
import analyzers.NetworkAnalyzer;
import analyzers.ThreatAnalyzer;
import dynamicprogramming.BitmaskDP;
import dynamicprogramming.EditDistance;
import dynamicprogramming.MatrixChainMultiplication;
import dynamicprogramming.WeightedEditDistance;
import models.AnalystAlert;
import models.MalwareSignature;
import models.NetworkEdge;
import models.SecurityLog;
import models.ThreatSignature;
import network.BipartiteMatching;
import npcomplete.Clique;
import npcomplete.IndependentSet;
import npcomplete.SATSolver;
import npcomplete.ThreeSAT;
import npcomplete.VertexCover;
import randomized.BloomFilter;
import randomized.MonteCarlo;
import randomized.ReservoirSampling;
import randomized.RandomizedQuickSort;
import reports.SecurityReport;
import suffix.LCP;
import suffix.SAIS;
import suffix.SuffixArray;
import suffix.SuffixTree;
import utils.CSVReader;

public class Main {

    static ArrayList<SecurityLog> logs = new ArrayList<SecurityLog>();
    static ArrayList<ThreatSignature> signatures = new ArrayList<ThreatSignature>();
    static ArrayList<MalwareSignature> malware = new ArrayList<MalwareSignature>();
    static ArrayList<NetworkEdge> network = new ArrayList<NetworkEdge>();
    static ArrayList<AnalystAlert> analysts = new ArrayList<AnalystAlert>();
    static ArrayList<String[]> policies = new ArrayList<String[]>();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:  loadAllData(); break;
                case 2:  threatSignatureDetection(); break;
                case 3:  malwareSimilarityAnalysis(); break;
                case 4:  suffixLogIndexing(); break;
                case 5:  networkAnalysis(); break;
                case 6:  policyAnalysis(); break;
                case 7:  analystAlertMatching(); break;
                case 8:  streamingAnalysis(); break;
                case 9:  runCompleteAnalysis(); break;
                case 10: generateReport(); break;
                case 11: npCompleteAnalysis(); break;
                case 12: dpAnalysis(); break;
                case 0:  running = false; break;
                default: System.out.println("[!] Invalid choice.");
            }
        }
        System.out.println("\nExiting CyberShield. Stay secure!");
    }

    static void printMenu() {
        System.out.println("\n========================================");
        System.out.println("             CYBERSHIELD                ");
        System.out.println("========================================");
        System.out.println("1.  Load All Data");
        System.out.println("2.  Threat Signature Detection");
        System.out.println("3.  Malware Similarity Analysis");
        System.out.println("4.  Suffix / Log Indexing");
        System.out.println("5.  Network Analysis");
        System.out.println("6.  Security Policy Analysis");
        System.out.println("7.  Analyst-Alert Matching");
        System.out.println("8.  Streaming Analysis");
        System.out.println("9.  Run Complete Security Analysis");
        System.out.println("10. Generate Security Report");
        System.out.println("11. NP-Complete Analysis");
        System.out.println("12. Dynamic Programming Analysis");
        System.out.println("0.  Exit");
        System.out.println("========================================");
    }

    static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("[!] Enter a number: ");
            scanner.next();
        }
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }

    // ---------------------------------------------------------
    static void loadAllData() {
        System.out.println("\nLoading all data files...");

        ArrayList<String[]> rows = CSVReader.readCSV("data/security_logs.csv");
        if (rows != null) {
            logs.clear();
            for (int i = 1; i < rows.size(); i++) {
                String[] r = rows.get(i);
                if (r.length < 6) continue;
                logs.add(new SecurityLog(r[0].trim(), r[1].trim(), r[2].trim(),
                                         r[3].trim(), r[4].trim(), r[5].trim()));
            }
        }

        rows = CSVReader.readCSV("data/threat_signatures.csv");
        if (rows != null) {
            signatures.clear();
            for (int i = 1; i < rows.size(); i++) {
                String[] r = rows.get(i);
                if (r.length < 2) continue;
                signatures.add(new ThreatSignature(r[0].trim(), r[1].trim()));
            }
        }

        rows = CSVReader.readCSV("data/malware_signatures.csv");
        if (rows != null) {
            malware.clear();
            for (int i = 1; i < rows.size(); i++) {
                String[] r = rows.get(i);
                if (r.length < 4) continue;
                malware.add(new MalwareSignature(
                        Integer.parseInt(r[0].trim()), r[1].trim(),
                        r[2].trim(), r[3].trim()));
            }
        }

        rows = CSVReader.readCSV("data/network.csv");
        if (rows != null) {
            network.clear();
            for (int i = 1; i < rows.size(); i++) {
                String[] r = rows.get(i);
                if (r.length < 3) continue;
                network.add(new NetworkEdge(r[0].trim(), r[1].trim(),
                                            Integer.parseInt(r[2].trim())));
            }
        }

        rows = CSVReader.readCSV("data/analyst_alerts.csv");
        if (rows != null) {
            analysts.clear();
            for (int i = 1; i < rows.size(); i++) {
                String[] r = rows.get(i);
                if (r.length < 3) continue;
                analysts.add(new AnalystAlert(r[0].trim(), r[1].trim(), r[2].trim()));
            }
        }

        rows = CSVReader.readCSV("data/security_policies.csv");
        if (rows != null) {
            policies.clear();
            for (int i = 1; i < rows.size(); i++) {
                String[] r = rows.get(i);
                if (r.length < 5) continue;
                policies.add(r);
            }
        }

        System.out.println("[OK] Logs: " + logs.size()
                + " | Threats: " + signatures.size()
                + " | Malware: " + malware.size()
                + " | Network: " + network.size()
                + " | Analysts: " + analysts.size()
                + " | Policies: " + policies.size());
    }

    // ---------------------------------------------------------
    static void threatSignatureDetection() {
        if (logs.isEmpty() || signatures.isEmpty()) {
            System.out.println("[!] Please load data first (option 1).");
            return;
        }

        LogAnalyzer la = new LogAnalyzer(logs, signatures);

        System.out.println("\n--- KMP ---");
        System.out.println("LOGIN_FAILED occurrences: "
                + la.countOccurrences("login_failed"));

        System.out.println("\n--- Z Algorithm ---");
        System.out.println("Logs containing 'sql injection': "
                + la.findByZ("sql injection").size());

        System.out.println("\n--- Rabin-Karp ---");
        System.out.println("Logs containing 'ransomware': "
                + la.findByRabinKarp("ransomware").size());

        System.out.println("\n--- Aho-Corasick (all signatures) ---");
        HashMap<String, Integer> detected = la.detectAllThreats();
        for (String key : detected.keySet()) {
            System.out.println("  " + key + " x" + detected.get(key));
        }
    }

    // ---------------------------------------------------------
    static void malwareSimilarityAnalysis() {
        System.out.println("\n--- Malware Similarity Analysis ---");
        MalwareAnalyzer.compare("ransomware", "ransomeware");
        MalwareAnalyzer.compare("trojan", "trogen");
        MalwareAnalyzer.compare("keylogger", "keyloger");
        MalwareAnalyzer.compareWeighted("ransomware", "ransomeware");
    }

    // ---------------------------------------------------------
    static void suffixLogIndexing() {
        if (logs.isEmpty()) {
            System.out.println("[!] Load data first.");
            return;
        }

        // Build a corpus: concatenate all log messages
        StringBuilder sb = new StringBuilder();
        for (SecurityLog l : logs) sb.append(l.getMessage().toLowerCase()).append(" ");
        String corpus = sb.toString();

        System.out.println("\n--- Suffix Array ---");
        int[] sa = SuffixArray.build(corpus);
        System.out.println("Suffix Array size: " + sa.length);
        System.out.println("First 10 suffix indices: ");
        for (int i = 0; i < Math.min(10, sa.length); i++) {
            System.out.println("  index=" + sa[i] + " -> "
                    + corpus.substring(sa[i], Math.min(sa[i] + 30, corpus.length())));
        }

        System.out.println("\n--- LCP Array ---");
        int[] lcp = LCP.build(corpus, sa);
        System.out.println("LCP size: " + lcp.length + " (first 10 shown)");
        for (int i = 0; i < Math.min(10, lcp.length); i++) {
            System.out.println("  lcp[" + i + "] = " + lcp[i]);
        }

        System.out.println("\n--- SA-IS (doubling method) ---");
        int[] sa2 = SAIS.build(corpus);
        System.out.println("SA-IS suffix array size: " + sa2.length);

        System.out.println("\n--- Suffix Tree ---");
        SuffixTree tree = new SuffixTree(corpus);
        System.out.println("Contains 'ransomware'? " + tree.contains("ransomware"));
        System.out.println("Contains 'sql injection'? " + tree.contains("sql injection"));

        System.out.println("\n--- Suffix Array Search ---");
        System.out.println("Occurrences of 'ransomware': "
                + SuffixArray.search(corpus, "ransomware").size());
    }

    // ---------------------------------------------------------
    static void networkAnalysis() {
        if (network.isEmpty()) {
            System.out.println("[!] Load data first.");
            return;
        }

        NetworkAnalyzer na = new NetworkAnalyzer(network);

        // Source = first node, sink = node that is never a source
        String source = network.get(0).getSource();
        String sink = findSink();

        System.out.println("\nSource: " + source + " | Sink: " + sink);

        long t1 = System.nanoTime();
        int ff = na.runFordFulkerson(source, sink);
        long t2 = System.nanoTime();
        int ek = na.runEdmondsKarp(source, sink);
        long t3 = System.nanoTime();
        int din = na.runDinic(source, sink);
        long t4 = System.nanoTime();

        System.out.println("\nFord-Fulkerson Max Flow: " + ff
                + "  (" + (t2 - t1) / 1000 + " us)");
        System.out.println("Edmonds-Karp Max Flow:   " + ek
                + "  (" + (t3 - t2) / 1000 + " us)");
        System.out.println("Dinic Max Flow:          " + din
                + "  (" + (t4 - t3) / 1000 + " us)");

        System.out.println("\nMinimum Cut Value: " + na.runMinCut(source, sink));

        System.out.println("\nCritical Connections:");
        ArrayList<String> crit = na.criticalEdges(source, sink);
        for (String c : crit) System.out.println("  " + c);
    }

    static String findSink() {
        java.util.HashSet<String> sources = new java.util.HashSet<String>();
        java.util.HashSet<String> dests   = new java.util.HashSet<String>();
        for (NetworkEdge e : network) {
            sources.add(e.getSource());
            dests.add(e.getDestination());
        }
        for (String d : dests) {
            if (!sources.contains(d)) return d;
        }
        return network.get(network.size() - 1).getDestination();
    }

    // ---------------------------------------------------------
    static void policyAnalysis() {
        if (policies.isEmpty()) {
            System.out.println("[!] Load data first.");
            return;
        }

        System.out.println("\n--- SAT / 3-SAT Policy Analysis ---");

        ArrayList<String> vars = new ArrayList<String>();
        vars.add("MFA");
        vars.add("VPN");
        vars.add("FIREWALL");
        vars.add("ADMIN_APPROVAL");
        vars.add("ENCRYPTION");

        ArrayList<ArrayList<String>> clauses = new ArrayList<ArrayList<String>>();
        ArrayList<String> c1 = new ArrayList<String>(); c1.add("MFA"); c1.add("VPN");
        ArrayList<String> c2 = new ArrayList<String>(); c2.add("MFA"); c2.add("ADMIN_APPROVAL");
        ArrayList<String> c3 = new ArrayList<String>(); c3.add("VPN"); c3.add("FIREWALL");
        clauses.add(c1); clauses.add(c2); clauses.add(c3);

        boolean sat = SATSolver.solve(clauses, vars);
        System.out.println("SAT (2-CNF policies): " + (sat ? "SATISFIABLE" : "UNSATISFIABLE"));

        ArrayList<ArrayList<String>> clauses3 = new ArrayList<ArrayList<String>>();
        ArrayList<String> d1 = new ArrayList<String>(); d1.add("MFA"); d1.add("VPN"); d1.add("FIREWALL");
        ArrayList<String> d2 = new ArrayList<String>(); d2.add("MFA"); d2.add("!VPN"); d2.add("ADMIN_APPROVAL");
        clauses3.add(d1); clauses3.add(d2);

        boolean sat3 = ThreeSAT.solve(clauses3, vars);
        System.out.println("3-SAT: " + (sat3 ? "SATISFIABLE" : "UNSATISFIABLE"));

        if (sat) {
            HashMap<String, Boolean> assignment = SATSolver.solveWithAssignment(clauses, vars);
            System.out.println("Sample assignment: " + assignment);
        }
    }

    // ---------------------------------------------------------
    static void analystAlertMatching() {
        if (analysts.isEmpty()) {
            System.out.println("[!] Load data first.");
            return;
        }

        System.out.println("\n--- Bipartite Matching: Analysts to Alerts ---");
        BipartiteMatching bm = new BipartiteMatching();
        for (AnalystAlert aa : analysts) {
            bm.addEdge(aa.getAnalyst(), aa.getAlert());
        }
        int matched = bm.maxMatching();
        System.out.println("Maximum matching size: " + matched);

        HashMap<String, String> matches = bm.getMatches();
        for (String alert : matches.keySet()) {
            System.out.println("  " + matches.get(alert) + " -> " + alert);
        }
    }

    // ---------------------------------------------------------
    static void streamingAnalysis() {
        System.out.println("\n--- Streaming Analysis ---");

        // Build a synthetic large stream from our logs
        String[] stream = new String[10000];
        for (int i = 0; i < stream.length; i++) {
            stream[i] = logs.get(i % logs.size()).getEvent() + "_" + i;
        }

        System.out.println("Stream size: " + stream.length + " events");
        ArrayList<String> sample = ReservoirSampling.sample(stream, 10);
        System.out.println("Reservoir sample (10 events):");
        for (String s : sample) System.out.println("  " + s);

        System.out.println("\n--- Bloom Filter ---");
        BloomFilter bf = new BloomFilter(1000);
        for (MalwareSignature m : malware) bf.add(m.getSignature());
        String[] tests = {"ransomware", "trojan", "unknown_malware", "keylogger"};
        for (String t : tests) {
            System.out.println("  " + t + " possibly malicious? " + bf.mightContain(t));
        }

        System.out.println("\n--- Monte Carlo Simulation ---");
        double prob = MonteCarlo.simulate(5000, 10, 0.3, 4);
        System.out.println("Probability of >=4 suspicious events in 10 trials: "
                + String.format("%.2f%%", prob * 100));
    }

    // ---------------------------------------------------------
    static void npCompleteAnalysis() {
        System.out.println("\n--- NP-Complete Analysis: Threat Relationships ---");

        // Small threat relationship graph
        String[] names = {"ransomware", "trojan", "keylogger", "phishing", "sql"};
        boolean[][] g = new boolean[5][5];
        // r-t, r-k, t-k, p-sql
        g[0][1] = g[1][0] = true;
        g[0][2] = g[2][0] = true;
        g[1][2] = g[2][1] = true;
        g[3][4] = g[4][3] = true;

        System.out.println("Threats: ransomware, trojan, keylogger, phishing, sql");
        System.out.println("Max Clique size:            " + Clique.maxCliqueSize(g));
        System.out.println("Max Independent Set size:   " + IndependentSet.maxIndependentSetSize(g));
        System.out.println("Min Vertex Cover size:      " + VertexCover.minVertexCoverSize(g));
    }

    // ---------------------------------------------------------
    static void dpAnalysis() {
        System.out.println("\n--- Dynamic Programming Analysis ---");

        System.out.println("Edit Distance 'ransomware' vs 'ransomeware': "
                + EditDistance.compute("ransomware", "ransomeware"));

        System.out.println("Weighted Edit Distance (1,1,2): "
                + WeightedEditDistance.compute("ransomware", "ransomeware", 1, 1, 2));

        String[] events = {"FAILED_LOGIN", "PORT_SCAN", "PRIV_ESC", "FILE_MOD"};
        int mask = BitmaskDP.maskOf(new String[]{"FAILED_LOGIN", "PORT_SCAN"}, events);
        System.out.println("Bitmask for FAILED_LOGIN + PORT_SCAN: "
                + BitmaskDP.toBinary(mask, 4) + " = " + mask);

        int[] dims = {10, 20, 30, 40, 50};
        System.out.println("Matrix Chain Multiplication min cost: "
                + MatrixChainMultiplication.compute(dims));
        System.out.println("Optimal order: " + MatrixChainMultiplication.order(dims));
    }

    // ---------------------------------------------------------
    static void runCompleteAnalysis() {
        if (logs.isEmpty()) {
            System.out.println("[!] Load data first.");
            return;
        }
        System.out.println("\n=========== RUNNING COMPLETE ANALYSIS ===========");
        threatSignatureDetection();
        malwareSimilarityAnalysis();
        networkAnalysis();
        policyAnalysis();
        analystAlertMatching();
        streamingAnalysis();
        npCompleteAnalysis();
        dpAnalysis();
        System.out.println("\n=========== COMPLETE ANALYSIS DONE ===========");
    }

    // ---------------------------------------------------------
    static void generateReport() {
        if (logs.isEmpty()) {
            System.out.println("[!] Load data first.");
            return;
        }
        SecurityReport.generate(logs, signatures, network);
    }
}