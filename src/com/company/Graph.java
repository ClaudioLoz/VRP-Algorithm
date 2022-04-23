package com.company;

import java.util.*;

public class Graph {

    // We use Hashmap to store the edges in the map
    private Map<Node, List<Node>> map = new HashMap<>();

    // This function adds a new vertex to the map
    public void addVertex(Node s)
    {
        map.put(s, new LinkedList<Node>());
    }

    // This function adds the edge
    // between source to destination
    public void addEdge(Node  source,
                        Node destination)
    {

        if (!map.containsKey(source))
            addVertex(source);

        if (!map.containsKey(destination))
            addVertex(destination);

        map.get(source).add(destination);
        map.get(destination).add(source);
    }

    // This function gives the count of vertices
    public void getVertexCount()
    {
        System.out.println("The map has "
                + map.keySet().size()
                + " vertex");
    }

    // This function gives the count of edges
    public void getEdgesCount()
    {
        int count = 0;
        for (Node v : map.keySet()) {
            count += map.get(v).size();
        }
        count = count / 2;
        System.out.println("The map has "
                + count
                + " edges.");
    }

    // This function gives whether
    // a vertex is present or not.
    public void hasVertex(Node s)
    {
        if (map.containsKey(s)) {
            System.out.println("The map contains "
                    + s + " as a vertex.");
        }
        else {
            System.out.println("The map does not contain "
                    + s + " as a vertex.");
        }
    }

    // This function gives whether an edge is present or not.
    public boolean hasEdge(Node s, Node d)
    {
       return map.get(s).contains(d);
    }

    public Set<Node> getKeys(){
        return map.keySet();
    }
    public List<Node> getValues(Node v){
        return map.get(v);
    }

    // Prints the adjancency list of each vertex.
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for (Node v : map.keySet()) {
            builder.append("* "+v.toString() + "adjacents:\n");
            for (Node w : map.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }

        return (builder.toString());
    }
}
