package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileHandler {
    public static SimulationConfig readConfigFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            try {
                // Leitura dos parâmetros básicos do arquivo
                int n = scanner.nextInt();
                int m = scanner.nextInt();
                double tau = scanner.nextDouble();
                int v = scanner.nextInt();
                int vmax = scanner.nextInt();
                double mu = scanner.nextDouble();
                double rho = scanner.nextDouble();
                double delta = scanner.nextDouble();

                // Criação e preenchimento da matriz C
                int[][] matrixC = new int[n][m];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < m; j++) {
                        matrixC[i][j] = scanner.nextInt();
                    }
                }

                // Criação da configuração de simulação com os valores lidos
                return new SimulationConfig(n, m, tau, v, vmax, mu, rho, delta, matrixC);
            } finally {
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            return null; // Return null to indicate that the file could not be read
        }
    }
}
