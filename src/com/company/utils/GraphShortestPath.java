package com.company.utils;


import com.company.Relationship;

//SOURCE: https://www.softwaretestinghelp.com/dijkstras-algorithm-in-java/
public class GraphShortestPath {

    private  int num_Vertices = 6;  //max number of vertices in graph
  
  public GraphShortestPath(int um_Vertices) {
    this.num_Vertices = um_Vertices;
  }
  
  // find a vertex with minimum distance
    int minDistance(Relationship path_array[], Boolean sptSet[])   {
        // Initialize min value
        double min = Integer.MAX_VALUE;
        int min_index = -1;
        for (int v = 0; v < num_Vertices; v++)
            if (sptSet[v] == false && path_array[v].getDistance() <= min) {
                min = path_array[v].getDistance();
                min_index = v;
            }

        return min_index;
    }

    // print the array of distances (path_array)
    void printMinpath(Relationship path_array[])   {
        System.out.println("Vertex# \t Minimum Distance from Source");
        for (int i = 0; i < num_Vertices; i++)
            System.out.println(i + " \t\t\t " + path_array[i].getDistance());
    }



    // Implementation of Dijkstra's algorithm for graph (adjacency matrix)
    public void algo_dijkstra(Relationship graph[][], int src_node)  {
      Relationship path_array[] = new Relationship[num_Vertices]; // The output array. dist[i] will hold
        // the shortest distance from src to i
        
        // spt (shortest path set) contains vertices that have shortest path
        Boolean sptSet[] = new Boolean[num_Vertices];

        // Initially all the distances are INFINITE and stpSet[] is set to false
        for (int i = 0; i < num_Vertices; i++) {
            path_array[i]=new Relationship(Double.MAX_VALUE);
            sptSet[i] = false;
        }

        // Path between vertex and itself is always 0
        path_array[src_node].setDistance(0); 
        // now find shortest path for all vertices
        for (int count = 0; count < num_Vertices - 1; count++) {
            // call minDistance method to find the vertex with min distance
            int u = minDistance(path_array, sptSet);
            // the current vertex u is processed
            sptSet[u] = true;
            // process adjacent nodes of the current vertex
            for (int v = 0; v < num_Vertices; v++)

                // if vertex v not in sptset then update it
                if (!sptSet[v] && graph[u][v].getDistance() != 0 && graph[u][v].getDistance() != -1 && path_array[u].getDistance( ) !=
                        Integer.MAX_VALUE && path_array[u].getDistance()
                        + graph[u][v].getDistance() < path_array[v].getDistance())
                    path_array[v] = new Relationship( path_array[u].getDistance() + graph[u][v].getDistance());
        }

        // print the path array
        printMinpath(path_array);
    }
}