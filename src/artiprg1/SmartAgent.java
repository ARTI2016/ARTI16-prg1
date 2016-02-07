package artiprg1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import artiprg1.State.Orientation;

public class SmartAgent implements Agent {
	
	private State iState = new State();
	private Random random = new Random();
	/* Frontier ordering:
		DFS- New nodes are inserted at back of frontier. Queue.
		BFS- New nodes are inserted at front of frontier. Stack.
		Uniform- Nodes are sorted in increasing cost. Priority Queue.
	 */
	
	@Override
	public void init(Collection<String> percepts) {
		/*
			Possible percepts are:
			- "(SIZE x y)" denoting the size of the environment, where x,y are integers
			- "(HOME x y)" with x,y >= 1 denoting the initial position of the robot
			- "(ORIENTATION o)" with o in {"NORTH", "SOUTH", "EAST", "WEST"} denoting the initial orientation of the robot
			- "(AT o x y)" with o being "DIRT" or "OBSTACLE" denoting the position of a dirt or an obstacle
			Moving north increases the y coordinate and moving east increases the x coordinate of the robots position.
			The robot is turned off initially, so don't forget to turn it on.
		*/
		for (String p : percepts)
		{
			if (p.startsWith("(HOME"))
			{
				int index = 6, x_start = 0, y_start = 0;
				while(Character.isDigit(p.charAt(index))) x_start = x_start * 10 + Character.getNumericValue(p.charAt(index++)); 
				index++;
				while(Character.isDigit(p.charAt(index))) y_start = y_start * 10 + Character.getNumericValue(p.charAt(index++));
				iState.setHome(new Coordinate(x_start, y_start));
			}
			else if (p.startsWith("(ORIENTATION"))
			{
				if (p.charAt(13) == 'N') iState.setOrientation(Orientation.NORTH);
				else if (p.charAt(13) == 'W') iState.setOrientation(Orientation.WEST);
				else if (p.charAt(13) == 'S') iState.setOrientation(Orientation.SOUTH);
				else iState.setOrientation(Orientation.EAST);
			}
			else if (p.startsWith("(AT DIRT"))
			{
				int index = 9, pos_x = 0, pos_y = 0;
				while(Character.isDigit(p.charAt(index))) pos_x = pos_x * 10 + Character.getNumericValue(p.charAt(index++));
				index++;
				while(Character.isDigit(p.charAt(index))) pos_y = pos_y * 10 + Character.getNumericValue(p.charAt(index++));
				Coordinate pos = new Coordinate(pos_x, pos_y);
				iState.dirt.add(pos);
			}
			else if (p.startsWith("(AT OBSTACLE"))
			{
				int index = 13, pos_x = 0, pos_y = 0;
				while(Character.isDigit(p.charAt(index))) pos_x = pos_x * 10 + Character.getNumericValue(p.charAt(index++));
				index++;
				while(Character.isDigit(p.charAt(index))) pos_y = pos_y * 10 + Character.getNumericValue(p.charAt(index++));
				Coordinate pos = new Coordinate(pos_x, pos_y);
				iState.obstacles.add(pos);
			}
			else if (p.startsWith("(SIZE"))
			{
				int index = 6, x_size = 0, y_size = 0;
				while(Character.isDigit(p.charAt(index))) x_size = x_size * 10 + Character.getNumericValue(p.charAt(index++));
				index++;
				while(Character.isDigit(p.charAt(index))) y_size = y_size * 10 + Character.getNumericValue(p.charAt(index++));
				iState.setSize(new Coordinate(x_size, y_size));
			}
		}
		iState.printState();
    }

	@Override
	public String nextAction(Collection<String> percepts) {
		System.out.print("perceiving:");
		for(String percept:percepts) {
			System.out.print("'" + percept + "', ");
		}
		System.out.println("");
		String[] actions = { "TURN_ON", "TURN_OFF", "TURN_RIGHT", "TURN_LEFT", "GO", "SUCK" };
		return actions[random.nextInt(actions.length)];
	}

}
