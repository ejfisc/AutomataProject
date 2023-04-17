/**
 * Tests DFA class to generate score
 */
import java.util.ArrayList;
public class Tester {

    /**
     * Executes tests against the DFA class and produces a final score.
     *
     * @param args not used
     */
    public static ArrayList<String> failed = new ArrayList<String>();

    public static void main(String[] args) {

        /*
         * Answer sheet:
         * DFA one should accept: 000010, 0010
         * DFA two should accept: 1100100
         * DFA three should accept: 1110001
         */

        String[] testStrings = {"000010", "1110001", "0010", "1100100", "", "0010010110011"};

        // A DFA that will check if a string ends in "10"
        int[][] tOne = new int[3][2];

        // format is as follows
        // transitionTable[currentState][currentInput] = nextState
        tOne[0][1] = 1; // at state 0, if 1 is input, transition to state 1
        tOne[0][0] = 0;
        tOne[1][0] = 2;
        tOne[1][1] = 1;
        tOne[2][0] = 0;
        tOne[2][1] = 1;
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

        // A DFA that will accept strings whose second to last symbol is '0'
        int[][] tFour = new int[4][2];
        tFour[0][0] = 1;
        tFour[0][1] = 0;
        tFour[1][0] = 3;
        tFour[1][1] = 2;
        tFour[2][0] = 1;
        tFour[2][1] = 0;
        tFour[3][0] = 3;
        tFour[3][1] = 2;
        int[] fFour = {2, 3};

        // A DFA that will only accept empty strings
        int[][] tFive = new int[2][2];
        tFive[0][0] = 1;
        tFive[0][1] = 1;
        tFive[1][0] = 1;
        tFive[1][1] = 1;
        int[] fFive = {0};

        // A DFA that accepts strings that end in 1
        int[][] tSix = new int[2][2];
        tSix[0][0] = 0;
        tSix[0][1] = 1;
        tSix[1][0] = 0;
        tSix[1][1] = 1;
        int[] fSix = {1};

        /*
         * NOTE: These are new cases made specifcally for part 3 of the project.
         */
        // A DFA that accepts nothing
        int[][] tSeven = new int[3][2];
        tSeven[0][0] = 0;
        tSeven[0][1] = 1;
        tSeven[1][0] = 0;
        tSeven[1][1] = 1;
        int[] fSeven = {};

        // A DFA that accepts strings with only one "1"
        int[][] tEight = new int[3][2];
        tEight[0][0] = 0;
        tEight[0][1] = tEight[1][0] = 1;
        tEight[1][1] = 2;
        tEight[2][0] = tEight[2][1] = 2;
        int[] fEight = {1};

        // A DFA that accepts odd length strings
        int[][] tNine = new int[2][2];
        tNine[0][0] = tNine[0][1] = 1;
        tNine[1][0] = tNine[1][1] = 0;
        int[] fNine = {1};

        // A DFA that only accepts a string of 1/0 or empty
        int[][] tTen = new int[3][2];
        tTen[0][0] = tTen[0][1] = 1;
        tTen[1][0] = tTen[1][1] = 2;
        tTen[2][0] = tTen[2][1] = 2;
        int[] fTen = {0, 1};

        // A DFA that only accepts the string "1010"
        int[][] tEleven = new int[6][2];
        tEleven[0][1] = 1;
        tEleven[1][0] = 2;
        tEleven[2][1] = 3;
        tEleven[3][0] = 4;
        tEleven[4][0] = tEleven[4][1] = tEleven[0][0] = tEleven[1][1] = tEleven[2][0] = tEleven[3][1] = 5;
        tEleven[5][0] = tEleven[5][1] = 5;
        int[] fEleven = {4};

        // A DFA that accepts everything
        int[][] tTwelve = new int[2][2];
        tTwelve[0][1] = 1;
        tTwelve[1][1] = 1;
        tTwelve[1][0] = 0;
        tTwelve[0][0] = 0;
        int[] fTwelve = {0, 1};

        // A DFA with the same language as ten (empty, 0, 1) but with different state structure
        int[][] tThirteen = new int[4][2];
        tThirteen[0][0] = 1;
        tThirteen[0][1] = 2;
        tThirteen[1][0] = tThirteen[1][1] = 3;
        tThirteen[2][0] = tThirteen[2][1] = 3;
        tThirteen[3][0] = tThirteen[3][1] = 3;
        int[] fThirteen = {0, 1, 2};

        // A DFA with the same language as six (strings ending in 1) but with an unreachable state
        int[][] tFourteen = new int[4][2];
        tFourteen[0][0] = 1;
        tFourteen[0][1] = 2;
        tFourteen[1][0] = 1;
        tFourteen[1][1] = 2;
        tFourteen[2][0] = 1;
        tFourteen[2][1] = 2;
        tFourteen[3][0] = 2;
        tFourteen[3][1] = 1;
        int[] fFourteen = {2, 3};

        DFA one = new DFA(tOne, fOne);
        DFA two = new DFA(tTwo, fTwo);
        DFA three = new DFA(tThree, fThree);
        DFA four = new DFA(tFour, fFour);
        DFA five = new DFA(tFive, fFive);
        DFA six = new DFA(tSix, fSix);
        DFA seven = new DFA(tSeven, fSeven);
        DFA eight = new DFA(tEight, fEight);
        DFA nine = new DFA(tNine, fNine);
        DFA ten = new DFA(tTen, fTen);
        DFA eleven = new DFA(tEleven, fEleven);
        DFA twelve = new DFA(tTwelve, fTwelve);
        DFA thirteen = new DFA(tThirteen, fThirteen);
        DFA fourteen = new DFA(tFourteen, fFourteen);

        int testsPassed = 0;
        int testsRan = 0;

        // Test each
        testsPassed += test(one, "one", testStrings,
                new boolean[]{true, false, true, false, false, false});
        testsRan += testStrings.length;

        testsPassed += test(two, "two", testStrings,
                new boolean[]{false, false, false, true, false, false});
        testsRan += testStrings.length;

        testsPassed += test(three, "three", testStrings,
                new boolean[]{false, true, false, false, false, true});
        testsRan += testStrings.length;

        testsPassed += test(four, "four", testStrings,
                new boolean[]{false, true, false, true, false, false});
        testsRan += testStrings.length;

        testsPassed += test(five, "five", testStrings,
                new boolean[]{false, false, false, false, true, false});
        testsRan += testStrings.length;

        testsPassed += test(six, "six", testStrings,
                new boolean[]{false, true, false, false, false, true});
        testsRan += testStrings.length;

        testsPassed += test(seven, "seven", testStrings,
                new boolean[]{false, false, false, false, false, false});
        testsRan += testStrings.length;

        testsPassed += test(eight, "eight", testStrings,
                new boolean[]{true, false, true, false, false, false});
        testsRan += testStrings.length;

        testsPassed += test(nine, "nine", testStrings,
                new boolean[]{false, true, false, true, false, true});
        testsRan += testStrings.length;

        testsPassed += test(ten, "ten", testStrings,
                new boolean[]{false, false, false, false, true, false});
        testsRan += testStrings.length;

        testsPassed += test(eleven, "eleven", testStrings,
                new boolean[]{false, false, false, false, false, false});
        testsRan += testStrings.length;

        testsPassed += test(twelve, "twelve", testStrings,
                new boolean[]{true, true, true, true, true, true});
        testsRan += testStrings.length;

        //Testing the union, intersection, difference, and complement
        testsPassed += test(DFA.union(one, two), "one OR two", testStrings,
                new boolean[]{true, false, true, true, false, false});
        testsRan += testStrings.length;

        testsPassed += test(DFA.union(one, five), "one OR five", testStrings,
                new boolean[]{true, false, true, false, true, false});
        testsRan += testStrings.length;

        testsPassed += test(DFA.union(two, four), "two OR four", testStrings,
                new boolean[]{false, true, false, true, false, false});
        testsRan += testStrings.length;

        testsPassed += test(DFA.union(five, six), "five OR six", testStrings,
                new boolean[]{false, true, false, false, true, true});
        testsRan += testStrings.length;

        testsPassed += test(DFA.intersection(three, four), "three AND four", testStrings,
                new boolean[]{false, true, false, false, false, false});
        testsRan += testStrings.length;

        testsPassed += test(DFA.intersection(three, six), "three AND six", testStrings,
                new boolean[]{false, true, false, false, false, true});
        testsRan += testStrings.length;

        testsPassed += test(DFA.intersection(four, six), "four AND six", testStrings,
                new boolean[]{false, true, false, false, false, false});
        testsRan += testStrings.length;

        testsPassed += test(DFA.intersection(one, five), "one AND five", testStrings,
                new boolean[]{false, false, false, false, false, false});
        testsRan += testStrings.length;

        testsPassed += test(DFA.difference(three, four), "three - four", testStrings,
                new boolean[]{false, false, false, false, false, true});
        testsRan += testStrings.length;

        testsPassed += test(DFA.difference(four, three), "four - three", testStrings,
                new boolean[]{false, false, false, true, false, false});
        testsRan += testStrings.length;

        testsPassed += test(DFA.difference(two, one), "two - one", testStrings,
                new boolean[]{false, false, false, true, false, false});
        testsRan += testStrings.length;

        testsPassed += test(DFA.difference(four, two), "four - two", testStrings,
                new boolean[]{false, true, false, false, false, false});
        testsRan += testStrings.length;

        testsPassed += test(DFA.complement(one), "Complement of one", testStrings,
                new boolean[]{false, true, false, true, true, true});
        testsRan += testStrings.length;

        testsPassed += test(DFA.complement(two), "Complement of two", testStrings,
                new boolean[]{true, true, true, false, true, true});
        testsRan += testStrings.length;

        testsPassed += test(DFA.complement(three), "Complement of three", testStrings,
                new boolean[]{true, false, true, true, true, false});
        testsRan += testStrings.length;

        testsPassed += test(DFA.complement(four), "Complement of four", testStrings,
                new boolean[]{true, false, true, false, true, true});
        testsRan += testStrings.length;

        testsPassed += test(DFA.complement(five), "Complement of five", testStrings,
                new boolean[]{true, true, true, true, false, true});
        testsRan += testStrings.length;

        testsPassed += test(DFA.complement(six), "Complement of six", testStrings,
                new boolean[]{true, false, true, true, true, false});
        testsRan += testStrings.length;

        testsPassed += emptyChecker(new DFA[]{one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen},
                new boolean[]{false, false, false, false, false, false, true, false, false, false, false, false, false, false});
        testsRan += 14;

        testsPassed += universalChecker(new DFA[]{one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen},
                new boolean[]{false, false, false, false, false, false, false, false, false, false, false, true, false, false});
        testsRan += 14;

        testsPassed += infiniteChecker(new DFA[]{one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen},
                new boolean[]{true, true, true, true, false, true, false, true, true, false, false, true, false, true});
        testsRan += 14;

        testsPassed += subsetChecker(new DFA[][]{{eight, ten}, {eleven, one}, {one, two}, {seven, twelve}, {ten, thirteen}, {six, fourteen}},
                new boolean[]{false, true, false, true, true, true});
        testsRan += 6;

        testsPassed += equalsChecker(new DFA[][]{{ten, eight}, {eleven, one}, {one, two}, {seven, twelve}, {ten, thirteen}, {six, fourteen}},
                new boolean[]{false, false, false, false, true, true});
        testsRan += 6;

        testsPassed += identicalChecker(new DFA[] { one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen });
        testsRan += 105;

        System.out.println();
        for (int i = 0; i < failed.size(); i++) {
            System.out.print(failed.get(i));
        }
        System.out.println("\nYou got " + testsPassed + "/" + testsRan + " correct.");
        System.out.println("Credits: " + DFA.credits());
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
            if (dfa.run(testStrings[i]) != desiredTestResults[i]) {
                if (desiredTestResults[i])
                    failed.add(testName + " with input: [" + testStrings[i] + "] returned FALSE. Expected TRUE\n");
                else
                    failed.add(testName + " with input: [" + testStrings[i] + "] returned TRUE. Expected FALSE\n");
            } else
                testsPassed++;
        }

