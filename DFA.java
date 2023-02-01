public class DFA {

    int[][] transitionTable;
    int[] finalStates;
    int initState; // assumed to be 0

    // constructor takes in a transition array and array of finalStates
    public DFA(int[][] transitionTable, int[] finalStates) {
        if(transitionTable == null || finalStates == null) {
            throw new IllegalArgumentException();
        }
        this.transitionTable = transitionTable;
        this.finalStates = finalStates;
        initState = 0;
        
    }

    // determine if the input string will be accepted (true) or rejected (false) by the automata
    public boolean run(String input) {
        int currState = initState;

        for(int i = 0; i < input.length(); i++) {
            char l = input.charAt(i);
            if(l == '0') {
                // set currState to next state using transitionTable
            }
            else { // l is '1'
                // set currState to next state using transitionTable
            }
        }
        
        // check if the currState is a final state
        if(contains(finalStates, currState))
            return true;
        else
            return false;
    }

    boolean contains(final int[] arr, final int key) {
        for (int element : arr) {
            if(element == key) {
                return true;
            }
        }
        return false;
    }
}


