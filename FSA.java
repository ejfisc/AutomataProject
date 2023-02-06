import java.util.List;
import java.util.Map;

interface FSA {
  public void setStates(String[] states);
  public void setAlphabet(String[] alphabet);
  public void setTransitionFunction(Map<String, Map<String, List<String>>> matrix);
  public void setInitialState(String state);
  public void setFinalStates(String[] states);
  public boolean checkString(String input);
}