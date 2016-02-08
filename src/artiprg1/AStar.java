package artiprg1;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStar implements Search {
	HashMap<State, AStarNode> marked;
	private Stack<String> successMoves;
	private Comparator<AStarNode> comparator;
	private AStarNode root;
	private PriorityQueue<AStarNode> frontier;
	
	public AStar(State startingState){
		comparator = new fComparator();
        frontier = new PriorityQueue<AStarNode>(6, comparator);
		marked = new HashMap<>();
		root = new AStarNode(startingState, null, null);
		successMoves = new Stack<String>();
		successMoves.push("TURN_OFF");
		as();
	}
	
	private void as(){
		frontier.add(root);
		while(!frontier.isEmpty()){
			
			AStarNode topNode = frontier.poll();
			State currentState = topNode.state;
			if(currentState.isGoal()){
				for(SearchNode i = topNode; i.getParent() != null; i = i.getParent()){
					System.out.println("Adding action to solution list");
					successMoves.push(i.getAction());
				}
				return;
			}
			if(!marked.containsKey(currentState)) {
				marked.put(currentState, topNode);
				for(String action : topNode.state.legalActions()) {
						AStarNode newNode = new AStarNode(topNode.state.expandState(action), action, topNode);
						frontier.add(newNode);
				}
			}
		}
	}

	@Override
	public String nextMove() {
		return successMoves.pop();
	}
	
	public class fComparator implements Comparator<AStarNode>
	{
	    @Override
	    public int compare(AStarNode x, AStarNode y)
	    {
	        if (x.getF() < y.getF())
	            return -1;
	        if (x.getF() > y.getF())
	            return 1;
	        return 0;
	    }
	}
}
