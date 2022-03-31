import java.util.*;
import java.lang.*;

public class SimulatedAnnealing {
    private int searchCost = 0;
    /*
     * Method implements simulated annealing algorithm with regards to the n-queens problem.
     * @param problem the state of the current node
     * @param coolingFactor the factor in which the temperature will slowly lower towards 0 and the solution
     * @return the current node with the solution
     */
    public Node solve(int[] problem, double coolingFactor) {
        Node current;
        Node next;
        double temperature = 100.0;

        current = new Node(problem);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            temperature *= coolingFactor;

            // if no attacking queen pairs in current node then break loop
            if (current.getValue() == 0)
                break;

            next = randomMove(current);
            // if next node has less attacking queen pairs then loss will be positive
            int loss = current.getValue() - next.getValue();
            // if e^(loss/temperature) is greater than 1 then probability is 1, else probability is e^(loss/temperature)
            double probability = (Math.exp(loss / temperature) > 1) ? 1 : Math.exp(loss / temperature);

            // if next node has less attacking queen pairs then set current node to next node
            if (loss > 0) {
                current = next;
                searchCost++;
            }
            else if (Math.random() <= probability) {
                current = next;
                searchCost++;
            }
        }

        return current;
    }

    /*
     * Method that randomly moves all queens to a random row.
     * @param node the node whose state is to be randomized
     * @return the new node with a randomized state
     */
    private Node randomMove(Node node) {
        int[] newState = new int[node.getState().length];
        Random random = new Random();
        int c = random.nextInt(newState.length);

        for (int i = 0; i < newState.length; i++) {
            newState[i] = node.getState()[i];
            if (i == c) {
                newState[i] = random.nextInt(newState.length);
            }
        }

        return new Node(newState);
    }

    public int getSearchCost() { return this.searchCost; }
}
