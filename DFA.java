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
    
    // credits function, prints out names of the group members (so that the grader can easily see who contributed while running the Tester class)
    public static String credits() {
        return "Credits: Ethan Fischer, John Lawler, Luke Wells";
    }
    
    // helper method to determine if the current state is a final state
    private static boolean contains(final int[] arr, final int key) {
        
    	// check to see if any element in the array matches key, if so return true
    	for (int element : arr) {
            if(element == key) {
                return true;
            }
        }
        return false;
    }

    // combines the transition tables of two DFAs for the union or intersection of the two
    public static int[][] combineTransitionTables(DFA m1, DFA m2) {
    	
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
}


