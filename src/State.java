import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class State {
	private List<Coordinate> dirt;
	private List<Coordinate> obstacles;
	private Coordinate currentPos;
	private Coordinate home;
	private Coordinate size;
	private boolean isOn;
	public enum Orientation{NORTH, EAST, SOUTH, WEST}
	
	
	public boolean isGoal() {
		return (currentPos.equals(home) && dirt.isEmpty() && !isOn);
	}
	
	public Collection<String> legalActions() {
		Collection<String> actions = new ArrayList<String>();
		//legal actions to perform in this state. can be used to cull nonsense actions.
		if(!isOn) {
			actions.add("TURN_ON");
		}

		return actions;
	}
	
	public State expandState() {
		return null;
	}
}
