/*************************************************************
 * DFA Class
 * 
 * Given a transition table, and a list of final states, 
 * a Deterministic Finite Automata with various operations 
 * is created.
 * 
 * Credits: Ethan Fischer, John Lawler, and Luke Wells
 *************************************************************/

import java.util.ArrayList;

public class DFA {

    private int[][] transitionTable; // transitionTable[currentState][input] = nextState
    private int[] finalStates;
    private int initState;

    // constructor takes in a transition array and array of finalStates
    public DFA(int[][] transitionTable, int[] finalStates) {
        if(transitionTable == null || finalStates == null) {
            throw new IllegalArgumentException();
        }
        this.transitionTable = transitionTable;
        this.finalStates = finalStates;
        initState = 0; // agreed to default to 0 in class
    }
    
    // determine if the input string will be accepted (true) or rejected (false) by the automata
    public boolean run(String testStr) {
        int currState = initState;
        
        // loop through string one by one
        for(int i = 0; i < testStr.length(); i++) {
            char ch = testStr.charAt(i);
            int input = ch - '0'; // turns ch into int by subtracting the ascii value of '0' from the ascii value of ch
            currState = transitionTable[currState][input]; // use transition table to determine next state
        }
        
        // check if the currState is a final state
        if(contains(finalStates, currState))
        return true;
        else
        return false;
    }
    
    // creates a DFA that accepts the union of M1 and M2
    public static DFA union(DFA m1, DFA m2) {
        return m1;
    }
    
    // creates a DFA that accepts the intersection of M1 and M2
    public static DFA intersection(DFA m1, DFA m2) {
        return m1;
    }
    
    // creates a DFA that accepts the set difference of M1 and M2 (set of strings accepted by M1 but not M2)
    public static DFA difference(DFA m1, DFA m2) {
        return m1;
    }
    
    // creates a DFA that is the complement of the given DFA (swap the final and non-final states)
    public static DFA complement(DFA m) {
        ArrayList<Integer> temp = new ArrayList<>(); // used for creating the new final states list

        // loop through states in m.transitionTable
        for(int i = 0; i < m.transitionTable.length; i++) {
            // if the current state is not in m.finalStates, add that state to the list of final states for our new DFA 
            if(!contains(m.finalStates, i)) {
                temp.add(i);
            }
        }

        int[] finStates = temp.stream().mapToInt(i -> i).toArray(); // convert the Integer ArrayList 'temp' to primitive int array

        DFA complement = new DFA(m.transitionTable, finStates); // when creating the complement of a DFA, the transition table remains the same, so we can use m.transitionTable here

        return complement;
    }
    
    // credits function, prints out names of the group members (so that the grader can easily see who contributed while running the Tester class)
    public static String credits() {
        String credits = "Credits: Ethan Fischer, John Lawler, Luke wells";
        return credits;
    }
    
    // helper method to determine if the current state is a final state
    static boolean contains(final int[] arr, final int key) {
        for (int element : arr) {
            if(element == key) {
                return true;
            }
        }
        return false;
    }
}


