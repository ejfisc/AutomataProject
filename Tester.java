
public class Tester {

	public static void main(String[] args) {
		// TODO taken from discord, probably need to mold it to our needs
		
	    /*    
	      The professor defined that the alphabet is {0, 1} so we can use the indexes in our 2D int array directly
	      We'll also define each state from 0-N
	      A 2D array access should correspond to the next state
	      In essence,
	      transitions[current_state][input_char] = new_state;
	    */
	    
	    // Look at the code I posted below for the test cases and insert them here
	
		String[] testStrings = {"000010", "1110001", "0010", "1100100"};
		/*
		 * Answer sheet:
		 * DFA one should accept:   000010, 0010
		 * DFA two should accept:   1100100
		 * DFA three should accept: 111000
		 */
		// A DFA that will check if a string ends in "10"
		int[][] tOne = new int[3][2];

		// Populating the transition table
		tOne[0][1] = 1;
		tOne[0][0] = 0;
		tOne[1][0] = 2;
		tOne[1][1] = 0;
		tOne[2][0] = tOne[2][1] = 0;

		// Declaring the final states
		int[] fOne = {2};

		// A DFA that will check if a string has an odd number of "1" and ends in at
		// least two "0"
		int[][] tTwo = new int[4][2];
		tTwo[0][1] = 1;
		tTwo[0][0] = tTwo[1][1] = tTwo[2][1] = tTwo[3][1] = 0;
		tTwo[1][0] = 2;
		tTwo[2][0] = tTwo[3][0] = 3;
		int[] fTwo = {3};

		// A DFA that will check if a string has an even number of "1" and an odd
		// number of "0"
		int[][] tThree = new int[4][2];
		tThree[0][0] = 1;
		tThree[0][1] = 2;
		tThree[1][0] = 0;
		tThree[1][1] = 3;
		tThree[2][0] = 3;
		tThree[2][1] = 0;
		tThree[3][0] = 2;
		tThree[3][1] = 1;
		int[] fThree = {1};
		
		DFA one = new DFA(tOne, fOne);
		DFA two = new DFA(tTwo, fTwo);
		DFA three = new DFA(tThree, fThree);
		for(int i = 0; i < testStrings.length; i++)
		{
		    System.out.print("First DFA with input, " + testStrings[i] + ": ");
		    if(one.run(testStrings[i]))
		        System.out.println("Accepted");
		    else
		        System.out.println("Rejected");
		}
		for(int i = 0; i < testStrings.length; i++)
		{
		    System.out.print("Second DFA with input, " + testStrings[i] + ": ");
		    if(two.run(testStrings[i]))
		        System.out.println("Accepted");
		    else
		        System.out.println("Rejected");
		}
		for(int i = 0; i < testStrings.length; i++)
		{
		    System.out.print("Third DFA with input, " + testStrings[i] + ": ");
		    if(three.run(testStrings[i]))
		        System.out.println("Accepted");
		    else
		        System.out.println("Rejected");
		}

	}

}

