package main;

import config.SimulationConfig;
import config.FileHandler;
import simulation.SimulationManager;

//import simulation.Simulation;

public class Main {
    public static void main(String[] args) {

        if (args.length == 0) {
            printUsage();
            return;
        }

        SimulationConfig config = null;

        switch (args[0]) {
            case "-r":
                if (args.length != 9) {
                    printUsage();
                    return;
                }
                config = new SimulationConfig(
                        Integer.parseInt(args[1]), Integer.parseInt(args[2]), Double.parseDouble(args[3]),
                        Integer.parseInt(args[4]), Integer.parseInt(args[5]), Double.parseDouble(args[6]),
                        Double.parseDouble(args[7]), Double.parseDouble(args[8]));
                break;
            case "-f":
                if (args.length != 2) {
                    printUsage();
                    return;
                }
                config = FileHandler.readConfigFromFile(args[1]);
                break;
            default:
                printUsage();
                return;
        }

        if (config == null) {
            System.out.println("Failed to load configuration.");
            return;
        }

        // Cria uma instância do gerenciador de simulação com os parâmetros especificados.
        SimulationManager manager = new SimulationManager(1000, config.getMaxPopulation(), config.getDeathRate(),
                config.getReproductionRate(),config.getMutationRate(), config.getNumPatrols(), config.getNumPlanetarySystems());

        // Inicia a simulação.
        manager.start(config.getFinalTime());
    }
    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("java -jar project.jar -r <n> <m> <τ> <ν> <νmax> <µ> <ρ> <δ>");
        System.out.println("java -jar project.jar -f <infile>");
    }

}
