package npcomplete;

import java.util.ArrayList;
import java.util.HashMap;

public class ThreeSAT {

    public static boolean solve(ArrayList<ArrayList<String>> clauses,
                                ArrayList<String> variables) {
        // Only handles clauses with exactly 3 literals
        for (ArrayList<String> c : clauses) {
            if (c.size() != 3) {
                System.out.println("[!] Clause does not have 3 literals.");
                return false;
            }
        }
        return SATSolver.solve(clauses, variables);
    }

    public static HashMap<String, Boolean> solveWithAssignment(
            ArrayList<ArrayList<String>> clauses,
            ArrayList<String> variables) {
        return SATSolver.solveWithAssignment(clauses, variables);
    }
}