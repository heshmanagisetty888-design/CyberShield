package network;

import java.util.ArrayList;
import java.util.HashMap;

public class BipartiteMatching {

    private HashMap<String, ArrayList<String>> adj = new HashMap<String, ArrayList<String>>();
    private HashMap<String, String> matchRight = new HashMap<String, String>();

    public void addEdge(String left, String right) {
        if (!adj.containsKey(left)) adj.put(left, new ArrayList<String>());
        adj.get(left).add(right);
    }

    public int maxMatching() {
        matchRight.clear();
        int result = 0;
        for (String left : adj.keySet()) {
            HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
            if (dfs(left, visited)) result++;
        }
        return result;
    }

    private boolean dfs(String left, HashMap<String, Boolean> visited) {
        for (String right : adj.get(left)) {
            if (visited.containsKey(right)) continue;
            visited.put(right, true);

            if (!matchRight.containsKey(right) || dfs(matchRight.get(right), visited)) {
                matchRight.put(right, left);
                return true;
            }
        }
        return false;
    }

    public HashMap<String, String> getMatches() {
        return matchRight;
    }
}