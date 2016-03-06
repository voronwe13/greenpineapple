package com.greenpineapple.game;

import java.util.ArrayList;
import java.util.List;

import Map.MapClass;
import Map.Treasure;
import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.greenpineapple.input.GPAInputProcessor;
import com.greenpineapple.player.GPAPlayer;
import com.greenpineapple.player.PlayerType;

public class GameScreen implements Screen {

	private static final int RAYS_NUM = 100;
	private RayHandler rayhandler;
	private ConeLight conelight;
	private World world;
	private OrthographicCamera cam;
	private String[] lines;

	private SpriteBatch batch;
	// private GPAPlayer guardplayer, robberplayer;
	private List<GPAPlayer> guardplayers, robberplayers;
	private List<Treasure> treasures;

	private Texture mapimage;
	private TextureRegion guardcurrentframe, robbercurrentframe;

	float statetime;

	MapClass map;

	public GameScreen(Game game) {
		batch = new SpriteBatch();
		map = new MapClass();
		List<Vector2> guardpositions = map.getGuardPositions();
		List<Vector2> robberpositions = map.getRobberPositions();
		List<Vector2> treasurepositions = map.getTreasurePositions();
		guardplayers = new ArrayList<GPAPlayer>();
		robberplayers = new ArrayList<GPAPlayer>();
		treasures = new ArrayList<Treasure>();
		for (int i = 0; i < guardpositions.size(); i++) {
			GPAPlayer guardplayer = new GPAPlayer(PlayerType.GUARD);
			guardplayer.setTexture("Guard Sprite polished small.png");
			int posx = (int) guardpositions.get(i).x;
			int posy = (int) guardpositions.get(i).y;
			guardplayer.setPosition(posx, posy);
			guardplayer.setMap(map);
			guardplayers.add(guardplayer);
		}
		for (int i = 0; i < robberpositions.size(); i++) {
			GPAPlayer robberplayer = new GPAPlayer(PlayerType.ROBBER);
			robberplayer.setTexture("Thief Sprite polished small.png");
			int posx = (int) robberpositions.get(i).x;
			int posy = (int) robberpositions.get(i).y;
			robberplayer.setPosition(posx, posy);
			robberplayer.setMap(map);
			robberplayers.add(robberplayer);
		}
		for (Vector2 treasureposition : treasurepositions) {
			Treasure treasure = new Treasure(treasureposition);
			treasures.add(treasure);
		}
		GPAInputProcessor inputProcessor = new GPAInputProcessor();
		inputProcessor.setPlayer(guardplayers.get(0));
		Gdx.input.setInputProcessor(inputProcessor);
		world = new World(new Vector2(), true);
		RayHandler.useDiffuseLight(true);
		rayhandler = new RayHandler(world);
		rayhandler.setCulling(true);
		// rayhandler.setCombinedMatrix(gameController.camera.combined);
		rayhandler.setAmbientLight(1);
		rayhandler.setShadows(false);
		conelight = new ConeLight(rayhandler, RAYS_NUM, new Color(.1f, .1f, .5f, .5f), 5f, 20, 20, 20, 20);

		statetime = 0f;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		for (GPAPlayer guardplayer : guardplayers) {
			guardplayer.update();
		}
		for (GPAPlayer robberplayer : robberplayers) {
			robberplayer.update();
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears
													// the screen.
		statetime += Gdx.graphics.getDeltaTime();
		mapimage = map.getImage();
		batch.begin();
		batch.draw(mapimage, 0, 0);
		for (GPAPlayer guardplayer : guardplayers) {
			guardcurrentframe = guardplayer.getCurrentFrame(statetime);
			batch.draw(guardcurrentframe, guardplayer.getPositionX(), guardplayer.getPositionY());
		}
		for (GPAPlayer robberplayer : robberplayers) {
			robbercurrentframe = robberplayer.getCurrentFrame(statetime);
			batch.draw(robbercurrentframe, robberplayer.getPositionX(), robberplayer.getPositionY());
		}
		batch.end();
		rayhandler.setCombinedMatrix(cam);
		rayhandler.updateAndRender();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		cam = new OrthographicCamera(20.0f, 20.0f * height / width);
		cam.position.set(cam.viewportWidth / 2.0f, cam.viewportHeight / 2.0f, 0.0f);
		cam.update();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
