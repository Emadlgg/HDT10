package grafo;

import java.util.*;
import java.io.*;

public class Graph {
    private int[][] adjMatrix;
    private String[] cities;
    private int numCities;

    public Graph(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            createDefaultGraphFile(file);
        }
        readGraph(filename);
    }

    private void createDefaultGraphFile(File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("Mixco Antigua 30");
            writer.println("Antigua Escuintla 25");
            writer.println("Escuintla SantaLucia 15");
        }
    }

    private void readGraph(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        List<String> cityList = new ArrayList<>();
        List<int[]> edges = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            String city1 = parts[0];
            String city2 = parts[1];
            int distance = Integer.parseInt(parts[2]);

            if (!cityList.contains(city1)) {
                cityList.add(city1);
            }
            if (!cityList.contains(city2)) {
                cityList.add(city2);
            }

            edges.add(new int[]{cityList.indexOf(city1), cityList.indexOf(city2), distance});
        }
        br.close();

        numCities = cityList.size();
        cities = cityList.toArray(new String[0]);
        adjMatrix = new int[numCities][numCities];

        for (int[] row : adjMatrix) {
            Arrays.fill(row, Integer.MAX_VALUE / 2);
        }

        for (int i = 0; i < numCities; i++) {
            adjMatrix[i][i] = 0;
        }

        for (int[] edge : edges) {
            adjMatrix[edge[0]][edge[1]] = edge[2];
        }
    }

    public void floydWarshall() {
        for (int k = 0; k < numCities; k++) {
            for (int i = 0; i < numCities; i++) {
                for (int j = 0; j < numCities; j++) {
                    if (adjMatrix[i][j] > adjMatrix[i][k] + adjMatrix[k][j]) {
                        adjMatrix[i][j] = adjMatrix[i][k] + adjMatrix[k][j];
                    }
                }
            }
        }
    }

    public void printShortestPath(String city1, String city2) {
        int i = Arrays.asList(cities).indexOf(city1);
        int j = Arrays.asList(cities).indexOf(city2);

        if (i == -1 || j == -1) {
            System.out.println("Ciudad no encontrada.");
            return;
        }

        if (adjMatrix[i][j] == Integer.MAX_VALUE / 2) {
            System.out.println("No hay ruta entre " + city1 + " y " + city2);
            return;
        }

        System.out.println("La ruta más corta de " + city1 + " a " + city2 es de " + adjMatrix[i][j] + " KM");
                // Mostrar las ciudades intermedias es más complicado y requeriría una matriz de predecesores.
    }

    public String getGraphCenter() {
        int[] eccentricities = new int[numCities];
        Arrays.fill(eccentricities, 0);

        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (adjMatrix[i][j] > eccentricities[i]) {
                    eccentricities[i] = adjMatrix[i][j];
                }
            }
        }

        int minEccentricity = Integer.MAX_VALUE;
        int centerIndex = -1;
        for (int i = 0; i < numCities; i++) {
            if (eccentricities[i] < minEccentricity) {
                minEccentricity = eccentricities[i];
                centerIndex = i;
            }
        }

        return cities[centerIndex];
    }

    public void updateGraph(String city1, String city2, int distance) {
        int i = Arrays.asList(cities).indexOf(city1);
        int j = Arrays.asList(cities).indexOf(city2);

        if (i == -1 || j == -1) {
            System.out.println("Ciudad no encontrada.");
            return;
        }

        adjMatrix[i][j] = distance;
        floydWarshall();
    }

    public void removeEdge(String city1, String city2) {
        int i = Arrays.asList(cities).indexOf(city1);
        int j = Arrays.asList(cities).indexOf(city2);

        if (i == -1 || j == -1) {
            System.out.println("Ciudad no encontrada.");
            return;
        }

        adjMatrix[i][j] = Integer.MAX_VALUE / 2;
        floydWarshall();
    }

    public void printAdjMatrix() {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (adjMatrix[i][j] == Integer.MAX_VALUE / 2) {
                    System.out.print("INF ");
                } else {
                    System.out.print(adjMatrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }
}
