package network;

import java.util.HashMap;

public class FordFulkerson {

    // residual[u][v] = remaining capacity
    private HashMap<String, HashMap<String, Integer>> residual = new HashMap<String, HashMap<String, Integer>>();
    private HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
    private String source, sink;

    public void addEdge(String u, String v, int capacity) {
        if (!residual.containsKey(u)) residual.put(u, new HashMap<String, Integer>());
        if (!residual.containsKey(v)) residual.put(v, new HashMap<String, Integer>());
        if (!residual.get(u).containsKey(v)) residual.get(u).put(v, 0);
        if (!residual.get(v).containsKey(u)) residual.get(v).put(u, 0);
        residual.get(u).put(v, residual.get(u).get(v) + capacity);
    }

    public int maxFlow(String source, String sink) {
        this.source = source;
        this.sink = sink;
        int flow = 0;

        while (true) {
            visited.clear();
            for (String node : residual.keySet()) visited.put(node, false);
            int pushed = dfs(source, Integer.MAX_VALUE);
            if (pushed == 0) break;
            flow += pushed;
        }
        return flow;
    }

    private int dfs(String u, int minCap) {
        if (u.equals(sink)) return minCap;
        visited.put(u, true);

        for (String v : residual.get(u).keySet()) {
            int cap = residual.get(u).get(v);
            if (!visited.get(v) && cap > 0) {
                int pushed = dfs(v, Math.min(minCap, cap));
                if (pushed > 0) {
                    residual.get(u).put(v, cap - pushed);
                    residual.get(v).put(u, residual.get(v).get(u) + pushed);
                    return pushed;
                }
            }
        }
        return 0;
    }
}