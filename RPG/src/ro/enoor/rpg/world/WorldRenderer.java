package ro.enoor.rpg.world;

import ro.enoor.rpg.entity.Player;
import ro.enoor.rpg.level.tile.Tile;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {
	private GameWorld world;
	private Player player;
	private SpriteBatch batch;
	private static OrthographicCamera camera;
	private RayHandler handler;

	public WorldRenderer(GameWorld world) {
		this.world = world;
		
		player = world.getPlayer();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
	}
		
	private void updateCameraPosition() {
		float cameraX, cameraY;
		
		if(player.getPosition().x - camera.viewportWidth / 2 < 0) cameraX = camera.viewportWidth / 2;
		else if(player.getPosition().x + camera.viewportWidth / 2 > world.getLevel().getWidth() * Tile.TILE_SIZE) 
			cameraX = world.getLevel().getWidth() * Tile.TILE_SIZE - camera.viewportWidth / 2;
		else cameraX = player.getPosition().x;
		
		if(player.getPosition().y - camera.viewportHeight / 2 < 8) cameraY = camera.viewportHeight / 2 + 8;
		else if(player.getPosition().y + camera.viewportHeight / 2 > world.getLevel().getHeight() * Tile.TILE_SIZE + 8) 
			cameraY = world.getLevel().getHeight() * Tile.TILE_SIZE - camera.viewportHeight / 2 + 8;
		else cameraY = player.getPosition().y;
		
		camera.position.set(cameraX, cameraY, 0);
		camera.update();
	}

	public void render() {
		Gdx.gl.glClearColor(.5f, .5f, .5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		updateCameraPosition();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			world.getLevel().renderTiles(batch);
			world.getLevel().renderEntities(batch);
		batch.end();
	}

	public void updateCameraSize(int width, int height) {
		camera.setToOrtho(false, width / 2, height / 2);
		camera.update();
	}
	
	public static OrthographicCamera getCamera() { return camera; }
}
