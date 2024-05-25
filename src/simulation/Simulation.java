package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A classe Simulation gerencia a simulação de várias patrulhas que monitorizam sistemas planetários.
 * Ela controla o fluxo de simulação, aplicando regras de reprodução, mutação e mortalidade.
 */
public class Simulation {
    private List<Patrol> patrols; // Lista que armazena todas as patrulhas na simulação.
    private double[][] patrollingTimes; // Matriz que armazena o tempo necessário para cada patrulha patrulhar cada sistema planetário.
    private boolean isRunning; // Controla se a simulação está ativa.
    private int currentTime; // Contador que registra o tempo atual dentro da simulação.
    private final Random random; // Objeto para gerar números aleatórios, usado em várias funções estocásticas.
    private final int MAX_STEPS; // O número máximo de passos (iterações) que a simulação pode executar.
    private final int MAX_POPULATION; // O número máximo de patrulhas que podem existir antes de desencadear uma epidemia.
    private final double mu; // Coeficiente que afeta a taxa de mortalidade das patrulhas.
    private final double rho; // Coeficiente que afeta a taxa de reprodução das patrulhas.
    private final double delta; // Coeficiente que afeta a taxa de mutação das patrulhas.

    /**
     * Construtor da classe Simulation.
     * @param maxSteps O número máximo de passos que a simulação pode executar.
     * @param maxPopulation O número máximo de patrulhas antes de uma epidemia ser disparada.
     * @param mu Taxa de mortalidade das patrulhas.
     * @param rho Taxa de reprodução das patrulhas.
     * @param delta Taxa de mutação das patrulhas.
     * @param numberOfPatrols Número inicial de patrulhas.
     * @param numberOfSystems Número de sistemas planetários.
     */
    public Simulation(int maxSteps, int maxPopulation, double mu, double rho, double delta, int numberOfPatrols, int numberOfSystems) {
        this.patrols = new ArrayList<>();
        this.isRunning = true;
        this.currentTime = 0;
        this.random = new Random();
        this.MAX_STEPS = maxSteps;
        this.MAX_POPULATION = maxPopulation;
        this.mu = mu;
        this.rho = rho;
        this.delta = delta;
        this.patrollingTimes = new double[numberOfPatrols][numberOfSystems];
        initializePatrols(numberOfPatrols, numberOfSystems);
        updateTmin(); // Atualiza o valor mínimo de tempo (tmin) necessário para patrulhar.
    }

    /**
     * Inicializa as patrulhas e os sistemas planetários que elas devem monitorizar.
     * @param numberOfPatrols Número de patrulhas a serem criadas.
     * @param numberOfSystems Número de sistemas planetários a serem monitorizados.
     */
    private void initializePatrols(int numberOfPatrols, int numberOfSystems) {
        for (int i = 0; i < numberOfPatrols; i++) {
            Patrol newPatrol = new Patrol();
            for (int j = 0; j < numberOfSystems; j++) {
                int pacificationTime = random.nextInt(300) + 50; // Gera um tempo de pacificação entre 50 e 350.
                PlanetarySystem system = new PlanetarySystem(pacificationTime);
                newPatrol.addSystem(system);
                patrollingTimes[i][j] = pacificationTime; // Armazena o tempo na matriz.
            }
            patrols.add(newPatrol); // Adiciona a nova patrulha à lista de patrulhas.
        }
    }

    /**
     * Atualiza o valor de tmin com base nos tempos mínimos registrados para patrulhar cada sistema.
     */
    private void updateTmin() {
        double tminSum = 0;
        int numberOfSystems = patrollingTimes[0].length;
        for (int j = 0; j < numberOfSystems; j++) {
            double minTime = Double.MAX_VALUE;
            for (int i = 0; i < patrollingTimes.length; i++) {
                if (patrollingTimes[i][j] < minTime) {
                    minTime = patrollingTimes[i][j];
                }
            }
            tminSum += minTime; // Soma os tempos mínimos de todos os sistemas.
        }
        Patrol.setTmin(tminSum / numberOfSystems); // Calcula a média e define o tmin na classe Patrol.
    }

    /**
     * Executa a simulação até que uma condição de término seja satisfeita.
     */
    public void runSimulation() {
        while (isRunning) {
            simulateStep();
            if (checkTerminationCondition()) {
                isRunning = false; // Para a simulação se a condição de término for atendida.
            }
        }
    }

