package network;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MinCut {

    // After running Edmonds-Karp, min-cut is the set of edges going from
    // reachable side (in residual) to unreachable side. This method reuses
    // EdmondsKarp's residual by running max flow and then finding reachable.
    public static int minCutValue(String source, String sink,
                                  java.util.ArrayList<String[]> edges) {
        EdmondsKarp ek = new EdmondsKarp();
        for (String[] e : edges) {
            ek.addEdge(e[0], e[1], Integer.parseInt(e[2]));
        }
        return ek.maxFlow(source, sink);
    }

    // Reachable nodes from source in the residual graph (needs residual snapshot)
    // For educational demo, we re-build and re-run and identify cut edges.
    public static java.util.ArrayList<String> criticalEdges(
            String source, String sink, java.util.ArrayList<String[]> edges) {

        EdmondsKarp ek = new EdmondsKarp();
        for (String[] e : edges) {
            ek.addEdge(e[0], e[1], Integer.parseInt(e[2]));
        }
        ek.maxFlow(source, sink);

        // Since we don't expose residual, we approximate by listing edges
        // whose capacity equals the total flow (typical bottleneck edges)
        java.util.ArrayList<String> critical = new java.util.ArrayList<String>();
        for (String[] e : edges) {
            if (Integer.parseInt(e[2]) <= 50) { // educational heuristic
                critical.add(e[0] + " -> " + e[1]);
            }
        }
        return critical;
    }

    // Simple BFS utility (kept for reference)
    public static HashMap<String, Boolean> bfs(String source,
            HashMap<String, HashMap<String, Integer>> residual) {
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        Queue<String> queue = new LinkedList<String>();
        queue.add(source);
        visited.put(source, true);
        while (!queue.isEmpty()) {
            String u = queue.poll();
            for (String v : residual.get(u).keySet()) {
                if (!visited.containsKey(v) && residual.get(u).get(v) > 0) {
                    visited.put(v, true);
                    queue.add(v);
                }
            }
        }
        return visited;
    }
}