package Map;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MapClass {
	
	public enum MapItems{
		WALL, FLOOR, GUARD, ROBBER, TREASURE
	}
	
	private ArrayList<Vector2> guardPositions = new ArrayList<Vector2>(); //Guard Positions
	private int numGuard = 0;
	private ArrayList<Vector2> robberPositions = new ArrayList<Vector2>(); //robber Positions
	private int numRobber = 0;
	
	private ArrayList<Vector2> treasurePositions = new ArrayList<Vector2>();
	private int numTreasure = 0;
	
	//static Pattern pattern = Pattern.compile("[0-9][0-9]?[0-9]?");
	//static Pattern spacePattern = Pattern.compile("\n");
	//static Pattern endSubStringPattern = Pattern.compile("[a-z]+");
	
	private MapItems[][] map;
	
	int gridsize = 32; //width of map gridsquares 
	
	
	MapClass()
	{
		String[] lines = null;
		try
		{
			//URI uri = this.getClass().getClassLoader().getResource("assets/MapTest.txt").toURI();
			//System.out.println(uri.toString());
			//lines = Files.readAllLines(Paths.get(uri));
			FileHandle file = Gdx.files.internal("MapTest.txt");
			String text = file.readString();
			lines = text.split("\n");
			//this.lines = lines;
			//System.out.println(lines);
			map = new MapItems[lines.length][lines[0].length()];
			for(int i=0; i<lines.length; i++){
				char[] chars = lines[i].toCharArray();
				
				for(int j=0; j<chars.length; j++){
					switch(chars[j]){
						case 'w': map[i][j] = MapItems.WALL;
						break;
						case '-': map[i][j] = MapItems.FLOOR;
						break;
						case 'g': map[i][j] = MapItems.GUARD;
						break;
						case 'r': map[i][j] = MapItems.ROBBER;
						break;
						case 't': map[i][j] = MapItems.TREASURE;
						break;
						default: map[i][j] = MapItems.FLOOR;
					}
				}
			}
		
		}
		catch (Exception e)
		{
			System.out.println("The file doesn't exist");
		}


	}
	
	public void setGridSquareSize(int gridsize){
		this.gridsize = gridsize;
	}
	
	public boolean checkCollision(Rectangle rect){
		for(int i=0; i<4; i++){
			if(getGridSquare((int)(rect.x + rect.width * (i/2)), (int)(rect.x + rect.width * (i%2)))
					== MapItems.WALL){
				return true;
			}
		}
		return false;
	}
	
	private MapItems getGridSquare(int x, int y){
		int xcoord = x/gridsize;
		int ycoord = y/gridsize;
		return map[ycoord][xcoord];
	}
}
