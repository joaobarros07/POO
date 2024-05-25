package simulation;

/**
 * Classe que representa um Sistema Planetário dentro de uma patrulha.
 * Cada sistema tem um tempo associado que é necessário para pacificar ou monitorizar o sistema.
 * Esta classe é usada para armazenar e gerenciar o tempo necessário para uma patrulha completar sua missão em um sistema específico.
 */
public class PlanetarySystem {
    // Variável para armazenar o tempo necessário para pacificar este sistema planetário.
    // O tempo de pacificação é o tempo que uma patrulha leva para garantir que o sistema está seguro e livre de ameaças.
    private int pacificationTime;

    /**
     * Construtor que inicializa um novo sistema planetário com um tempo específico de pacificação.
     * Este construtor é chamado quando um novo sistema planetário é criado dentro da simulação.
     *
     * @param pacificationTime O tempo necessário para pacificar o sistema planetário.
     * Este valor é definido durante a criação do sistema e não muda ao longo da simulação.
     * Ele representa quanto tempo a patrulha precisa gastar para garantir a segurança do sistema.
     */
    public PlanetarySystem(int pacificationTime) {
        this.pacificationTime = pacificationTime;
    }

    /**
     * Retorna o tempo de pacificação do sistema planetário.
     * Este método permite que outras partes do programa consultem quanto tempo leva para pacificar o sistema.
     * O tempo de pacificação é crucial para calcular o tempo total que uma patrulha gasta em suas missões.
     *
     * @return O tempo de pacificação deste sistema planetário.
     * O valor retornado é usado para ajudar a calcular o 'conforto' de uma patrulha, ou seja,
     * quão eficiente ela é em completar suas tarefas dentro do tempo esperado.
     */
    public int getPacificationTime() {
        return pacificationTime;
    }
}
