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
	
	public UniformCost(State startingState)
    {
		comparator = new CostComparator();
        frontier = new PriorityQueue<SearchNode>(6, comparator);
        marked = new HashMap<>();
        root = new SearchNode(startingState, "TURN_ON", null);
        successMoves = new Stack<String>();
        successMoves.push("TURN_OFF");
        ucs();
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
    				}
    				return;
    			}
    			if(!marked.containsKey(currentState)) {
    				marked.put(currentState, topNode);
    				for(String action : topNode.state.legalActions()) {
    					SearchNode newNode = new SearchNode(topNode.state.expandState(action), action, topNode);
    						frontier.add(newNode);
    				}
    			}
    		}
        }
        return;
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
