package artiprg1;

public class SearchNode{
	private State state;
	private SearchNode parent;
	private String action;
	private int cost;
	
	public SearchNode(State state, String action, SearchNode parent){
		this.state = new State(state);
		if(action == null) {
			this.action = "";
		} else this.action = action;
        this.parent = parent;
	}
	
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
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof SearchNode) {
			SearchNode s = (SearchNode) o;
			if(s.getState().equals(this.getState()) && s.getAction().equals(this.getAction())
					&& s.getCost() == this.getCost() && s.getParent() == this.getParent()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = state.hashCode() * 3;
		if(action != null) {
			hash *= action.hashCode();
		}
		hash *= cost;
		if(parent != null) hash *= parent.hashCode();
		return hash;
	}
}
