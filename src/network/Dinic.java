package network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Dinic {

    static class Edge {
        String to;
        int cap, flow;
        Edge rev;
        Edge(String to, int cap) { this.to = to; this.cap = cap; }
    }

    private HashMap<String, ArrayList<Edge>> graph = new HashMap<String, ArrayList<Edge>>();
    private HashMap<String, Integer> level = new HashMap<String, Integer>();
    private HashMap<String, Integer> ptr   = new HashMap<String, Integer>();
    private String source, sink;

    public void addNode(String u) {
        if (!graph.containsKey(u)) graph.put(u, new ArrayList<Edge>());
    }

    public void addEdge(String u, String v, int capacity) {
        addNode(u); addNode(v);
        Edge a = new Edge(v, capacity);
        Edge b = new Edge(u, 0);
        a.rev = b;
        b.rev = a;
        graph.get(u).add(a);
        graph.get(v).add(b);
    }

    private boolean bfs() {
        level.clear();
        for (String node : graph.keySet()) level.put(node, -1);
        Queue<String> q = new LinkedList<String>();
        q.add(source);
        level.put(source, 0);

        while (!q.isEmpty()) {
            String u = q.poll();
            for (Edge e : graph.get(u)) {
                if (level.get(e.to) == -1 && e.cap - e.flow > 0) {
                    level.put(e.to, level.get(u) + 1);
                    q.add(e.to);
                }
            }
        }
        return level.get(sink) != -1;
    }

    private int dfs(String u, int pushed) {
        if (pushed == 0) return 0;
        if (u.equals(sink)) return pushed;
        ArrayList<Edge> edges = graph.get(u);
        for (; ptr.get(u) < edges.size(); ptr.put(u, ptr.get(u) + 1)) {
            Edge e = edges.get(ptr.get(u));
            if (level.get(e.to) == level.get(u) + 1 && e.cap - e.flow > 0) {
                int tr = dfs(e.to, Math.min(pushed, e.cap - e.flow));
                if (tr == 0) continue;
                e.flow += tr;
                e.rev.flow -= tr;
                return tr;
            }
        }
        return 0;
    }

    public int maxFlow(String source, String sink) {
        this.source = source;
        this.sink = sink;
        int flow = 0;

        while (bfs()) {
            ptr.clear();
            for (String node : graph.keySet()) ptr.put(node, 0);
            while (true) {
                int pushed = dfs(source, Integer.MAX_VALUE);
                if (pushed == 0) break;
                flow += pushed;
            }
        }
        return flow;
    }
}