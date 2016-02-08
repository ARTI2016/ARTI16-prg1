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
	 * a parameter to the search. That way the state class is only tracking things
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
		System.out.println("Agent location: " + currentPos.getX() + "," + currentPos.getY());
		System.out.println("dirt list:");
		for(Coordinate d : dirt) {
			System.out.print("(" + d.getX() + "," + d.getY() + ")" + ", ");
		}
		System.out.println();
		System.out.println("obstacle list:");
		for(Coordinate o : obstacles) {
			System.out.print("(" + o.getX() + "," + o.getY() + ")" + ", ");
		}
		System.out.println();
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
	
	public Coordinate getHome(){
		return this.home;
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
		if(!isOn) {
			actions.add("TURN_ON");
			return actions;
		}
		if(isOn && currentPos.equals(home) && dirt.isEmpty()) {
			actions.add("TURN_OFF");
			return actions;
		}
		for(Coordinate d : dirt) {
			if(d.equals(currentPos)){
				actions.add("SUCK");
				return actions;
			}
		}
		
		if(!isObsticleInFront() && isWithinWorld(coordinateInFront()))
			actions.add("GO");
		
		actions.add("TURN_LEFT");
		actions.add("TURN_RIGHT");
		return actions;
	}
	
	private boolean isObsticleInFront(){
		switch(ori) {
		case NORTH:
			for(Coordinate o : obstacles){
				if(o.getX() == currentPos.getX() && o.getY() == currentPos.getY()+1)
					return true;
			}
			break;
		case SOUTH:
			for(Coordinate o : obstacles){
				if(o.getX() == currentPos.getX() && o.getY() == currentPos.getY()-1)
					return true;
			}
			break;
		case EAST:
			for(Coordinate o : obstacles){
				if(o.getX() == currentPos.getX()+1 && o.getY() == currentPos.getY())
					return true;
			}
			break;
		case WEST:
			for(Coordinate o : obstacles){
				if(o.getX() == currentPos.getX()-1 && o.getY() == currentPos.getY())
					return true;
			}
			break;
		}
		return false;
	}
	
	private Coordinate coordinateInFront(){
		Coordinate c = new Coordinate();
		
		if(ori == Orientation.NORTH){
			c.set(currentPos.getX(), currentPos.getY()+1);
			return c;
		}
		else if(ori == Orientation.SOUTH){
			c.set(currentPos.getX(), currentPos.getY()-1);
			return c;
		}
		else if(ori == Orientation.EAST){
			c.set(currentPos.getX()+1, currentPos.getY());
			return c;
		}
		else{
			c.set(currentPos.getX()-1, currentPos.getY());
			return c;
		}
	}
	
	private boolean isWithinWorld(Coordinate c){
		if(c.getX() <= 0 || c.getX() > size.getX())
			return false;
		
		if(c.getY() <= 0 || c.getY() > size.getY())
			return false;
		
		return true;
	}
	
	/*public Collection<String> legalActions() {
		Collection<String> actions = new ArrayList<String>();
		//legal actions to perform in this state. can be used to cull nonsense actions.
		if(!isOn) {
			actions.add("TURN_ON");
			return actions;
		}
		if(isOn && currentPos.equals(home) && dirt.isEmpty()) {
			actions.add("TURN_OFF");
			return actions;
		}
		for(Coordinate d : dirt) {
			if(d.equals(currentPos)) actions.add("SUCK");
			return actions;
		}
		//If square in front of agent is empty, GO is legal move.
		Coordinate nextSquare = new Coordinate();
		switch(ori) {
		case NORTH:
			nextSquare.set(this.currentPos.getX(), this.currentPos.getY() + 1);
			break;
		case SOUTH:
			nextSquare.set(this.currentPos.getX(), this.currentPos.getY() - 1);
			break;
		case EAST:
			nextSquare.set(this.currentPos.getX() + 1, this.currentPos.getY());
			break;
		case WEST:
			nextSquare.set(this.currentPos.getX() - 1, this.currentPos.getY());
			break;
		}
		boolean isBlocked = false;
		for(Coordinate o : obstacles) {
			if(nextSquare.equals(o)) {
				isBlocked = true;
			}
		}
		if(!isBlocked) {
			actions.add("GO");
		}
		actions.add("TURN_LEFT");
		actions.add("TURN_RIGHT");
		return actions;
	}*/
	
	private State copyState() {
		State copy = new State();
		copy.setCurrentPos(new Coordinate(this.currentPos.getX(), this.currentPos.getY()));
		copy.setSize(new Coordinate(this.size.getX(), this.size.getY()));
		copy.setHome(new Coordinate(this.home.getX(), this.home.getY()));
		if(this.isOn) {
			copy.setOn(true);
		}
		for(Coordinate o : this.obstacles) {
			copy.obstacles.add(new Coordinate(o.getX(), o.getY()));
		}
		for(Coordinate d : this.dirt) {
			copy.dirt.add(new Coordinate(d.getX(), d.getY()));
		}
		switch(this.ori) {
			case NORTH: 
				copy.setOrientation(Orientation.NORTH);
				break;
			case SOUTH: 
				copy.setOrientation(Orientation.SOUTH);
				break;
			case EAST: 
				copy.setOrientation(Orientation.EAST);
				break;
			case WEST:
				copy.setOrientation(Orientation.WEST);
				break;
		}
		return copy;
	}
	
	public State expandState(String action) {
		State nextState = copyState();
		if(action == "TURN_ON") {
			if(!isOn) {
				nextState.isOn = true;
			}
		}
		if(action == "SUCK") {
			for(Coordinate d : dirt) {
				if(this.currentPos.equals(d)) {
					nextState.dirt.remove(d);
					break;
				}
			}
		}
		if(action == "GO") {
			switch(this.ori) {
				case NORTH:
					nextState.currentPos.set(this.currentPos.getX(),
												this.currentPos.getY() + 1);
					break;
				case SOUTH:
					nextState.currentPos.set(this.currentPos.getX(),
							this.currentPos.getY() - 1);
					break;
				case EAST:
					nextState.currentPos.set(this.currentPos.getX() + 1,
							this.currentPos.getY());
					break;
				case WEST:
					nextState.currentPos.set(this.currentPos.getX() - 1,
							this.currentPos.getY());
					break;
			}
		}
		if(action == "TURN_LEFT") {
			if(ori == Orientation.NORTH) {
				nextState.setOrientation(Orientation.WEST);
				System.out.println("Agent turned left! Should now be facing WEST");
				System.out.println("Agent is now facing " + nextState.ori.toString());
			} else if (ori == Orientation.WEST) {
				nextState.setOrientation(Orientation.SOUTH);
				System.out.println("Agent turned left! Should now be facing SOUTH");
				System.out.println("Agent is now facing " + nextState.ori.toString());
			} else if (ori == Orientation.SOUTH) {
				nextState.setOrientation(Orientation.EAST);
				System.out.println("Agent turned left! Should now be facing EAST");
				System.out.println("Agent is now facing " + nextState.ori.toString());
			} else if (ori == Orientation.EAST) {
				nextState.setOrientation(Orientation.NORTH);
				System.out.println("Agent turned left! Should now be facing NORTH");
				System.out.println("Agent is now facing " + nextState.ori.toString());
			}
		}
		if(action == "TURN_RIGHT") {
			switch(this.ori) {
				case NORTH:
					nextState.setOrientation(Orientation.EAST);
					System.out.println("Agent turned right! Should now be facing EAST");
					System.out.println("Agent is now facing " + nextState.ori.toString());
					break;
				case EAST:
					nextState.setOrientation(Orientation.SOUTH);
					System.out.println("Agent turned right! Should now be facing SOUTH");
					System.out.println("Agent is now facing " + nextState.ori.toString());
					break;
				case SOUTH: 
					nextState.setOrientation(Orientation.WEST);
					System.out.println("Agent turned right! Should now be facing WEST");
					System.out.println("Agent is now facing " + nextState.ori.toString());
					break;
				case WEST:
					nextState.setOrientation(Orientation.NORTH);
					System.out.println("Agent turned right! Should now be facing NORTH");
					System.out.println("Agent is now facing " + nextState.ori.toString());
			}
		}
		return nextState;
	}
	
	@Override
    public boolean equals(Object obj)
    {
		State s = (State) obj;
        if (this.size.equals(s.size) && this.currentPos.equals(s.currentPos)
        		&& this.isOn == s.isOn && this.ori == s.ori
        		&& this.dirt.equals(s.dirt) && this.obstacles.equals(s.obstacles)
        		&& this.home.equals(s.home))
            return true;
        return false;
    }
}
