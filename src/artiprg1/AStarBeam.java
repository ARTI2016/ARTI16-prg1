package artiprg1;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStarBeam implements Search {
	HashMap<State, AStarNode> marked; //CLOSED LIST
	private Stack<String> successMoves;
	private Comparator<AStarNode> comparator;
	private AStarNode root;
	private PriorityQueue<AStarNode> SET; //BEAM
	private int beamSize = 100;
	private Stack<AStarNode> beam;
	
	//Performance measurement variables.
	int frontierMax = 1;
	Stopwatch timer;
	int expansions = 0;
	int totalcost = 0;
	double elapsedtime = 0;
	
	public AStarBeam(State startingState){
		comparator = new fComparator();
		beam = new Stack<AStarNode>();
		marked = new HashMap<>();
		root = new AStarNode(startingState, null, null, 0);
		successMoves = new Stack<String>();
		successMoves.push("TURN_OFF");
		
		timer = new Stopwatch();
        as();
        elapsedtime = timer.elapsedTime();
		
		printStats();
	}
	
	private void as(){
		beam.add(root);
		while(!beam.isEmpty()){ //while beam is not empty
			SET = new PriorityQueue<AStarNode>(6, comparator); //initialize the empty set
			AStarNode currentNode;
			State currentState;
			
			if(beam.size() > frontierMax) {
				frontierMax = beam.size();
			}
			
			for(AStarNode s : beam) { //for each state in beam
				currentNode = s;
				currentState = s.state;
				
				if(currentState.isGoal()){ //if (successor == goal) return
					for(SearchNode i = currentNode; i.getParent() != null; i = i.getParent()){
						successMoves.push(i.getAction());
						totalcost += actionCost(i.getParent(), i.getAction());
					}
					return;
				}
				
				SET.add(s); //add that state to the SET
				for(String action : s.state.legalActions()) { //and its successors
					AStarNode newNode = new AStarNode(currentNode.state.expandState(action), action, currentNode, actionCost(currentNode, action));
					expansions++;
					SET.add(newNode); //add those successors to the SET
				}
			}
			
			beam.clear();
			
			while(!SET.isEmpty() && beam.size() < beamSize) {
				currentNode = SET.poll();
				currentState = currentNode.state;
				if(!marked.containsKey(currentState)) {
					marked.put(currentState, currentNode);
					beam.add(currentNode);
				}
			}
		}
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
