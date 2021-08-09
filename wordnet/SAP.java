import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.HashSet;

public class SAP {
    private final Digraph digraph;
    private final Cache cache;

    public SAP(Digraph digraph) {
        if (digraph == null) throw new IllegalArgumentException();
        this.digraph = digraph;
        cache = new Cache();
    }

    public int length(int v, int w) {
        validateInput(v, w);
        HashSet<Integer> hashSetA = convertToSet(v);
        HashSet<Integer> hashSetB = convertToSet(w);
        return findShortestCommonAncestor(hashSetA, hashSetB).distanceToAncestor;
    }

    public int ancestor(int v, int w) {
        validateInput(v, w);
        HashSet<Integer> hashSetA = convertToSet(v);
        HashSet<Integer> hashSetB = convertToSet(w);
        return findShortestCommonAncestor(hashSetA, hashSetB).commonAncestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateInput(v, w);
        HashSet<Integer> hashSetA = convertToSet(v);
        HashSet<Integer> hashSetB = convertToSet(w);
        if (hashSetA.isEmpty() || hashSetB.isEmpty()) return -1;
        return findShortestCommonAncestor(hashSetA, hashSetB).distanceToAncestor;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateInput(v, w);
        HashSet<Integer> hashSetA = convertToSet(v);
        HashSet<Integer> hashSetB = convertToSet(w);
        if (hashSetA.isEmpty() || hashSetB.isEmpty()) return -1;
        return findShortestCommonAncestor(hashSetA, hashSetB).commonAncestor;
    }

    private ShortestCommonAncestor findShortestCommonAncestor(HashSet<Integer> hashSetA,
                                                              HashSet<Integer> hashSetB) {
        ShortestCommonAncestor commonAncestor = cache.checkCache(hashSetA, hashSetB);
        if (commonAncestor == null) {
            commonAncestor = findShortestAncestralPath(hashSetA, hashSetB);
            cache.addToCache(hashSetA, hashSetB, commonAncestor);
        }
        return commonAncestor;
    }

    private void validateInput(int a, int b) {
        validateVertex(a);
        validateVertex(b);
    }

    private void validateInput(Iterable<Integer> iterableA, Iterable<Integer> iterableB) {
        if (iterableA == null || iterableB == null) throw new IllegalArgumentException();
        for (Integer x : iterableA) validateVertex(x);
        for (Integer y : iterableB) validateVertex(y);
    }

    private void validateVertex(Integer vertex) {
        if (vertex == null || vertex < 0 || vertex >= digraph.V()) {
            throw new IllegalArgumentException();
        }
    }


    private HashSet<Integer> convertToSet(Iterable<Integer> toBeConverted) {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        for (Integer x : toBeConverted) hashSet.add(x);
        return hashSet;
    }

    private HashSet<Integer> convertToSet(int toBeConverted) {
        HashSet<Integer> hashSEt = new HashSet<Integer>();
        hashSEt.add(toBeConverted);
        return hashSEt;
    }

    private ShortestCommonAncestor findShortestAncestralPath(Iterable<Integer> v,
                                                             Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(digraph, w);
        int minDistance = Integer.MAX_VALUE;
        int shortestCommonAncestor = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                int dist = bfsv.distTo(i) + bfsw.distTo(i);
                if (dist < minDistance) {
                    minDistance = dist;
                    shortestCommonAncestor = i;
                }
            }
        }
        ShortestCommonAncestor commonAncestor =
                new ShortestCommonAncestor(shortestCommonAncestor,
                                           minDistance != Integer.MAX_VALUE ? minDistance : -1);
        return commonAncestor;
    }


    private class ShortestCommonAncestor {
        private final int commonAncestor;
        private final int distanceToAncestor;

        private ShortestCommonAncestor(int commonAncestor, int distanceToAncestor) {
            this.commonAncestor = commonAncestor;
            this.distanceToAncestor = distanceToAncestor;
        }
    }

    private class Cache {
        private final HashMap<HashSet<Integer>, HashMap<HashSet<Integer>, ShortestCommonAncestor>>
                cache
                = new HashMap<HashSet<Integer>, HashMap<HashSet<Integer>, ShortestCommonAncestor>>();

        private ShortestCommonAncestor checkCache(HashSet<Integer> hashSetA,
                                                  HashSet<Integer> hashSetB) {
            HashSet<Integer>[] keys = enforceKeyOrder(hashSetA, hashSetB);
            if (cache.containsKey(keys[0])) {
                return cache.get(keys[0]).get(keys[1]);
            }
            return null;
        }

        private void addToCache(HashSet<Integer> hashSetA, HashSet<Integer> hashSetB,
                                ShortestCommonAncestor shortestCommonAncestor) {
            HashSet<Integer>[] keys = enforceKeyOrder(hashSetA, hashSetB);
            if (cache.containsKey(keys[0])) {
                cache.get(keys[0]).put(keys[1], shortestCommonAncestor);
            }
            else {
                HashMap<HashSet<Integer>, ShortestCommonAncestor> temp
                        = new HashMap<HashSet<Integer>, ShortestCommonAncestor>();
                temp.put(keys[1], shortestCommonAncestor);
                cache.put(keys[0], temp);
            }
        }

        private HashSet<Integer>[] enforceKeyOrder(HashSet<Integer> hashSetA,
                                                   HashSet<Integer> hashSetB) {
            HashSet<Integer>[] orderedKeys = new HashSet[2];
            if (hashSetB.hashCode() < hashSetA.hashCode()) {
                orderedKeys[0] = hashSetB;
                orderedKeys[1] = hashSetA;
            }
            else {
                orderedKeys[0] = hashSetA;
                orderedKeys[1] = hashSetB;
            }
            return orderedKeys;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        In in2 = new In(args[1]);
        while (!in2.isEmpty()) {
            int v = in2.readInt();
            int w = in2.readInt();
            Stopwatch stopwatch = new Stopwatch();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.println(stopwatch.elapsedTime());
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
