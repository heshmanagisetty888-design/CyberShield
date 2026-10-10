package randomized;

public class BloomFilter {

    private boolean[] bits;
    private int size;

    public BloomFilter(int size) {
        this.size = size;
        this.bits = new boolean[size];
    }

    // Hash function 1
    private int hash1(String s) {
        int h = 0;
        for (int i = 0; i < s.length(); i++) {
            h = (h * 31 + s.charAt(i)) % size;
        }
        return Math.abs(h);
    }

    // Hash function 2
    private int hash2(String s) {
        int h = 7;
        for (int i = 0; i < s.length(); i++) {
            h = (h * 131 + s.charAt(i)) % size;
        }
        return Math.abs(h);
    }

    // Hash function 3
    private int hash3(String s) {
        int h = 17;
        for (int i = 0; i < s.length(); i++) {
            h = (h * 53 + s.charAt(i)) % size;
        }
        return Math.abs(h);
    }

    public void add(String item) {
        bits[hash1(item)] = true;
        bits[hash2(item)] = true;
        bits[hash3(item)] = true;
    }

    public boolean mightContain(String item) {
        return bits[hash1(item)] && bits[hash2(item)] && bits[hash3(item)];
    }
}