    /**
     * Simula um único passo, incluindo reprodução, mutação e morte das patrulhas.
     */
    private void simulateStep() {
        if (patrols.size() > MAX_POPULATION) {
            triggerEpidemic(); // Desencadeia uma epidemia se o número de patrulhas exceder o limite máximo.
        }

        List<Patrol> patrolsCopy = new ArrayList<>(patrols);
        for (Patrol patrol : patrolsCopy) {
            if (random.nextDouble() < deathRate(patrol)) {
                patrols.remove(patrol); // Remove patrulhas com base na taxa de mortalidade.
            }
            if (random.nextDouble() < reproductionRate(patrol)) {
                reproduce(patrol); // Reproduz patrulhas com base na taxa de reprodução.
            }
            if (random.nextDouble() < mutationRate(patrol)) {
                mutate(patrol); // Muta patrulhas com base na taxa de mutação.
            }
        }
        currentTime++; // Incrementa o contador de tempo.
    }

    /**
     * Calcula a taxa de mortalidade de uma patrulha com base no seu conforto.
     */
    private double deathRate(Patrol patrol) {
        return Math.exp(-mu * (1 - Math.log(1 - patrol.getComfort())));
    }

    /**
     * Calcula a taxa de reprodução de uma patrulha com base no seu conforto.
     */
    private double reproductionRate(Patrol patrol) {
        return Math.exp(-rho * (1 - Math.log(patrol.getComfort())));
    }

    /**
     * Calcula a taxa de mutação de uma patrulha com base no seu conforto.
     */
    private double mutationRate(Patrol patrol) {
        return Math.exp(-delta * (1 - Math.log(patrol.getComfort())));
    }

    /**
     * Realiza a mutação de uma patrulha, movendo um sistema planetário de uma patrulha para outra.
     */
    private void mutate(Patrol patrol) {
        if (patrols.size() < 2 || patrol.getSystems().isEmpty()) return;

        PlanetarySystem systemToMutate = patrol.removeRandomSystem();
        Patrol targetPatrol;
        do {
            targetPatrol = patrols.get(random.nextInt(patrols.size()));
        } while (targetPatrol == patrol);
        targetPatrol.addSystem(systemToMutate);
    }

    /**
     * Realiza a reprodução de uma patrulha, criando uma nova patrulha com sistemas semelhantes.
     */
    private void reproduce(Patrol patrol) {
        Patrol newPatrol = new Patrol();
        newPatrol.cloneSystemsFrom(patrol);

        // Limitar o número de sistemas a remover para o tamanho atual dos sistemas
        int systemsToRemove = Math.min((int) Math.floor((1 - patrol.getComfort()) * patrol.getSystems().size()), newPatrol.getSystems().size());

        if (systemsToRemove > 0 && patrols.size() > 1) {
            List<PlanetarySystem> removedSystems = newPatrol.removeRandomSystems(systemsToRemove);
            for (PlanetarySystem system : removedSystems) {
                Patrol randomPatrol;
                do {
                    randomPatrol = patrols.get(random.nextInt(patrols.size()));
                } while (randomPatrol == patrol);

                randomPatrol.addSystem(system);
            }
            patrols.add(newPatrol);
        }
    }

    /**
     * Desencadeia uma epidemia quando a população excede o limite máximo, mantendo apenas as patrulhas com maior conforto.
     */
    private void triggerEpidemic() {
        if (patrols.size() <= MAX_POPULATION) return;
        patrols.sort((p1, p2) -> Double.compare(p2.getComfort(), p1.getComfort()));
        List<Patrol> survivors = new ArrayList<>(patrols.subList(0, Math.min(5, patrols.size())));
        patrols = survivors;
    }

    /**
     * Verifica se a simulação deve terminar, baseado em condições como tempo máximo ou extinção das patrulhas.
     */
    private boolean checkTerminationCondition() {
        if (currentTime >= MAX_STEPS || patrols.isEmpty()) {
            return true;
        }
        return patrols.stream().anyMatch(p -> p.getComfort() >= 1.0);
    }

    /**
     * Retorna a lista de patrulhas na simulação.
     * @return A lista de patrulhas.
     */
    public List<Patrol> getPatrols() {
        return new ArrayList<>(patrols);
    }
}
