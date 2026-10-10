package stringalgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class AhoCorasick {

    // Trie node
    static class Node {
        HashMap<Character, Node> children = new HashMap<Character, Node>();
        Node fail;
        ArrayList<String> output = new ArrayList<String>();
    }

    private Node root = new Node();

    // Insert one pattern into the trie
    public void addPattern(String pattern) {
        Node current = root;
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (!current.children.containsKey(c)) {
                current.children.put(c, new Node());
            }
            current = current.children.get(c);
        }
        current.output.add(pattern);
    }

    // Build failure links using BFS
    public void build() {
        Queue<Node> queue = new LinkedList<Node>();
        root.fail = root;

        // Depth-1 nodes point failure to root
        for (Character c : root.children.keySet()) {
            Node child = root.children.get(c);
            child.fail = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            for (Character c : current.children.keySet()) {
                Node child = current.children.get(c);
                Node fail = current.fail;

                while (fail != root && !fail.children.containsKey(c)) {
                    fail = fail.fail;
                }
                if (fail.children.containsKey(c) && fail.children.get(c) != child) {
                    child.fail = fail.children.get(c);
                } else {
                    child.fail = root;
                }

                // Merge output from fail node
                child.output.addAll(child.fail.output);
                queue.add(child);
            }
        }
    }

    // Search all patterns in text
    public HashMap<String, ArrayList<Integer>> search(String text) {
        HashMap<String, ArrayList<Integer>> result = new HashMap<String, ArrayList<Integer>>();
        Node current = root;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            while (current != root && !current.children.containsKey(c)) {
                current = current.fail;
            }
            if (current.children.containsKey(c)) {
                current = current.children.get(c);
            } else {
                current = root;
            }

            for (int k = 0; k < current.output.size(); k++) {
                String pattern = current.output.get(k);
                if (!result.containsKey(pattern)) {
                    result.put(pattern, new ArrayList<Integer>());
                }
                result.get(pattern).add(i - pattern.length() + 1);
            }
        }
        return result;
    }
}