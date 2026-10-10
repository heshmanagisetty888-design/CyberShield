package suffix;

import java.util.HashMap;

// Educational suffix tree built from all suffixes.
// Full Ukkonen's algorithm is O(n) but very complex.
// This version is simple but uses O(n^2) memory.
public class SuffixTree {

    static class TreeNode {
        HashMap<Character, TreeNode> children = new HashMap<Character, TreeNode>();
        int startIndex = -1;
    }

    private TreeNode root = new TreeNode();

    public SuffixTree(String s) {
        for (int i = 0; i < s.length(); i++) {
            insert(s, i);
        }
    }

    private void insert(String s, int start) {
        TreeNode current = root;
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!current.children.containsKey(c)) {
                current.children.put(c, new TreeNode());
            }
            current = current.children.get(c);
        }
        current.startIndex = start;
    }

    public boolean contains(String pattern) {
        TreeNode current = root;
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (!current.children.containsKey(c)) return false;
            current = current.children.get(c);
        }
        return true;
    }
}