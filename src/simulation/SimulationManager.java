package simulation;

import java.util.List;

/**
 * Classe SimulationManager é responsável por gerir e iniciar a simulação.
 * Esta classe configura a simulação com parâmetros específicos e inicia a execução.
 */
public class SimulationManager {
    private final Simulation simulation;  // Referência para a simulação que será gerida.

    /**
     * Construtor do SimulationManager que configura a simulação com os parâmetros necessários.
     *
     * @param maxSteps        Número máximo de passos da simulação.
     * @param maxPopulation   População máxima antes de uma epidemia ocorrer.
     * @param mu              Taxa de mortalidade para usar na simulação.
     * @param rho             Taxa de reprodução para usar na simulação.
     * @param delta           Taxa de mutação para usar na simulação.
     * @param numberOfPatrols Número de patrulhas na simulação.
     * @param numberOfSystems Número de sistemas planetários na simulação.
     */
    public SimulationManager(int maxSteps, int maxPopulation, double mu, double rho, double delta, int numberOfPatrols, int numberOfSystems) {
        // Passa todos os parâmetros necessários para o construtor de Simulation.
        this.simulation = new Simulation(maxSteps, maxPopulation, mu, rho, delta, numberOfPatrols, numberOfSystems);
    }

    /**
     * Inicia a execução da simulação.
     */
    public void start(double tau) {

        for (double i = (tau/20); i <= tau; i += tau/20 ) {
            System.out.println("Observation " + i + ':');
            System.out.println("\t\tPresent instant:                   " + "instant");
            System.out.println("\t\tNumber of realized events:         " + "Number");
            System.out.println("\t\tPopulation size:                   " + "Population size");
            System.out.println("\t\tNumber of epidemics:               " + "Number of epidemics");
            System.out.println("\t\tBest distribution of the patrols:  " + "Best");
            System.out.println("\t\tEmpire policing time:              " + "Empire policing time");
            System.out.println("\t\tComfort:                           " + "Comfort");
            System.out.println("\t\tOther candidate distributions:     " + "Other candidate distributions");
        }
//        System.out.println("Observation number:");
//        System.out.println("Número inicial de patrulhas: " + simulation.getPatrols().size());
//        System.out.println("Conforto médio inicial das patrulhas: " + calculateAverageComfort(simulation.getPatrols()));
//        simulation.runSimulation();
//        System.out.println("Simulação concluída.");
//        displaySimulationResults();
    }

    /**
     * Calcula o conforto médio das patrulhas.
     *
     * @param patrols Lista de patrulhas.
     * @return O conforto médio.
     */
    private double calculateAverageComfort(List<Patrol> patrols) {
        return patrols.stream()
                .mapToDouble(Patrol::getComfort)
                .average()
                .orElse(0.0);
    }

    /**
     * Exibe resultados resumidos da simulação, como número de patrulhas ativas e o conforto médio.
     */
    private void displaySimulationResults() {
        List<Patrol> activePatrols = simulation.getPatrols();
        System.out.println("Número de patrulhas ativas ao final: " + activePatrols.size());
        System.out.println("Conforto médio das patrulhas ao final: " + calculateAverageComfort(activePatrols));
    }
}

    /**
     * Método principal que pode ser usado para executar o SimulationManager.
     *
     * @param args Argumentos da linha de comando não utilizados neste contexto.
     */
//    public static void main(String[] args) {
//        int maxSteps = 100000;  // Define o número máximo de passos da simulação.
//        int maxPopulation = 5000;   // Define a população máxima antes de desencadear uma epidemia.
//        double mu = 0.01;  // Define a taxa de mortalidade da simulação.
//        double rho = 0.5;  // Define a taxa de reprodução da simulação.
//        double delta = 0.02;  // Define a taxa de mutação da simulação.
//        int numberOfPatrols = 100;  // Exemplo: número de patrulhas
//        int numberOfSystems = 5000;   // Exemplo: número de sistemas planetários
//
//        // Cria uma instância do gerenciador de simulação com os parâmetros especificados.
//        SimulationManager manager = new SimulationManager(maxSteps, maxPopulation, mu, rho, delta, numberOfPatrols, numberOfSystems);
//
//        // Inicia a simulação.
//        manager.start();
//    }
//}
