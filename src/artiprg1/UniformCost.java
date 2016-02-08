package artiprg1;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class UniformCost implements Search{
	
	private HashMap<State, SearchNode> marked;
	private PriorityQueue<SearchNode> frontier;
	private Comparator<SearchNode> comparator;
	private Stack<String> successMoves;
	private SearchNode root;
	
	//Performance measurement variables.
	int frontierMax = 1;
	Stopwatch timer;
	int expansions = 0;
	int totalcost = 0;
	double elapsedtime = 0;
	
	public UniformCost(State startingState)
    {
		comparator = new CostComparator();
        frontier = new PriorityQueue<SearchNode>(6, comparator);
        marked = new HashMap<>();
        root = new SearchNode(startingState, "TURN_ON", null, 0);
        successMoves = new Stack<String>();
        successMoves.push("TURN_OFF");
        
        timer = new Stopwatch();
        ucs();
        elapsedtime = timer.elapsedTime();
        
        printStats();
    }
	
	private void ucs(){
		frontier.add(root);    
        
        while(!frontier.isEmpty())
        {
        	frontier.add(root);
    		while(!frontier.isEmpty()){
    			
    			SearchNode topNode = frontier.poll();
    			State currentState = topNode.state;
    			if(currentState.isGoal()){
    				for(SearchNode i = topNode; i.getParent() != null; i = i.getParent()){
    					successMoves.push(i.getAction());
    					totalcost += actionCost(i.getParent(), i.getAction());
    					if(successMoves.size() > frontierMax) {
    						frontierMax = successMoves.size();
    					}
    				}
    				return;
    			}
    			if(!marked.containsKey(currentState)) {
    				marked.put(currentState, topNode);
    				for(String action : topNode.state.legalActions()) {
    					SearchNode newNode = new SearchNode(topNode.state.expandState(action), action, topNode, actionCost(topNode, action));
    					expansions++;
    					frontier.add(newNode);
    				}
    			}
    		}
        }
        return;
	}
	
	private void printStats() {
    	System.out.println("Maximum frontier size: " + frontierMax);
    	System.out.println("Expansion count: " + expansions);
    	System.out.println("Elapsed time: " + elapsedtime);
    	System.out.println("Solution cost: " + totalcost);
    }
	
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
	
	public class CostComparator implements Comparator<SearchNode>
	{
	    @Override
	    public int compare(SearchNode x, SearchNode y)
	    {
	        if (x.getCost() < y.getCost())
	        {
	            return -1;
	        }
	        if (x.getCost() > y.getCost())
	        {
	            return 1;
	        }
	        return 0;
	    }
	}
}
