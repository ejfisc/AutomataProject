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
        ArrayList<Integer> temp = new ArrayList<>(); // used for creating the new final states list
        int[][] unionTable = combineTransitionTables(m1, m2); // combine transition tables

        int m2State = 0;
        int m1State = 0;
        for(int i = 0; i < unionTable.length; i++) {
            m2State = i % m1.transitionTable.length;
            m1State = (i - m2State) / m1.transitionTable.length;

            if(contains(m1.finalStates, m1State) || contains(m2.finalStates, m2State)) {
                temp.add(i);
            }
        }

        int[] finStates = temp.stream().mapToInt(i -> i).toArray(); // convert the Integer ArrayList 'temp' to primitive int array
        DFA union = new DFA(unionTable, finStates);

        return union;
    }

    // creates a DFA that accepts the intersection of M1 and M2
    public static DFA intersection(DFA m1, DFA m2) {
        
        ArrayList<Integer> temp = new ArrayList<>(); // used for creating the new final states list

        int[][] transTable = combineTransitionTables(m1, m2);
        
        int m2State = 0;
        int m1State = 0;
        for(int i = 0; i < transTable.length; i++) {
            // use the next intersection state = (next m1 state * num of m1 states) + next m2 state to derive which m1 state and m2 state the i state represents
            m2State = i % m1.transitionTable.length;
            m1State = (i - m2State) / m1.transitionTable.length;

            // because this is intersection, check if both of those states are considered final states for their respective DFAs
            if(contains(m1.finalStates, m1State) && contains(m2.finalStates, m2State)) {
                temp.add(i); // this is a final state
            }
        }

        int[] finStates = temp.stream().mapToInt(i -> i).toArray(); // convert the Integer ArrayList 'temp' to primitive int array

        DFA intersection = new DFA(transTable, finStates);

        return intersection;
    }
    
    // creates a DFA that accepts the set difference of M1 and M2 (set of strings accepted by M1 but not M2)
    public static DFA difference(DFA m1, DFA m2) {
    	
    	// i think this way would work too, possibly even better than this longer way
    	// m2 = complement(m2);
    	// return intersection(m1, m2);
    	
    	
    	ArrayList<Integer> temp = new ArrayList<>(); // used for creating the new final states list	
    	
    	// loop for length of original transition table
    	for(int i = 0; i < m1.transitionTable.length; i++) {
    		
    		// if this state is a final state in m1 and not a final state in m2
    		if (m1.finalStates[i] == 1 && m2.finalStates[i] != 1) {
    			temp.add(i);
    		}
    	}
    	
    	int[][] transTable = combineTransitionTables(m1, m2); // combine transition tables
    	int[] finStates = temp.stream().mapToInt(i -> i).toArray(); // convert the Integer ArrayList 'temp' to primitive int array
    	
    	DFA difference = new DFA(transTable, finStates);
    	
        return difference;
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

        int[] finStates = temp.stream().mapToInt(i -> i).toArray(); // convert the Integer ArrayList 'temp' to primitive int array

        DFA complement = new DFA(m.transitionTable, finStates); // when creating the complement of a DFA, the transition table remains the same, so we can use m.transitionTable here

        return complement;
    }
    
    // credits function, prints out names of the group members (so that the grader can easily see who contributed while running the Tester class)
    public static String credits() {
        String credits = "Credits: Ethan Fischer, John Lawler, Luke Wells";
        return credits;
    }
    
    // helper method to determine if the current state is a final state
    private static boolean contains(final int[] arr, final int key) {
        for (int element : arr) {
            if(element == key) {
                return true;
            }
        }
        return false;
    }

    private static int[][] combineTransitionTables(DFA m1, DFA m2) {
        int numOfM1States = m1.transitionTable.length;
        int numOfM2States = m2.transitionTable.length;
        int[][] intersectionTransitions = new int[numOfM1States*numOfM2States][2]; // new transition table

        /* Consider two 3 state DFAs, these would be the new states:
         * 0 -> m1_0, m2_0
         * 1 -> m1_0, m2_1
         * 2 -> m1_0, m2_2
         * 3 -> m1_1, m2_0
         * 4 -> m1_1, m2_1
         * 5 -> m1_1, m2_2
         * 6 -> m1_2, m2_0
         * 7 -> m1_2, m2_1
         * 8 -> m1_2, m2_2
         * to determine which state in the intersection state table to put a transition, we use
         next intersect state = (next m1 state * # of m1 states) + next m2 state */
         
         int nxtM1_0State = 0; 
         int nxtM1_1State = 0;
         int nxtM2State = 0;
         for(int i = 0; i < intersectionTransitions.length; i++) {
             for(int j = 0; j < numOfM1States; j++) {
                 /* I split the next M1 state into two because it allowed me to take the next m1 state computation out of the m2 loop so it's more efficient,
                 But I needed to be able to remember what the next state was for both inputs. */
                 nxtM1_0State = m1.transitionTable[j][0];
                 nxtM1_1State = m1.transitionTable[j][1];
                 for(int k = 0; k < numOfM2States; k++) {
                     // 0 transition
                     nxtM2State = m2.transitionTable[k][0];
                     intersectionTransitions[i][0] = (nxtM1_0State * numOfM1States) + nxtM2State;
                     // 1 transition
                     nxtM2State = m2.transitionTable[k][1];
                     intersectionTransitions[i][1] = (nxtM1_1State * numOfM1States) + nxtM2State;
                 }
             }
         }

         return intersectionTransitions;
    }


}


