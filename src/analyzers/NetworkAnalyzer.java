package analyzers;

import java.util.ArrayList;

import models.NetworkEdge;
import network.Dinic;
import network.EdmondsKarp;
import network.FordFulkerson;
import network.MinCut;

public class NetworkAnalyzer {

    private ArrayList<NetworkEdge> edges;

    public NetworkAnalyzer(ArrayList<NetworkEdge> edges) {
        this.edges = edges;
    }

    private ArrayList<String[]> toArray() {
        ArrayList<String[]> arr = new ArrayList<String[]>();
        for (NetworkEdge e : edges) {
            arr.add(new String[]{e.getSource(), e.getDestination(),
                                 String.valueOf(e.getCapacity())});
        }
        return arr;
    }

    public int runFordFulkerson(String source, String sink) {
        FordFulkerson ff = new FordFulkerson();
        for (NetworkEdge e : edges) {
            ff.addEdge(e.getSource(), e.getDestination(), e.getCapacity());
        }
        return ff.maxFlow(source, sink);
    }

    public int runEdmondsKarp(String source, String sink) {
        EdmondsKarp ek = new EdmondsKarp();
        for (NetworkEdge e : edges) {
            ek.addEdge(e.getSource(), e.getDestination(), e.getCapacity());
        }
        return ek.maxFlow(source, sink);
    }

    public int runDinic(String source, String sink) {
        Dinic d = new Dinic();
        for (NetworkEdge e : edges) {
            d.addEdge(e.getSource(), e.getDestination(), e.getCapacity());
        }
        return d.maxFlow(source, sink);
    }

    public int runMinCut(String source, String sink) {
        return MinCut.minCutValue(source, sink, toArray());
    }

    public ArrayList<String> criticalEdges(String source, String sink) {
        return MinCut.criticalEdges(source, sink, toArray());
    }
}