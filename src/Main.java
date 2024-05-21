package grafo;

import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Graph graph = new Graph("guategrafo.txt");
            graph.floydWarshall();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1. Mostrar ruta más corta entre dos ciudades");
                System.out.println("2. Mostrar la ciudad centro del grafo");
                System.out.println("3. Modificar el grafo");
                System.out.println("4. Finalizar");
                int option = scanner.nextInt();
                scanner.nextLine();  // consume newline

                if (option == 1) {
                    System.out.println("Ingrese la ciudad de origen:");
                    String city1 = scanner.nextLine();
                    System.out.println("Ingrese la ciudad de destino:");
                    String city2 = scanner.nextLine();
                    graph.printShortestPath(city1, city2);
                } else if (option == 2) {
                    System.out.println("La ciudad centro del grafo es: " + graph.getGraphCenter());
                } else if (option == 3) {
                    System.out.println("1. Interrupción de tráfico entre dos ciudades");
                    System.out.println("2. Establecer conexión entre dos ciudades");
                    int subOption = scanner.nextInt();
                    scanner.nextLine();  // consume newline

                    if (subOption == 1) {
                        System.out.println("Ingrese la primera ciudad:");
                        String city1 = scanner.nextLine();
                        System.out.println("Ingrese la segunda ciudad:");
                        String city2 = scanner.nextLine();
                        graph.removeEdge(city1, city2);
                    } else if (subOption == 2) {
                        System.out.println("Ingrese la primera ciudad:");
                        String city1 = scanner.nextLine();
                        System.out.println("Ingrese la segunda ciudad:");
                        String city2 = scanner.nextLine();
                        System.out.println("Ingrese la distancia en KM:");
                        int distance = scanner.nextInt();
                        graph.updateGraph(city1, city2, distance);
                    }
                } else if (option == 4) {
                    break;
                }
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }
    }
}
