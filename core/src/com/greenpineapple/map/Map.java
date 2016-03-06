package com.greenpineapple.map;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.greenpineapple.GreenPineappleGame;

public class Map {

	private int gridsize = 32; // width of map gridsquares

	public enum MapItems {
		WALL, FLOOR, GUARD, ROBBER, TREASURE
	}

	public class GridSquare {
		MapItems type;
		Rectangle rectangle;

		GridSquare(MapItems type, int gridx, int gridy) {
			this.type = type;
			rectangle = new Rectangle();
			rectangle.width = gridsize;
			rectangle.height = gridsize;
			rectangle.x = gridx * gridsize;
			rectangle.y = GreenPineappleGame.SCREEN_HEIGHT - gridy * gridsize - gridsize;

		}
	}

	private ArrayList<Vector2> guardPositions = new ArrayList<Vector2>(); // Guard
																			// Positions

	// private int numGuard = 0;
	private ArrayList<Vector2> robberPositions = new ArrayList<Vector2>(); // robber
																			// Positions
	// private int numRobber = 0;

	private ArrayList<Vector2> treasurePositions = new ArrayList<Vector2>();
	// private int numTreasure = 0;

	// static Pattern pattern = Pattern.compile("[0-9][0-9]?[0-9]?");
	// static Pattern spacePattern = Pattern.compile("\n");
	// static Pattern endSubStringPattern = Pattern.compile("[a-z]+");

	private GridSquare[][] map;

	private Texture mapimage;
	private Pixmap wallimage, floorimage;

	public Map(String mapFileName) {
		String[] lines = null;
		wallimage = new Pixmap(Gdx.files.internal("wall.png"));
		// wallimage.setColor(.5f,.6f,.7f,.8f);
		// wallimage.fill();
		floorimage = new Pixmap(Gdx.files.internal("floor.png"));
		// floorimage.setColor(Color.LIGHT_GRAY);
		// floorimage.fill();
		try {
			// URI uri =
			// this.getClass().getClassLoader().getResource("assets/MapTest.txt").toURI();
			// System.out.println(uri.toString());
			// lines = Files.readAllLines(Paths.get(uri));
			FileHandle file = Gdx.files.internal(mapFileName);
			String text = file.readString();
			lines = text.split("\n");
			// this.lines = lines;
			// System.out.println(lines);
			map = new GridSquare[lines.length][lines[0].length()];
			int mapwidth = gridsize * map[0].length;
			int mapheight = gridsize * map.length;
			mapimage = new Texture(mapwidth, mapheight, Format.RGB888);
			for (int i = 0; i < lines.length; i++) {
				char[] chars = lines[i].toCharArray();

				for (int j = 0; j < chars.length; j++) {
					switch (chars[j]) {
					case 'w':
						map[i][j] = new GridSquare(MapItems.WALL, j, i);
						drawToTexture(i, j, MapItems.WALL);
						break;
					case '-':
						map[i][j] = new GridSquare(MapItems.FLOOR, j, i);
						drawToTexture(i, j, MapItems.FLOOR);
						break;
					case 'g':
						map[i][j] = new GridSquare(MapItems.FLOOR, j, i);
						drawToTexture(i, j, MapItems.FLOOR);
						guardPositions.add(new Vector2(map[i][j].rectangle.x, map[i][j].rectangle.y));
						break;
					case 'r':
						map[i][j] = new GridSquare(MapItems.FLOOR, j, i);
						drawToTexture(i, j, MapItems.FLOOR);
						robberPositions.add(new Vector2(map[i][j].rectangle.x, map[i][j].rectangle.y));
						break;
					case 't':
						map[i][j] = new GridSquare(MapItems.FLOOR, j, i);
						drawToTexture(i, j, MapItems.FLOOR);
						treasurePositions.add(new Vector2(map[i][j].rectangle.x, map[i][j].rectangle.y));
						break;
					default:
						map[i][j] = new GridSquare(MapItems.FLOOR, j, i);
						drawToTexture(i, j, MapItems.FLOOR);
					}
				}
			}

		} catch (Exception e) {
			System.out.println("The file doesn't exist");
		}

	}

	private void drawToTexture(int y, int x, MapItems maptype) {
		mapimage.draw(maptype == MapItems.FLOOR ? floorimage : wallimage, x * gridsize, y * gridsize);

	}

	public void setGridSquareSize(int gridsize) {
		this.gridsize = gridsize;
	}

	public boolean checkCollision(Rectangle rect) {
		for (int i = 0; i < 4; i++) {
			GridSquare square = getGridSquare((int) (rect.x + rect.width * (i / 2)),
					(int) (rect.y + rect.height * (i % 2)));
			if (square.type == MapItems.WALL && square.rectangle.overlaps(rect)) {
				return true;
			}
		}
		return false;
	}

	private GridSquare getGridSquare(int x, int y) {
		int xcoord = x / gridsize;
		int ycoord = map.length - y / gridsize - 1;
		return map[ycoord][xcoord];
	}

	public Texture getImage() {
		// TODO Auto-generated method stub
		return mapimage;
	}

	public ArrayList<Vector2> getGuardPositions() {
		return guardPositions;
	}

	public ArrayList<Vector2> getRobberPositions() {
		return robberPositions;
	}

	public ArrayList<Vector2> getTreasurePositions() {
		return treasurePositions;
	}
}
