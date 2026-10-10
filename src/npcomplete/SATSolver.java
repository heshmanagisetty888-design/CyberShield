package npcomplete;

import java.util.ArrayList;
import java.util.HashMap;

public class SATSolver {

    // A clause is a list of literals. A literal is a variable name possibly
    // starting with "!" meaning NOT.
    public static boolean solve(ArrayList<ArrayList<String>> clauses,
                                ArrayList<String> variables) {
        int n = variables.size();
        int total = 1 << n;

        for (int mask = 0; mask < total; mask++) {
            HashMap<String, Boolean> assignment = new HashMap<String, Boolean>();
            for (int i = 0; i < n; i++) {
                assignment.put(variables.get(i), (mask & (1 << i)) != 0);
            }
            if (evaluate(clauses, assignment)) {
                return true;
            }
        }
        return false;
    }

    public static HashMap<String, Boolean> solveWithAssignment(
            ArrayList<ArrayList<String>> clauses,
            ArrayList<String> variables) {
        int n = variables.size();
        int total = 1 << n;

        for (int mask = 0; mask < total; mask++) {
            HashMap<String, Boolean> assignment = new HashMap<String, Boolean>();
            for (int i = 0; i < n; i++) {
                assignment.put(variables.get(i), (mask & (1 << i)) != 0);
            }
            if (evaluate(clauses, assignment)) return assignment;
        }
        return null;
    }

    private static boolean evaluate(ArrayList<ArrayList<String>> clauses,
                                    HashMap<String, Boolean> assignment) {
        for (ArrayList<String> clause : clauses) {
            boolean clauseTrue = false;
            for (String literal : clause) {
                boolean value;
                if (literal.startsWith("!")) {
                    String var = literal.substring(1);
                    value = !assignment.get(var);
                } else {
                    value = assignment.get(literal);
                }
                if (value) { clauseTrue = true; break; }
            }
            if (!clauseTrue) return false;
        }
        return true;
    }
}