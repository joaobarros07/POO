package simulation;

import java.util.ArrayList;
import java.util.List;


/**
 * A classe Patrol representa uma patrulha responsável por manter a ordem em sistemas planetários.
 * Cada patrulha gere um conjunto de sistemas e calcula o tempo necessário para pacificar cada um deles.
 */
public class Patrol {
    // Lista de sistemas planetários que esta patrulha está encarregue de pacificar.
    private List<PlanetarySystem> systems = new ArrayList<>();

    // Total de tempo gasto pela patrulha para pacificar todos os seus sistemas.
    private int totalPatrolTime;

    // Tempo mínimo necessário para pacificar todos os sistemas, usado para calcular o conforto.
    private static double tmin = 100;

    /**
     * Define o tempo mínimo global para todos os sistemas, baseado em dados externos ou cálculos.
     * @param newTmin O novo valor de tempo mínimo.
     */
    public static void setTmin(double newTmin) {
        tmin = newTmin;
    }

    /**
     * Adiciona um sistema planetário à patrulha e aumenta o tempo total de patrulha conforme necessário.
     * @param system O sistema planetário a ser adicionado.
     */
    public void addSystem(PlanetarySystem system) {
        systems.add(system);
        totalPatrolTime += system.getPacificationTime();
    }

    /**
     * Remove um sistema planetário da patrulha e reduz o tempo total de patrulha.
     * @param system O sistema planetário a ser removido.
     */
    public void removeSystem(PlanetarySystem system) {
        if (systems.remove(system)) {
            totalPatrolTime -= system.getPacificationTime();
        }
    }

    /**
     * Obtém o tempo total de patrulha gasto com todos os sistemas.
     * @return O tempo total de patrulhamento.
     */
    public int getTotalPatrolTime() {
        return totalPatrolTime;
    }

    /**
     * Calcula o conforto da patrulha, que é uma medida de quão eficiente ela é.
     * Quanto mais próximo de 1, mais eficiente é a patrulha.
     * @return O valor de conforto da patrulha.
     */
    public double getComfort() {
        if (totalPatrolTime == 0) return 1; // Se não há tempo de patrulha, conforto é máximo.
        return tmin / totalPatrolTime;
    }

    /**
     * Obtém uma lista dos sistemas planetários que a patrulha está a monitorizar.
     * @return Uma cópia da lista de sistemas planetários.
     */
    public List<PlanetarySystem> getSystems() {
        return new ArrayList<>(systems);
    }

    /**
     * Remove aleatoriamente um sistema planetário da patrulha e o retorna.
     * @return O sistema planetário removido, ou null se não houver sistemas.
     */
    public PlanetarySystem removeRandomSystem() {
        if (systems.isEmpty()) return null;
        return systems.remove((int) (Math.random() * systems.size()));
    }

    /**
     * Remove uma quantidade específica de sistemas planetários aleatoriamente.
     * @param count O número de sistemas a remover.
     * @return Uma lista dos sistemas planetários removidos.
     */
    public List<PlanetarySystem> removeRandomSystems(int count) {
        List<PlanetarySystem> removedSystems = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            PlanetarySystem removed = removeRandomSystem();
            if (removed != null) {
                removedSystems.add(removed);
            } else {
                break; // Para de remover se não houver mais sistemas.
            }
        }
        return removedSystems;
    }

    /**
     * Clona os sistemas planetários de outra patrulha para esta patrulha.
     * @param other A outra patrulha de onde os sistemas serão clonados.
     */
    public void cloneSystemsFrom(Patrol other) {
        for (PlanetarySystem system : other.getSystems()) {
            this.addSystem(new PlanetarySystem(system.getPacificationTime()));
        }
    }
}


