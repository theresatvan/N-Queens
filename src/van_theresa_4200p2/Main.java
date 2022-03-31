import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Node initial = new Node(generateProblem());

        // System.out.println("MIN-CONFLICTS ALGORITHM");
        // System.out.print("Initial state: ");
        // printState(initial);
        // printConflicts(initial.getConflicts());

        // MinConflicts mc = new MinConflicts();
        // Node solved_mc = mc.solve(initial);
        // printState(solved_mc);
        // System.out.println(solved_mc.getValue());

        System.out.println("SIMULATED ANNEALING ALGORITHM");
        System.out.print("Initial state: ");
        printState(initial);
        System.out.println("Initial amount of attacking queen pairs: " + initial.getValue());

        SimulatedAnnealing sa = new SimulatedAnnealing();
        double startTime = System.nanoTime();
        Node solved_sa = sa.solve(initial.getState(), 0.95);
        double durationTime = System.nanoTime() - startTime;
        System.out.print("Solved state: ");
        printState(solved_sa);
        System.out.println("Amount of attacking queen pairs: " + solved_sa.getValue());
        System.out.println("Runtime: " + durationTime + " ns");

        System.out.println("\nGENETIC ALGORITHM");
        System.out.println("Initial population of 1000 random problems generated.");
        System.out.println("Starting genetic algorithm...");
        List<Node> initial_population = new ArrayList<>();
        initial_population.add(initial);
        for (int i = 1; i < 1000; i++) {
            initial_population.add(new Node(generateProblem()));
        }

        GeneticAlgorithm ga = new GeneticAlgorithm();
        startTime = System.nanoTime();
        Node solved_ga = ga.solve(initial_population);
        durationTime = System.nanoTime() - startTime;
        System.out.print("Solved state: ");
        printState(solved_ga);
        System.out.println("Amount of attacking queen pairs: " + solved_ga.getValue());
        System.out.println("Runtime: " + durationTime + " ns");

        testCases(initial, initial_population);
    }

    /*
     * Method generates a random problem
     * @return int array that is the state of a random problem
     */
    private static int[] generateProblem() {
        Random random = new Random();
        int[] problem = new int[25];

        for(int i = 0; i < problem.length; i++) {
            problem[i] = random.nextInt(25);
        }

        return problem;
    }

    /*
     * Method prints out the state of a node
     * @param node the node whose state is to be printed
     */
    private static void printState(Node node) {
        System.out.print("[");
        for (int i = 0; i < node.getState().length; i++) {
            System.out.print(node.getState()[i]);
            if (i < node.getState().length -1)
                System.out.print(", ");
        }
        System.out.print("]\n");
    }

    /*
     * Method which will run the simulated annealing algorithm and the genetic algorithm 500 times each then print out
     * the average runtime of each.
     */
    private static void testCases(Node node, List<Node> population) {
        System.out.println("\nTEST CASES");
        double average_sa = 0.0;
        double average_ga = 0.0;
        int average_sa_sc = 0;
        long average_ga_sc = 0;

        for (int i = 0; i < 500; i++) {
            SimulatedAnnealing sa = new SimulatedAnnealing();
            double startTime = System.nanoTime();
            sa.solve(node.getState(), 0.95);
            double durationTime = System.nanoTime() - startTime;
            average_sa += durationTime;
            average_sa_sc += sa.getSearchCost();

            GeneticAlgorithm ga = new GeneticAlgorithm();
            startTime = System.nanoTime();
            ga.solve(population);
            durationTime = System.nanoTime() - startTime;
            average_ga += durationTime;
            average_ga_sc += ga.getSearchCost();
        }
        average_sa = average_sa / 500;
        average_ga = average_ga / 500;
        average_sa_sc = average_sa_sc / 500;
        average_ga_sc = average_ga_sc / 500;

        System.out.println("Simulated annealing average runtime: " + average_sa + " ns");
        System.out.println("Simulated annealing average search cost: " + average_sa_sc);
        System.out.println("Genetic algorithm average runtime: " + average_ga + " ns");
        System.out.println("Genetic algorithm average search cost: " + average_ga_sc);
    }

    // private static void printConflicts(List<LinkedList<Integer>> conflicts) {
    //     for (int i = 0; i < conflicts.size(); i++) {
    //         System.out.print(i + " - ");
    //         for (int j = 0; j < conflicts.get(i).size(); j++) {
    //             System.out.print(conflicts.get(i).get(j) + " ");
    //         }
    //         System.out.println();
    //     }
    // }
}
