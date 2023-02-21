/**
 * Tests DFA class to generate score
 */
public class Tester {

	/**
	 * Executes tests against the DFA class and produces a final score.
	 *
	 * @param args not used
	 */
	public static void main(String[] args) {

		/*
		 * Answer sheet:
		 * DFA one should accept: 000010, 0010
		 * DFA two should accept: 1100100
		 * DFA three should accept: 1110001
		 */

		String[] testStrings = {"000010", "1110001", "0010", "1100100", "", "0010010110011"};

		// A DFA that will check if a string ends in "10"
		int[][] testOneTransitionTable = new int[3][2];

		// format is as follows
		// transitionTable[currentState][currentInput] = nextState
		testOneTransitionTable[0][1] = 1; // at state 0, if 1 is input, transition to state 1
		testOneTransitionTable[0][0] = 0;
		testOneTransitionTable[1][0] = 2;
		testOneTransitionTable[1][1] = 1;
		testOneTransitionTable[2][0] = 0;
		testOneTransitionTable[2][1] = 1;
		int[] testOneFinalStates = {2};

		// A DFA that will check if a string has an odd number of "1" and ends in at
		// least two "0"
		int[][] testTwoTransitionTable = new int[4][2];
		testTwoTransitionTable[0][1] = 1;
		testTwoTransitionTable[0][0] = testTwoTransitionTable[1][1] = testTwoTransitionTable[2][1] = testTwoTransitionTable[3][1] = 0;
		testTwoTransitionTable[1][0] = 2;
		testTwoTransitionTable[2][0] = testTwoTransitionTable[3][0] = 3;
		int[] testTwoFinalStates = {3};

		// A DFA that will check if a string has an even number of "1" and an odd
		// number of "0"
		int[][] testThreeTransitionTable = new int[4][2];
		testThreeTransitionTable[0][0] = 1;
		testThreeTransitionTable[0][1] = 2;
		testThreeTransitionTable[1][0] = 0;
		testThreeTransitionTable[1][1] = 3;
		testThreeTransitionTable[2][0] = 3;
		testThreeTransitionTable[2][1] = 0;
		testThreeTransitionTable[3][0] = 2;
		testThreeTransitionTable[3][1] = 1;
		int[] testThreeFinalStates = {1};

		// A DFA that will accept strings whose second to last symbol is '0'
		int[][] testFourTransitionTable = new int[4][2];
		testFourTransitionTable[0][0] = 1;
		testFourTransitionTable[0][1] = 0;
		testFourTransitionTable[1][0] = 3;
		testFourTransitionTable[1][1] = 2;
		testFourTransitionTable[2][0] = 1;
		testFourTransitionTable[2][1] = 0;
		testFourTransitionTable[3][0] = 3;
		testFourTransitionTable[3][1] = 2;
		int[] testFourFinalStates = {2, 3};

		// A DFA that will only accept empty strings
		int[][] testFiveTransitionTable = new int[2][2];
		testFiveTransitionTable[0][0] = 1;
		testFiveTransitionTable[0][1] = 1;
		testFiveTransitionTable[1][0] = 1;
		testFiveTransitionTable[1][1] = 1;
		int[] testFiveFinalStates = {0};

		//A DFA that accepts strings that end in 1
		int[][] testSixTransitionTable = new int[2][2];
		testSixTransitionTable[0][0] = 0;
		testSixTransitionTable[0][1] = 1;
		testSixTransitionTable[1][0] = 0;
		testSixTransitionTable[1][1] = 1;
		int[] testSixFinalStates = {1};

		DFA one = new DFA(testOneTransitionTable, testOneFinalStates);
		DFA two = new DFA(testTwoTransitionTable, testTwoFinalStates);
		DFA three = new DFA(testThreeTransitionTable, testThreeFinalStates);
		DFA four = new DFA(testFourTransitionTable, testFourFinalStates);
		DFA five = new DFA(testFiveTransitionTable, testFiveFinalStates);
		DFA six = new DFA(testSixTransitionTable, testSixFinalStates);

		int testsPassed = 0;
		int testsRan = 0;

		// Test each
		testsPassed += test(one, "one", testStrings, new boolean[]{true, false, true, false, false, false});
		testsRan += testStrings.length;

		testsPassed += test(two, "two", testStrings, new boolean[]{false, false, false, true, false, false});
		testsRan += testStrings.length;

		testsPassed += test(three, "three", testStrings, new boolean[]{false, true, false, false, false, true});
		testsRan += testStrings.length;

		testsPassed += test(four, "four", testStrings, new boolean[]{false, true, false, true, false, false});
		testsRan += testStrings.length;

		testsPassed += test(five, "five", testStrings, new boolean[]{false, false, false, false, true, false});
		testsRan += testStrings.length;

		testsPassed += test(six, "six", testStrings, new boolean[]{false, true, false, false, false, true});
		testsRan += testStrings.length;

		//Testing the union, intersection, difference, and complement
		// Union Fails Most Tests
		testsPassed += test(DFA.union(one, two), "DFA one OR two", testStrings, new boolean[]{true, false, true, true, false, false});
		testsRan += testStrings.length;

		testsPassed += test(DFA.union(one, five), "DFA one OR five", testStrings, new boolean[]{true, false, true, false, true, false});
		testsRan += testStrings.length;

		testsPassed += test(DFA.union(two, four), "DFA two OR four", testStrings, new boolean[]{false, true, false, true, false, false});
		testsRan += testStrings.length;

		testsPassed += test(DFA.intersection(three, four), "DFA three AND four", testStrings, new boolean[]{false, true, false, false, false, false});
		testsRan += testStrings.length;

		// Intersection Completely Breaks Here \/
		testsPassed += test(DFA.intersection(three, six), "DFA three AND six", testStrings, new boolean[]{false, true, false, false, false, true});
		testsRan += testStrings.length;

		testsPassed += test(DFA.intersection(four, six), "DFA four AND six", testStrings, new boolean[]{false, true, false, false, false, false});
		testsRan += testStrings.length;

		testsPassed += test(DFA.difference(three, four), "DFA three - four", testStrings, new boolean[]{false, false, false, false, false, true});
		testsRan += testStrings.length;

		testsPassed += test(DFA.complement(one), "Complement of DFA one", testStrings, new boolean[]{false, true, false, true, true, true});
		testsRan += testStrings.length;

		testsPassed += test(DFA.complement(five), "Complement of DFA five", testStrings, new boolean[]{true, true, true, true, false, true});
		testsRan += testStrings.length;

		//System.out.println(DFA.complement(five).getFinalStates());

		System.out.print("\n\nYou got " + testsPassed + "/" + testsRan + " correct.\n\n");

        /*System.out.println(three.getFinalStates());
        System.out.println(DFA.complement(three).getFinalStates());
        System.out.println(DFA.union(one, three).getFinalStates());
        System.out.println(DFA.intersection(one, three).getFinalStates());
        System.out.println(DFA.intersection(one, three).run("00111000010"));
        System.out.println(DFA.intersection(three, four).run("0011001000100"));
        System.out.println(DFA.difference(one, three).run("001100100010"));*/

	}

	/**
	 * Tests a DFA using a set of testStrings and compares the results to desired results to return a score.
	 *
	 * @param dfa                DFA to be tested
	 * @param testName           name of test ex: "one"
	 * @param testStrings        array of strings to be tested
	 * @param desiredTestResults booleans corresponding to test strings indicating whether test is expected to pass or fail
	 * @return number of tests passed
	 */
	public static int test(DFA dfa, String testName, String[] testStrings, boolean[] desiredTestResults) {

		int testsPassed = 0;

		for (int i = 0; i < testStrings.length; i++) {

			System.out.printf("DFA %5s with input %10s ", testName, testStrings[i]);

			if (dfa.run(testStrings[i])) {

				System.out.print("Accepted");

				if (desiredTestResults[i]) {
					System.out.println(" \t(passed test)");
					testsPassed++;
				} else {
					System.out.println(" \t(failed test)");
				}

			} else {

				System.out.print("Rejected");

				if (!desiredTestResults[i]) {
					System.out.println(" \t(passed test)");
					testsPassed++;
				} else {
					System.out.println(" \t(failed test)");
				}

			}
		}

		return testsPassed;
	}
}