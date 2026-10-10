package models;

public class NetworkEdge {
    private String source;
    private String destination;
    private int capacity;

    public NetworkEdge(String source, String destination, int capacity) {
        this.source = source;
        this.destination = destination;
        this.capacity = capacity;
    }

    public String getSource()      { return source; }
    public String getDestination() { return destination; }
    public int getCapacity()       { return capacity; }

    public String toString() {
        return source + " -> " + destination + " (" + capacity + ")";
    }
}