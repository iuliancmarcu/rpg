package ro.enoor.rpg.world;

import ro.enoor.rpg.MainGame;
import ro.enoor.rpg.entity.Player;
import ro.enoor.rpg.level.tile.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class WorldRenderer {
	private World world;
	private Player player;
	private SpriteBatch batch;
	private static OrthographicCamera camera;

	public WorldRenderer(World world) {
		this.world = world;
		
		player = world.getPlayer();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
	}
		
	private void updateCameraPosition() {
		float targetX, targetY;
		
		if(player.getPosition().x - camera.viewportWidth / 2 < 0) targetX = camera.viewportWidth / 2;
		else if(player.getPosition().x + camera.viewportWidth / 2 > world.getLevel().getWidth() * Tile.TILE_SIZE) 
			targetX = world.getLevel().getWidth() * Tile.TILE_SIZE - camera.viewportWidth / 2;
		else targetX = player.getPosition().x;
		
		if(player.getPosition().y - camera.viewportHeight / 2 < 8) targetY = camera.viewportHeight / 2 + 8;
		else if(player.getPosition().y + camera.viewportHeight / 2 > world.getLevel().getHeight() * Tile.TILE_SIZE + 8) 
			targetY = world.getLevel().getHeight() * Tile.TILE_SIZE - camera.viewportHeight / 2 + 8;
		else targetY = player.getPosition().y;
		
		float dx = targetX - camera.position.x, dy = targetY - camera.position.y, dist = (float) Math.hypot(dx, dy);
		Vector3 cameraVector = new Vector3((float) Math.cos(Math.atan2(dy, dx)), (float) Math.sin(Math.atan2(dy, dx)), 0);
		cameraVector.mul(dist / 50f);
		
		camera.position.add(cameraVector);
		camera.update();
	}

	public void render() {
		Gdx.gl.glClearColor(.5f, .5f, .5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		updateCameraPosition();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			world.getLevel().renderTiles(batch);
			world.getLevel().renderEntities(batch);
		batch.end();
		
		MainGame.fpslogger.log();
	}

	public void updateCameraSize(int width, int height) {
		camera.setToOrtho(false, width / 2, height / 2);
		camera.update();
	}
	
	public static OrthographicCamera getCamera() { return camera; }
}
