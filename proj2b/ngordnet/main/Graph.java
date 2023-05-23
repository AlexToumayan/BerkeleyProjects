package ngordnet.main;

import java.util.*;

public class Graph {
    private final List<Set<Integer>> adjacencyList;

    public Graph(int numVertices) {
        adjacencyList = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new HashSet<>());
        }
    }

    public void addEdge(int vertex, int otherVertex) {
        adjacencyList.get(vertex).add(otherVertex);
    }

    public Set<Integer> reachable(Set<Integer> vertices) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>(vertices);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (!visited.contains(current)) {
                visited.add(current);
                queue.addAll(adjacencyList.get(current));
            }
        }
        return visited;
    }
}