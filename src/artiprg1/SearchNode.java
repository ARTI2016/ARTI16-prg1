package artiprg1;

public class SearchNode {
	private State state;
	private SearchNode parent;
	private String action;
	private int cost;
	
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public SearchNode getParent() {
		return parent;
	}
	public void setParent(SearchNode parent) {
		this.parent = parent;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	
}
