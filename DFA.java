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

    // credits function, prints out names of the group members (so that the grader can easily see who contributed while running the Tester class)
    public static String credits() {
        return "Credits: Ethan Fischer, John Lawler, Luke Wells";
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


    // creates a DFA that accepts the union of M1 and M2 (M1 or M2)
    public static DFA union(DFA m1, DFA m2) {
        ArrayList<Integer> temp = new ArrayList<>(); // used for creating the new final states list
        int[][] unionTable = combineTransitionTables(m1, m2); // combine transition tables

        int m2State = 0;
        int m1State = 0;
        for(int i = 0; i < unionTable.length; i++) {
        	m1State = i / m2.transitionTable.length;
            m2State = i % m2.transitionTable.length;
            

            if(contains(m1.finalStates, m1State) || contains(m2.finalStates, m2State)) {
                temp.add(i);
            }
        }

        return new DFA(unionTable, temp.stream().mapToInt(i -> i).toArray());
    } 

    // creates a DFA that accepts the intersection of M1 and M2 (M1 & M2)
    public static DFA intersection(DFA m1, DFA m2) {

        // create a new array of final states that contains the intersection of the final states of the two input DFAs
        ArrayList<Integer> newFinalStates = new ArrayList<Integer>();
        
        // add the intersection of the final states between the DFAs
        for (int i = 0; i < m1.finalStates.length; i++) {
            for (int j = 0; j < m2.finalStates.length; j++) {
                newFinalStates.add(m1.finalStates[i] * m2.transitionTable.length + m2.finalStates[j]);
            }
        }

        // return a DFA of the combined transition table, and an int[] of the new final states
        return new DFA(combineTransitionTables(m1, m2), newFinalStates.stream().mapToInt(i -> i).toArray());
    }
     
    // creates a DFA that accepts the set difference of M1 and M2 (set of strings accepted by M1 but not M2)
    public static DFA difference(DFA m1, DFA m2) {
    	return intersection(m1, complement(m2));	// difference is equivalent to (m1 (intersection) !m2)
    }
    
    // creates a DFA that is the complement of the given DFA (swap the final and non-final states)
    public static DFA complement(DFA m) {
    	
        ArrayList<Integer> temp = new ArrayList<>(); // used for creating the new final states list

        // loop through states in m.transitionTable`
        for(int i = 0; i < m.transitionTable.length; i++) {
        	
            // if the current state is not in m.finalStates, add that state to the list of final states for our new DFA 
            if(!contains(m.finalStates, i)) {
                temp.add(i);
            }
        }
        
        // return new DFA of the transition table, and an int[] of the complement final states
        return new DFA(m.transitionTable, temp.stream().mapToInt(i -> i).toArray());
    }

    // determines if the language of the DFA is empty
    public boolean isEmptyLanguage() {
        boolean[] visited = new boolean[transitionTable.length];

        dfs(visited, initState); // perform depth first search from init state, if a final state has been visited, the language is not empty

        for(int state : finalStates) {
            if(visited[state]) {
                return false; // a final state is reachable, so the language is not empty
            }
        }

        return true; // no final state is reachable, so the language is empty
    }

    // determines if the language of the DFA is universal
    public boolean isUniversalLanguage() {
        // check if all binary bit strings of the same length as the transition table are accepted by the DFA
        int inputLength = transitionTable[0].length;
        for(int i = 0; i < (i << inputLength); i++) { // iterate over all binary bit strings
            int state = 0; // start at the init state
            for(int j = 0; j < inputLength; j++) {
                int input = (i >> j) & 1; // extract the jth bit of i
                state = transitionTable[state][input]; // transition to the next state
            }
            if(!contains(finalStates, state)) {
                return false; // if the final state is not reached, the string is not accepted, so the language is not universal
            }
        }
        return true; // all binary bit strings of the same length as the transition table are accepted, so the langauge is universal
    }

    // checks for loops in the DFA to determine if the language is infinite or not
    public boolean isInfinite() {
        // Perform depth-first search from each state to detect loops of any length
        for (int i = 0; i < transitionTable.length; i++) {
            boolean[] visited = new boolean[transitionTable.length];
            ArrayList<Integer> path = new ArrayList<>();
            if (dfsPath(i, i, visited, path)) {
                // If a loop was found that leads to a final state, the language is infinite
                if (contains(finalStates, path.get(path.size() - 1))) {
                    return true;
                }
            }
        }

        // If no loops of any length were found, the language is finite
        return false;
    }

    // checks if the language for the given DFA is equivalent to the language of this DFA
    @Override
    public boolean equals(Object obj) {
        // check if obj is a valid DFA
        if(obj == null) return false;
        if(obj.getClass() != this.getClass()) return false;
        final DFA m2 = (DFA) obj; // cast the Object into a DFA
        
        // Check if the number of states is the same
        if (this.transitionTable.length != m2.transitionTable.length) {
            return false;
        }
        
        // Check if the number of final states is the same
        if (this.finalStates.length != m2.finalStates.length) {
            return false;
        }
        
        // Check if the final states are the same
        for (int i = 0; i < this.finalStates.length; i++) {
            if (this.finalStates[i] != m2.finalStates[i]) {
                return false;
            }
        }
        
        // Check if the transition table is the same
        for (int i = 0; i < this.transitionTable.length; i++) {
            for (int j = 0; j < this.transitionTable[i].length; j++) {
                if (this.transitionTable[i][j] != m2.transitionTable[i][j]) {
                    return false;
                }
            }
        }
        // If all checks pass, the DFAs are equivalent
        return true;
    }

    // determines if the language of this DFA is a subset of the given DFA
    public boolean isSubsetOf(DFA m2) {
        // // If we've checked every state and transition and haven't returned false, this DFA is a subset
        // return true;
        // Check that the alphabets of both DFAs are the same
        int minStates = Math.min(transitionTable.length, m2.transitionTable.length);
        int minInputs = Math.min(transitionTable[0].length, m2.transitionTable[0].length);

        // Iterate over the transition tables of both DFAs using the minimum size
        for (int i = 0; i < minStates; i++) {
            for (int j = 0; j < minInputs; j++) {
                int nextState1 = transitionTable[i][j];
                int nextState2 = m2.transitionTable[i][j];
                
                // If the current input symbol takes us to different states, return false
                if (nextState1 != nextState2) {
                    return false;
                }
            }
        }
        
        // If we reach here, it means all transitions in the current DFA are also in m2,
        // so the language of the current DFA is a subset of the language of m2
        return true;
    }
    
    /*-----------------------------------------------------
     * Private Helper Methods
     *-----------------------------------------------------*/
    
    // helper method to determine if the current state is a final state
    private static boolean contains(final int[] arr, final int key) {
    	for (int element : arr) {
            if(element == key) {
                return true;
            }
        }
        return false;
    }

    // combines the transition tables of two DFAs for the union or intersection of the two
    private static int[][] combineTransitionTables(DFA m1, DFA m2) {
    	
    	// create a new table that is the size of the combined transition table
        int[][] combinedTable = new int[m1.transitionTable.length * m2.transitionTable.length][2];

        // for every spot on each transition table
        for (int i = 0; i < m1.transitionTable.length * m2.transitionTable.length; i++) {
        	
        	// for every character in the alphabet
            for (int j = 0; j < 2; j++) {
            	
            	// set next state appropriately
                int nextState1 = m1.transitionTable[i / m2.transitionTable.length][j];
                int nextState2 = m2.transitionTable[i % m2.transitionTable.length][j];
                
                // create the new combined state and add it to the table
                int newState = nextState1 * m2.transitionTable.length + nextState2;
                combinedTable[i][j] = newState;
            }
        }

        // return the combined transition table
        return combinedTable;
    } 

    // depth first search helper function for isEmptyLanguage
    private void dfs(boolean[] visited, int state) {
        visited[state] = true;

        for(int input = 0; input < transitionTable[state].length; input++) {
            int nextState = transitionTable[state][input];
            if(!visited[nextState]) {
                dfs(visited, nextState);
            }
        }
    }

    // depth first search helper function for finding a loop for isInfinite
    private boolean dfsPath(int startState, int currentState, boolean[] visited, ArrayList<Integer> path) {
        visited[currentState] = true;
        path.add(currentState);
        for (int j = 0; j < 2; j++) { // Assume binary alphabet
            int nextState = transitionTable[currentState][j];
            if (nextState == startState && visited[startState] && path.size() > 1) {
                return true; // Loop found
            }
            if (!visited[nextState] && dfsPath(startState, nextState, visited, path)) {
                return true; // Loop found further down the path
            }
        }
        path.remove(path.size() - 1);
        visited[currentState] = false;
        return false;
    }
}


