package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class GraphV2<T extends Relationship> {
  List<List<T>> graph;
  boolean visited[];

  GraphV2(int nodes) {
    graph = new ArrayList<>();
    visited = new boolean[nodes];

    for(int i = 0; i < nodes; i++) {
      graph.add(i, new ArrayList<>());
    }
  }

  public void addEdge(T a, T b) {
    int indexA=graph.indexOf(a);
    int indexB=graph.indexOf(b);
    graph.get(indexA).add(b);
    graph.get(indexB).add(a);
  }

  public int minimumDistanceBetweenTwoNodes(T source, T destination) {
    if(source == destination) {
      return 0;
    }

    Queue<T> queue = new LinkedList<>();
    int minDistance = 0;

    queue.add(source);
    int indexSource=graph.indexOf(source);
    visited[indexSource] = true;

    while(!queue.isEmpty()) {
      int size = queue.size();

      while(size > 0) {
        T node = queue.poll();
        int indexNode=graph.indexOf(node);
        List<T> childList = graph.get(indexNode);

        for(T child: childList) {
          if(child == destination) {
            return ++minDistance;
          }
          int indexChild=childList.indexOf(child);
          if(!visited[indexChild]) {
            queue.add(child);
            visited[indexChild] = true;
          }
        }

        size--;
      }
      minDistance++;
    }

    return -1;
  }

}