package Map;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapClass {
	Scanner inFile;
	StringBuffer toRead;
	
	String reading; //the current string being read
	String guard; //guard char
	ArrayList<Node> guardPositions = new ArrayList<Node>(); //Guard Positions
	int numGuard = 0;
	String robber; //robber char
	ArrayList<Node> robberPositions = new ArrayList<Node>(); //robber Positions
	int numRobber = 0;
	
	String wall; //wall char
	String floor; //floor char
	
	ArrayList<Node> treasurePositions = new ArrayList<Node>();
	int numTreasure = 0;
	String treasure; //treasure char
	
	static Pattern pattern = Pattern.compile("[0-9][0-9]?[0-9]?");
	static Pattern spacePattern = Pattern.compile("\n");
	static Pattern endSubStringPattern = Pattern.compile("[a-z]+");
	
	String[] lines;
	
	
	MapClass(String[] lines)
	{
		try
		{
			//URI uri = this.getClass().getClassLoader().getResource("assets/MapTest.txt").toURI();
			//System.out.println(uri.toString());
			//lines = Files.readAllLines(Paths.get(uri));
			//FileHandle file = Gdx.files.internal("MapTest.txt");
			//String text = file.readString();
			//lines = text.split("\n");
			this.lines = lines;
			//System.out.println(lines);
		}
		catch (Exception e)
		{
			System.out.println("The file doesn't exist");
		}
		/*
		 * The file must have multiple lines. 
		 * The first line will be guard positions.
		 * The second line should be robber positions.
		 * The third line should be treasure positions.
		 * The fourth should be the wall character
		 * The fifth should be floor character
		 * The sixth should be the Robber character
		 * The seventh should be the guard character
		 * The eighth should be the treasure character
		 */
		int lineNumber = 1; //Note: The line does not start at 0 to keep consistent with the definitions above.
		while(lineNumber <= lines.length)
		{
			reading = lines[lineNumber-1];
			if (lineNumber == 1) //if this is position of guards
			{
				String gaurdPositionString = reading.substring(reading.indexOf("guard")); //assumes guard will occupy first line of the file
				Matcher m = pattern.matcher(gaurdPositionString);
				int ctr = 0;
				int x = 0;
				int y = 0;
				while (m.find())
				{
					if (ctr %2 == 0)
						{
						x = Integer.parseInt(m.group());
						ctr++;
						}
					else
					{
						y = Integer.parseInt(m.group());
						ctr++;
						push(x,y,guardPositions);
						numGuard++;
					}		
				}
			}
			if (lineNumber == 2) //if this is position of robbers
			{
				String robberPositionString = reading.substring(reading.indexOf("robber"));
				Matcher m = pattern.matcher(robberPositionString);
				int ctr = 0;
				int x = 0;
				int y = 0;
				while (m.find())
				{
					if (ctr %2 == 0)
						{
						x = Integer.parseInt(m.group());
						ctr++;
						}
					else
					{
						y = Integer.parseInt(m.group());
						ctr++;
						push(x,y,robberPositions);
						numRobber++;
					}		
				}
			}
			if(lineNumber == 4) //wallCharacter
			{
				String wallCharacter = reading.substring(reading.indexOf("wallChar"));
				wall = wallCharacter;
			}
			if(lineNumber == 5) //floorCharacter
			{
				String floorCharacter = reading.substring(reading.indexOf("floorChar"));
				floor = floorCharacter;
				
			}
			if(lineNumber == 3) //this line contains treasure positions
			{
				String treasurePositionString = reading.substring(reading.indexOf("treasure"));
				Matcher m = pattern.matcher(treasurePositionString);
				int ctr = 0;
				int x = 0;
				int y = 0;
				while (m.find())
				{
					if (ctr %2 == 0)
						{
						x = Integer.parseInt(m.group());
						ctr++;
						}
					else
					{
						y = Integer.parseInt(m.group());
						ctr++;
						push(x,y,treasurePositions);
						numTreasure++;
					}		
				}
			}
			if (lineNumber == 6) //6 is robberChar
			{
				robber = reading.substring(reading.indexOf("robberChar:"));
			}
			if (lineNumber == 7) //7 is guardChar
			{
				guard = reading.substring(reading.indexOf("guardChar:"));
			}
			if (lineNumber == 8)//8 is treasureChar
			{
				treasure = reading.substring(reading.indexOf("treasureChar:"));
			}
		}
	}
	
	//This method will push new position nodes onto the position list for their respective objects
	private void push(int x, int y, ArrayList<Node> list)
	{
		Node newNode = new Node(x,y);
		list.add(newNode);
		
	}
	//sent a rectangle, check to see if it collides with any walls method .. Rectangle class
	
	//public static void main(String [] args)
	//{
		//new MapClass();
	//}
}
