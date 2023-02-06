import java.util.List;
import java.util.Map;

public class DFA implements FSA {

    private int[][] transitionTable; // transitionTable[currentState][input] = nextState
    private int[] finalStates;
    private int initState;
	private String[] states;
	private String[] alphabet;

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

    // helper method to determine if the current state is a final state
    boolean contains(final int[] arr, final int key) {
        for (int element : arr) {
            if(element == key) {
                return true;
            }
        }
        return false;
    }

	@Override
	public void setStates(String[] states) { this.states = states; }

	@Override
	public void setAlphabet(String[] alphabet) { this.alphabet = alphabet; }

	@Override
	public void setTransitionFunction(Map<String, Map<String, List<String>>> matrix) {
		this.transitionTable = matrix; // this isn't right
	}

	@Override
	public void setInitialState(String state) {
		this.initState = Integer.parseInt(state);
		
	}

	@Override
	public void setFinalStates(String[] states) {
		this.finalStates = Integer.parseInt(states); // also not right
		
	}

	@Override
	public boolean checkString(String input) {
		// TODO Auto-generated method stub
		return false;
	}
}


