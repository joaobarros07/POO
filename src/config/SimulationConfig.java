package config;

public class SimulationConfig {
    private int numPatrols;
    private int numPlanetarySystems;
    private double finalTime;
    private int initialPopulation;
    private int maxPopulation;
    private double deathRate;
    private double reproductionRate;
    private double mutationRate;
    private int[][] matrixC;

    public SimulationConfig (int n, int m, double tau, int v, int vmax, double mu, double rho, double delta){

        this.numPatrols = n;
        this.numPlanetarySystems = m;
        this.finalTime = tau;
        this.initialPopulation = v;
        this.maxPopulation = vmax;
        this.deathRate = mu;
        this.reproductionRate = rho;
        this.mutationRate = delta;

        generateRandomMatrixC();
    }

    // Construtor para uso quando os parâmetros são lidos de um arquivo
    public SimulationConfig(int n, int m, double tau, int v, int vmax, double mu, double rho, double delta, int[][] matrixC) {
        this.numPatrols = n;
        this.numPlanetarySystems = m;
        this.finalTime = tau;
        this.initialPopulation = v;
        this.maxPopulation = vmax;
        this.deathRate = mu;
        this.reproductionRate = rho;
        this.mutationRate = delta;
        this.matrixC = matrixC;
    }

    // Método para gerar a matriz C aleatoriamente
    private void generateRandomMatrixC() {
        this.matrixC = new int[numPatrols][numPlanetarySystems];
        for (int i = 0; i < numPatrols; i++) {
            for (int j = 0; j < numPlanetarySystems; j++) {
                matrixC[i][j] = (int) (Math.random() * 10 + 1);  // Gera valores entre 1 e 10
            }
        }
    }

    public int getNumPatrols() {
        return numPatrols;
    }

    public int getNumPlanetarySystems() {
        return numPlanetarySystems;
    }

    public double getFinalTime() {
        return finalTime;
    }

    public int getInitialPopulation() {
        return initialPopulation;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public double getDeathRate() {
        return deathRate;
    }

    public double getReproductionRate() {
        return reproductionRate;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public int getMatrixC(int i, int j) {
        return matrixC[i][j];
    }


}
