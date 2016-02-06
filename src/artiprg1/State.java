package artiprg1;
import java.util.ArrayList;
import java.util.Collection;

public class State {
	public ArrayList<Coordinate> dirt;
	public ArrayList<Coordinate> obstacles;
	private Coordinate currentPos;
	private Coordinate home;
	private Coordinate size;
	private boolean isOn;
	public enum Orientation{NORTH, EAST, SOUTH, WEST}
	private Orientation ori;
	/**
	 * Lab class instructor hints that we can minimize the state space by storing 
	 * grid size, obstacles list, and home in the agent class and passing it as 
	 * a parameter to the search. That we the state class is only tracking things
	 * that change.
	 */
	
	public State() {
		dirt = new ArrayList<Coordinate>();
		obstacles = new ArrayList<Coordinate>();
		currentPos = null;
		home = null;
		size = null;
		isOn = false;
		ori = null;
	}
	
	//Print function to see if we're constructing these right
	public void printState() {
		System.out.println("dirt list:");
		for(Coordinate d : dirt) {
			System.out.println("Dirt at: " + d.getX() + "," + d.getY());
		}
		System.out.println("obstacle list:");
		for(Coordinate o : obstacles) {
			System.out.println("Obstacle at: " + o.getX() + "," + o.getY());
		}
		System.out.println("Home: " + home.getX() + "," + home.getY());
		System.out.println("Size: " + size.getX() + "," + size.getY());
		if(isOn) System.out.println("Agent is on");
		else System.out.println("Agent is off");
		System.out.println(ori.toString());
	}
	
	public void setDirt(ArrayList<Coordinate> dlist) {
		this.dirt = dlist;
	}
	
	public void setObstacles(ArrayList<Coordinate> olist) {
		this.obstacles = olist;
	}
	
	public void setCurrentPos(Coordinate cpos) {
		this.currentPos = cpos;
	}
	
	public void setHome(Coordinate home) {
		this.home = home;
	}
	
	public void setSize(Coordinate size) {
		this.size = size;
	}
	
	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	
	public void setOrientation(Orientation o) {
		this.ori = o;
	}
	
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
