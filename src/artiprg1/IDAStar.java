package artiprg1;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class IDAStar implements Search {
	AStarNode currentNode;
	/*
	g; //cost to reach current node
	f; //estimated cost of cheapest root>node>goal path
	h(node); //estimated cost of cheapest node>goal path
	cost(node, succ); //step cost function
	is_goal(node); //goal test
	successors(node); //node expanding function
	
	All of these are handled by the AStarNode class
	*/
	private Stack<String> successMoves;
	private Comparator<AStarNode> comparator;
	private AStarNode root;
	private PriorityQueue<AStarNode> frontier;
	private int bound;
	
	//Performance measurement variables.
	int frontierMax = 1;
	Stopwatch timer;
	int expansions = 0;
	int totalcost = 0;
	double elapsedtime = 0;
	
	public IDAStar(State startingState){
		comparator = new fComparator();
        frontier = new PriorityQueue<AStarNode>(6, comparator);
		root = new AStarNode(startingState, null, null, 0);
		successMoves = new Stack<String>();
		successMoves.push("TURN_OFF");
		bound = root.heuristic();
		
		timer = new Stopwatch();
        int t = search(root, bound);
        bound = t;
        elapsedtime = timer.elapsedTime();
		
		printStats();
	}
	
	private int search(AStarNode node, int bound){
		if(node.getF() > bound) {
			return node.getF();
		}
		if(node.state.isGoal()) {
			for(SearchNode i = currentNode; i.getParent() != null; i = i.getParent()){
				successMoves.push(i.getAction());
				totalcost += actionCost(i.getParent(), i.getAction());
			}
				return 1;
		}
		return 0;
	}
	
	private void printStats() {
    	System.out.println("Maximum frontier size: " + frontierMax);
    	System.out.println("Expansion count: " + expansions);
    	System.out.println("Elapsed time: " + elapsedtime);
    	System.out.println("Solution cost: " + totalcost);
    }
	
    /**
     * Calculates the cost of an action being applied to a state.
     * 
     * Return 1 by default. If this isn't returning 1, something has gone horribly wrong.
     * 
     * @param n, the state the action is being applied to
     * @param action, the action being applied to the state 
     * @return the cost of the action being applied
     */
	private int actionCost(SearchNode n, String action) {
    	if(action == "TURN_OFF" && n.getState().getPos().equals(n.getState().world.getHome())
    			&& (!n.getState().dirt.isEmpty())) {
    		return 1 + 50* n.getState().dirt.size();
    	}
    	if(action == "TURN_OFF" && !(n.getState().getPos().equals(n.getState().world.getHome()))
    			&& (!n.getState().dirt.isEmpty())) {
    		return 100 + 50* n.getState().dirt.size();
    	}
    	/*
    	 * SUCK is never a legal action in a clean square. No need to check for it.
    	 * Above checks are only in case the thing somehow runs out of moves and turns off.
    	 */
    	return 1;
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
