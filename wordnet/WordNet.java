import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private final HashMap<String, ArrayList<Integer>> nounToSynsetsMap
            = new HashMap<String, ArrayList<Integer>>();
    private final String[] synsets;
    private final SAP sap;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        In inSynsets = new In(synsets);
        In inHypernyms = new In(hypernyms);
        String[] synsetLines = inSynsets.readAllLines();
        String[] hypernymsLines = inHypernyms.readAllLines();
        int numOfVertices = synsetLines.length;
        Digraph digraph = new Digraph(numOfVertices);
        this.synsets = new String[numOfVertices];
        addDigraphEdges(hypernymsLines, digraph);
        validateGraph(digraph);
        mapNounsToSynsets(synsetLines, numOfVertices);
        sap = new SAP(digraph);
    }

    private void mapNounsToSynsets(String[] synsetLines, int numOfVertices) {
        for (int i = 0; i < numOfVertices; i++) {
            String[] partitions = synsetLines[i].split(",");
            this.synsets[i] = partitions[1];
            String[] nouns = partitions[1].split(" ");
            for (String noun : nouns) {
                if (nounToSynsetsMap.containsKey(noun)) {
                    nounToSynsetsMap.get(noun).add(i);
                }
                else {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.add(i);
                    nounToSynsetsMap.put(noun, temp);
                }
            }
        }
    }

    private void addDigraphEdges(String[] hypernymsLines, Digraph digraph) {
        for (String line : hypernymsLines) {
            if (line.contains(",")) {
                String[] divisions = line.split(",");
                int id = Integer.parseInt(divisions[0]);
                for (int i = 1; i < divisions.length; i++) {
                    int connection = Integer.parseInt(divisions[i]);
                    digraph.addEdge(id, connection);
                }
            }
        }
    }

    private void validateGraph(Digraph digraph) {
        DirectedCycle test = new DirectedCycle(digraph);
        if (test.hasCycle()) throw new IllegalArgumentException();
        int numOfRoots = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0) numOfRoots++;
        }
        if (numOfRoots > 1) throw new IllegalArgumentException();
    }

    public Iterable<String> nouns() {
        return nounToSynsetsMap.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return nounToSynsetsMap.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        validateTwoNouns(nounA, nounB);
        return sap.length(nounToSynsetsMap.get(nounA), nounToSynsetsMap.get(nounB));
    }

    public String sap(String nounA, String nounB) {
        validateTwoNouns(nounA, nounB);
        int ancestorIndex = sap.ancestor(nounToSynsetsMap.get(nounA), nounToSynsetsMap.get(nounB));
        return this.synsets[ancestorIndex];
    }

    private void validateTwoNouns(String nounA, String nounB) {
        if (!(isNoun(nounA)) || !(isNoun(nounB))) throw new IllegalArgumentException();
    }

}
