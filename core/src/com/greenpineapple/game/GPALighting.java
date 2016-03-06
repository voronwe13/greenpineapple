package com.greenpineapple.game;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.greenpineapple.GreenPineappleGame;

public class GPALighting {

	private static final int RAYS_NUM = 100;
	private RayHandler rayhandler;
	private ConeLight conelight;
	private PointLight pointlight;
	private World world;
	private OrthographicCamera cam;

	private PointLight pointlight2;

	private static GPALighting lighting = null;
	
	private GPALighting(){
		
	}

	public void initialize(){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        cam = new OrthographicCamera(GreenPineappleGame.SCREEN_WIDTH, GreenPineappleGame.SCREEN_HEIGHT);
        cam.setToOrtho(false);
        //cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 20);
        cam.update();
        
		world = new World(new Vector2(), true);
		RayHandler.useDiffuseLight(true);
		rayhandler = new RayHandler(world);
		rayhandler.setCulling(true);
		//rayhandler.setCombinedMatrix(gameController.camera.combined);
		rayhandler.setAmbientLight(0.1f);
		rayhandler.setShadows(false);
		pointlight = new PointLight(rayhandler, 32, new Color(1f, 0.0f, 0.0f, 0.8f), 2, 0, 0);
		//conelight = new ConeLight(rayhandler, RAYS_NUM, new Color(.1f,.1f,.5f,.5f), 5f, 20, 20, 20, 20);
//		pointlight = new PointLight(rayhandler, RAYS_NUM);
//		pointlight.setPosition(100,100);
//		pointlight.setDistance(5);
//		pointlight.setColor(.1f, .4f, .8f, 0.3f);
//		
//		// this lights the map (Sun)  
//		pointlight2 = new PointLight(rayhandler, 200);
//		pointlight2.setPosition(30,30);
//		pointlight2.setDistance(50);
//		pointlight2.setColor(.1f, .4f, .8f, 0.5f);
	}
	
	public void preRender(float delta){
		world.step(delta, 1, 1);
		cam.update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.

	}
	
	public void postRender(){
		rayhandler.setCombinedMatrix(cam.combined, cam.position.x, cam.position.y,cam.viewportWidth, cam.viewportHeight);
		rayhandler.updateAndRender();
	}

	public Camera getCamera() {
		// TODO Auto-generated method stub
		return cam;
	}
	
	public void resize(int width, int height){
		
	}

	public static GPALighting getInstance() {
		// TODO Auto-generated method stub
		if(lighting == null){
			lighting = new GPALighting();
		}
		return lighting;
	}
}
