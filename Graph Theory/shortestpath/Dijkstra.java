import java.util.*;
import java.io.*;

public class Dijkstra {
  
  static int n, m, orig, dest;
  static ArrayList<ArrayList<Edge>> adj;
  static int[] dist;
  static boolean[] v;

  public static void main (String[] args) {
    Scanner sc = new Scanner(System.in);

    n = sc.nextInt();
    m = sc.nextInt();

    orig = sc.nextInt() - 1;
    dest = sc.nextInt() - 1;

    adj = new ArrayList<ArrayList<Edge>>();

    dist = new int[n];
    v = new boolean[n];

    for (int i = 0; i < n; i++) {
      adj.add(new ArrayList<Edge>());
      dist[i] = 1 << 30;
    }

    for (int i = 0; i < m; i++) {
      int a = sc.nextInt() - 1;
      int b = sc.nextInt() - 1;
      int c = sc.nextInt();
      adj.get(a).add(new Edge(b, c));
    }
    dist[orig] = 0;

    for (int i = 0; i < n - 1; i++) {
      int minIndex = -1;
      for (int j = 0; j < n; j++)
        if (!v[j] && (minIndex == -1 || dist[minIndex] > dist[j]))
          minIndex = j;
      v[minIndex] = true;
      for (Edge e : adj.get(minIndex))
        dist[e.dest] = Math.min(dist[e.dest], dist[minIndex] + e.cost);
    }

    System.out.println(dist[dest]);
    System.out.close();
  }

  static class Edge {
    int dest, cost;

    Edge (int dest, int cost) {
      this.dest = dest;
      this.cost = cost;
    }
  }
}
