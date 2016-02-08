package artiprg1;

class AStarNode extends SearchNode{
	private int f;
	private int g;

	public AStarNode(State state, String action, AStarNode parentNode) {
		super(state, action, (AStarNode)parentNode);
		if( parentNode == null){
			g = 0;
		}else{
			g = parentNode.g + getCost(action);
		}
		f = g + heuristic();
	}
	
	@Override
	public int compareTo(Node node) {
		if(!(node instanceof AStarNode))
			return super.compareTo(node);
		AStarNode aNode = (AStarNode) node;
		if(this.f != aNode.f)
			return this.f < aNode.f ? -1 : 1;
		return 0;
	}
	
	/**
	 * returns the cost from the given position to the dirt farthest away + the cost from the farthest dirt to the home position
	 * + the cost of sucking all the dirt
	 */
	private int heuristic() {
		int h = state.dirt.size() * state.getActionCost("SUCK");
		h += !state.turned_on && !state.dirt.isEmpty() ? state.getActionCost("TURN_ON") : 0;
		h += state.turned_on ? state.getActionCost("TURN_OFF") : 0;
		Position[] dirts = this.state.getStateDirt();
		Position currentPos = this.state.position;
		if(dirts.length != 0) {
			Position dirt = dirts[0];
			int distance = this.state.travelCost(this.state.position, dirt);
			for(int i = 1; i < dirts.length; i++) {
				int newDistance = this.state.travelCost(this.state.position, dirts[i]);
				if(newDistance > distance) {
					distance = newDistance;
					dirt = dirts[i];
				}
			}
			currentPos = dirt;
			h += distance;
		}
		return h + this.state.travelCost(currentPos, this.state.world.homePosition);
	}
}
