package network;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class EdmondsKarp {

    private HashMap<String, HashMap<String, Integer>> cap = new HashMap<String, HashMap<String, Integer>>();
    private String source, sink;

    public void addEdge(String u, String v, int capacity) {
        if (!cap.containsKey(u)) cap.put(u, new HashMap<String, Integer>());
        if (!cap.containsKey(v)) cap.put(v, new HashMap<String, Integer>());
        cap.get(u).put(v, cap.get(u).getOrDefault(v, 0) + capacity);
        if (!cap.get(v).containsKey(u)) cap.get(v).put(u, 0);
    }

    public int maxFlow(String source, String sink) {
        this.source = source;
        this.sink = sink;
        int flow = 0;

        while (true) {
            HashMap<String, String> parent = new HashMap<String, String>();
            Queue<String> queue = new LinkedList<String>();
            queue.add(source);
            parent.put(source, null);

            while (!queue.isEmpty() && !parent.containsKey(sink)) {
                String u = queue.poll();
                for (String v : cap.get(u).keySet()) {
                    if (!parent.containsKey(v) && cap.get(u).get(v) > 0) {
                        parent.put(v, u);
                        queue.add(v);
                    }
                }
            }

            if (!parent.containsKey(sink)) break;

            // Find bottleneck
            int bottleneck = Integer.MAX_VALUE;
            String v = sink;
            while (!v.equals(source)) {
                String u = parent.get(v);
                bottleneck = Math.min(bottleneck, cap.get(u).get(v));
                v = u;
            }

            // Update residual
            v = sink;
            while (!v.equals(source)) {
                String u = parent.get(v);
                cap.get(u).put(v, cap.get(u).get(v) - bottleneck);
                cap.get(v).put(u, cap.get(v).get(u) + bottleneck);
                v = u;
            }
            flow += bottleneck;
        }
        return flow;
    }
}