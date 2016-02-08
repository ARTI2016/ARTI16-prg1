package artiprg1;
import java.util.ArrayList;
import java.util.Collection;

public class State {
	public ArrayList<Coordinate> dirt;
	public ArrayList<Coordinate> obstacles;
	private Coordinate currentPos;
	private Coordinate home;
	private Coordinate size;
	public boolean isOn;
	public enum Orientation{NORTH, EAST, SOUTH, WEST}
	private Orientation ori;
	/**
	 * Lab class instructor hints that we can minimize the state space by storing 
	 * grid size, obstacles list, and home in the agent class and passing it as 
	 * a parameter to the search. That way the state class is only tracking things
	 * that change.
	 */
	
	public State() {
		this.dirt = new ArrayList<Coordinate>();
		this.obstacles = new ArrayList<Coordinate>();
		this.currentPos = new Coordinate();
		this.home = new Coordinate();
		this.size = new Coordinate();
		this.isOn = false;
		this.ori = null;
	}
	
	public State(State s) {
		this.dirt = new ArrayList<Coordinate>();
		for(Coordinate d : s.dirt) {
			this.dirt.add(new Coordinate(d.getX(), d.getY()));
		}
		this.obstacles = new ArrayList<Coordinate>();
		for(Coordinate o : s.obstacles) {
			this.obstacles.add(new Coordinate(o.getX(), o.getY()));
		}
		
		this.currentPos = new Coordinate();
		this.home = new Coordinate();
		this.size = new Coordinate();
		
		this.currentPos.set(s.currentPos.getX(), s.currentPos.getY());
		this.home.set(s.home.getX(), s.home.getY());
		this.size.set(s.size.getX(), s.size.getY());
		this.isOn = s.isOn;
		this.ori = s.ori;
	}
	
	public Coordinate getPos() {
		return currentPos;
	}
	
	//Print function to see if we're constructing these right
	public void printState() {
		System.out.println("Agent location: " + currentPos.getX() + "," + currentPos.getY());
		System.out.println("dirt list:");
		for(Coordinate d : dirt) {
			System.out.print("(" + d.getX() + "," + d.getY() + ")" + ", ");
		}
		System.out.println();
		/*System.out.println("obstacle list:");
		for(Coordinate o : obstacles) {
			System.out.print("(" + o.getX() + "," + o.getY() + ")" + ", ");
		}
		System.out.println();
		System.out.println("Home: " + home.getX() + "," + home.getY());
		System.out.println("Size: " + size.getX() + "," + size.getY());
		if(isOn) System.out.println("Agent is on");
		else System.out.println("Agent is off");
		System.out.println(ori.toString());*/
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
		return (currentPos.equals(home) && dirt.size() == 0);
	}
	
	public Collection<String> legalActions() {
		Collection<String> actions = new ArrayList<String>();
		if(!this.isOn) {
			actions.add("TURN_ON");
			return actions;
		}
		if(this.isOn && this.currentPos.equals(home) && this.dirt.size() == 0) {
			System.out.println("PLEAS TURN OFF!");
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
		
		if(isWithinWorld(coordinateOnLeft()))
			actions.add("TURN_LEFT");
		
		if(isWithinWorld(coordinateOnRight()))
			actions.add("TURN_RIGHT");
		
		/*if(isObsticleOnLeft() && isObsticleOnRight() && isObsticleInFront()){
			actions.add("TURN_LEFT");
		}*/
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
	
	private boolean isObsticleOnRight(){
		switch(ori) {
		case NORTH:
			for(Coordinate o : obstacles){
				if(o.getX() == currentPos.getX()+1 && o.getY() == currentPos.getY())
					return true;
			}
			break;
		case SOUTH:
			for(Coordinate o : obstacles){
				if(o.getX() == currentPos.getX()-1 && o.getY() == currentPos.getY())
					return true;
			}
			break;
		case EAST:
			for(Coordinate o : obstacles){
				if(o.getX() == currentPos.getX() && o.getY() == currentPos.getY()-1)
					return true;
			}
			break;
		case WEST:
			for(Coordinate o : obstacles){
				if(o.getX() == currentPos.getX() && o.getY() == currentPos.getY()+1)
					return true;
			}
			break;
		}
		return false;
	}
	
	private Coordinate coordinateOnRight(){
		Coordinate c = new Coordinate();
		
		if(ori == Orientation.NORTH){
			c.set(currentPos.getX()+1, currentPos.getY());
			return c;
		}
		else if(ori == Orientation.SOUTH){
			c.set(currentPos.getX()-1, currentPos.getY());
			return c;
		}
		else if(ori == Orientation.EAST){
			c.set(currentPos.getX(), currentPos.getY()-1);
			return c;
		}
		else{
			c.set(currentPos.getX(), currentPos.getY()+1);
			return c;
		}
	}
	private boolean isObsticleOnLeft(){
		switch(ori) {
		case NORTH:
			for(Coordinate o : obstacles){
				if(o.getX() == currentPos.getX()-1 && o.getY() == currentPos.getY())
					return true;
			}
			break;
		case SOUTH:
			for(Coordinate o : obstacles){
				if(o.getX() == currentPos.getX()+1 && o.getY() == currentPos.getY())
					return true;
			}
			break;
		case EAST:
			for(Coordinate o : obstacles){
				if(o.getX() == currentPos.getX() && o.getY() == currentPos.getY()+1)
					return true;
			}
			break;
		case WEST:
			for(Coordinate o : obstacles){
				if(o.getX() == currentPos.getX() && o.getY() == currentPos.getY()-1)
					return true;
			}
			break;
		}
		return false;
	}
	
	private Coordinate coordinateOnLeft(){
		Coordinate c = new Coordinate();
		
		if(ori == Orientation.NORTH){
			c.set(currentPos.getX()-1, currentPos.getY());
			return c;
		}
		else if(ori == Orientation.SOUTH){
			c.set(currentPos.getX()+1, currentPos.getY());
			return c;
		}
		else if(ori == Orientation.EAST){
			c.set(currentPos.getX(), currentPos.getY()+1);
			return c;
		}
		else{
			c.set(currentPos.getX(), currentPos.getY()-1);
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
	
	
	public State expandState(String action) {
		State nextState = new State(this);
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
			} else if (ori == Orientation.WEST) {
				nextState.setOrientation(Orientation.SOUTH);
			} else if (ori == Orientation.SOUTH) {
				nextState.setOrientation(Orientation.EAST);
			} else if (ori == Orientation.EAST) {
				nextState.setOrientation(Orientation.NORTH);
			}
		}
		if(action == "TURN_RIGHT") {
			switch(this.ori) {
				case NORTH:
					nextState.setOrientation(Orientation.EAST);
					break;
				case EAST:
					nextState.setOrientation(Orientation.SOUTH);
					break;
				case SOUTH: 
					nextState.setOrientation(Orientation.WEST);
					break;
				case WEST:
					nextState.setOrientation(Orientation.NORTH);
					break;
			}
		}
		return nextState;
	}
	
	@Override
    public boolean equals(Object obj)
    {
		State s = (State) obj;
        if ((this.currentPos.equals(s.currentPos)
        		&& this.isOn == s.isOn && this.ori == s.ori
        		&& this.dirt.equals(s.dirt)))
            return true;
        return false;
    }
}
