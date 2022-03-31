import java.util.*;

public class Node implements Comparable<Node> {
    int[] state;
    int value;      // amount of attacking queen pairs
    List<LinkedList<Integer>> conflicts = new ArrayList<>();

    public Node(int[] state) {
        this.state = state;
        setValue();
        setConflicts();
    }

    public void setState(int[] state) {
        this.state = state;
        setValue();
        setConflicts();
    }

    public int[] getState() {
        return this.state;
    }

    private void setValue() {
        int attacking_queens = 0;

        for (int i = 0; i < this.state.length; i++) {
            for (int j = 1; j < this.state.length; j++) {
                if (i - j >= 0) {
                    if (this.state[i] == this.state[i-j])
                        attacking_queens++;
                    if (this.state[i-j] == this.state[i] - j)
                        attacking_queens++;
                    if (this.state[i-j] == this.state[i] + j)
                        attacking_queens++;
                }

                if (i + j < this.state.length) {
                    if (this.state[i] == this.state[i+j])
                        attacking_queens++;
                    if (this.state[i+j] == this.state[i] - j)
                        attacking_queens++;
                    if (this.state[i+j] == this.state[i] + j)
                        attacking_queens++;
                }
            }
        }

        value = attacking_queens;
    }

    public int getValue() { return this.value; }

    public void setConflicts() {
        for (int i = 0; i < this.state.length; i++) {
            LinkedList<Integer> conflicting_queens = new LinkedList<>();
            for (int j = 0; j < this.state.length; j++) {
                if (j != i) {
                    if (i - j >= 0) {
                        if (this.state[i] == this.state[i-j])
                            conflicting_queens.add(j);
                        if (this.state[i-j] == this.state[i] - j)
                            conflicting_queens.add(j);
                        if (this.state[i-j] == this.state[i] + j)
                            conflicting_queens.add(j);
                    }

                    if (i + j < this.state.length) {
                        if (this.state[i] == this.state[i+j])
                            conflicting_queens.add(j);
                        if (this.state[i+j] == this.state[i] - j)
                            conflicting_queens.add(j);
                        if (this.state[i+j] == this.state[i] + j)
                            conflicting_queens.add(j);
                    }
                }
            }
            conflicts.add(conflicting_queens);
        }
    }

    public List<LinkedList<Integer>> getConflicts() { return this.conflicts; }

    public int compareTo(Node node) {
        if (node.getValue() < this.value)
            return 1;
        if (node.getValue() > this.value)
            return -1;
        return 0;
    }
}
