package dynamicprogramming;

import java.util.ArrayList;

public class BitmaskDP {

    // Generate all subsets (bitmask representation) of n events
    public static ArrayList<Integer> allMasks(int n) {
        ArrayList<Integer> masks = new ArrayList<Integer>();
        int total = 1 << n;
        for (int mask = 0; mask < total; mask++) {
            masks.add(mask);
        }
        return masks;
    }

    // Given an event name and a list of event names, return its bit
    public static int bitOf(String event, String[] events) {
        for (int i = 0; i < events.length; i++) {
            if (events[i].equals(event)) return 1 << i;
        }
        return 0;
    }

    // Given a set of events, return combined bitmask
    public static int maskOf(String[] selected, String[] events) {
        int mask = 0;
        for (int i = 0; i < selected.length; i++) {
            mask |= bitOf(selected[i], events);
        }
        return mask;
    }

    // Count how many events are in the mask
    public static int countBits(int mask) {
        int count = 0;
        while (mask > 0) {
            count += mask & 1;
            mask >>= 1;
        }
        return count;
    }

    // Print a mask as 4-bit binary (like 1111)
    public static String toBinary(int mask, int width) {
        StringBuilder sb = new StringBuilder();
        for (int i = width - 1; i >= 0; i--) {
            sb.append((mask >> i) & 1);
        }
        return sb.toString();
    }
}