        return testsPassed;
    }

    public static int emptyChecker(DFA[] dfaList, boolean[] desiredTestResults) {
        int testsPassed = 0;

        for (int i = 0; i < dfaList.length; i++) {
            if (dfaList[i].isEmptyLanguage() != desiredTestResults[i]) {
                if (desiredTestResults[i])
                    failed.add("DFA " + (i + 1) + ": incorrectly stated it was NOT empty\n");
                else
                    failed.add("DFA " + (i + 1) + ": incorrectly stated it was empty\n");
            } else
                testsPassed++;
        }
        return testsPassed;
    }

    public static int universalChecker(DFA[] dfaList, boolean[] desiredTestResults) {
        int testsPassed = 0;

        for (int i = 0; i < dfaList.length; i++) {
            if (dfaList[i].isUniversalLanguage() != desiredTestResults[i]) {
                if (desiredTestResults[i])
                    failed.add("DFA " + (i + 1) + ": incorrectly stated it was NOT universal\n");
                else
                    failed.add("DFA " + (i + 1) + ": incorrectly stated it was universal\n");
            } else
                testsPassed++;
        }
        return testsPassed;
    }

    public static int infiniteChecker(DFA[] dfaList, boolean[] desiredTestResults) {
        int testsPassed = 0;

        for (int i = 0; i < dfaList.length; i++) {
            if (dfaList[i].isInfinite() != desiredTestResults[i]) {
                if (desiredTestResults[i])
                    failed.add("DFA " + (i + 1) + ": incorrectly stated it was NOT infinite\n");
                else
                    failed.add("DFA " + (i + 1) + ": incorrectly stated it was infinite\n");
            } else
                testsPassed++;
        }
        return testsPassed;
    }

    public static int equalsChecker(DFA[][] dfaList, boolean[] desiredTestResults) {
        int testsPassed = 0;

        for (int i = 0; i < dfaList.length; i++) {
            if (dfaList[i][0].equals(dfaList[i][1]) != desiredTestResults[i]) {
                if (desiredTestResults[i])
                    failed.add("The DFAs in pair [" + (i + 1) + "] incorrectly stated they were NOT equal\n");
                else
                    failed.add("The DFAs in pair [" + (i + 1) + "] incorrectly stated they were equal\n");
            } else
                testsPassed++;
        }
        return testsPassed;
    }

    public static int subsetChecker(DFA[][] dfaList, boolean[] desiredTestResults) {
        int testsPassed = 0;

        for (int i = 0; i < dfaList.length; i++) {
            if (dfaList[i][0].isSubsetOf(dfaList[i][1]) != desiredTestResults[i]) {
                if (desiredTestResults[i])
                    failed.add("The first DFA in pair [" + (i + 1) + "] incorrectly stated it was NOT a subset\n");
                else
                    failed.add("The first DFA in pair [" + (i + 1) + "] incorrectly stated it was a subset\n");
            } else
                testsPassed++;
        }
        return testsPassed;
    }

    public static int identicalChecker(DFA[] dfaList){
        int testPassed = 0;

        for(int i = 0; i < dfaList.length; i++){
            for(int j = i; j < dfaList.length; j++){
                if(!dfaList[i].identical(DFA.decompress(DFA.compress(dfaList[j])))){
                    if(i == j){
                        failed.add(String.format("The decoding of DFA %d is identical to DFA %d but incorrectly states they are not identical\n", (j + 1) , (i + 1)));
                    }
                    else{
                        testPassed++;
                    }
                }
                else {
                    if(i == j){
                        testPassed++;
                    }
                    else{
                        failed.add(String.format("The decoding of DFA %d is not identical to DFA %d but incorrectly states they are identical\n", (j + 1) , (i + 1)));
                    }
                }
            }
        }

        return testPassed;
    }
}
