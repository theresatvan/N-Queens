import java.util.*;

public class GeneticAlgorithm {
    private int searchCost = 0;
    /*
     * Method implements the genetic algorithm
     * @param population a sorted list of nodes in ascending order according to value
     * @return the first index of population
     */
    public Node solve(List<Node> population) {
        double mutation_rate = 0.25;
        do {
            List<Node> new_population = new ArrayList<>();

            for(int i = 0; i < population.size(); i++) {
                Node x = randomSelection(population);
                Node y = randomSelection(population);
                Node child = reproduce(x, y);
                searchCost++;

                if (Math.random() <= mutation_rate)
                    child = mutate(child);
                new_population.add(child);
            }
            population = new ArrayList<>(new_population);
            Collections.sort(population);
            new_population.clear();
        } while (population.get(0).getValue() != 0);

        return population.get(0);
    }

    /*
     * Method randomly selects and returns a node from the half of the population's most fittest
     * @param population a sorted list of nodes in ascending order according to value
     * @return a randomly selected node from the half of the population's most fittest
     */
    private Node randomSelection(List<Node> population) {
        Random random = new Random();

        return population.get(random.nextInt(population.size() / 2));
    }

    /*
     * Method creates and returns a child from two parent nodes. Crossover index is randomly selected.
     * @param x parent node 1
     * @param y parent node 2
     * @return the child node created from x and y
     */
    private Node reproduce(Node x, Node y) {
        int n = x.getState().length;
        int[] child = new int[n];

        Random random = new Random();
        int crossover_index = random.nextInt(n-2) + 1;

        System.arraycopy(x.getState(), 0, child, 0, crossover_index);
        System.arraycopy(y.getState(), crossover_index, child, crossover_index, n - crossover_index);

        return new Node(child);
    }

    /*
     * Method returns a mutated node. Mutation in this method is defined as a randomly selected queen is moved to a
     * randomly selected row.
     * @param node the node to be mutated
     * @return the mutated node
     */
    private Node mutate(Node node) {
        Random random = new Random();
        int[] mutated_state = node.getState();

        mutated_state[random.nextInt(mutated_state.length)] = random.nextInt(mutated_state.length);

        return new Node(mutated_state);
    }

    public int getSearchCost() { return this.searchCost; }
}
