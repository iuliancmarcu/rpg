package ro.enoor.rpg.world;

import ro.enoor.rpg.MainGame;
import ro.enoor.rpg.entity.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class WorldRenderer {
	private World world;
	private Player player;
	private SpriteBatch batch;
	private ShapeRenderer shape;

	public WorldRenderer(World world) {
		this.world = world;
		;
		player = world.getPlayer();
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		world.camera = new OrthographicCamera();
		world.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		world.camera.position.set(player.getPosition().x + Player.WIDTH / 2, player.getPosition().y + Player.HEIGHT / 2, 0);
		world.camera.update();

		batch.setProjectionMatrix(world.camera.combined);
		batch.begin();
		world.getLevel().renderLevel(batch);
		batch.end();
		
		if(MainGame.DEBUGGING) {
			shape.setProjectionMatrix(world.camera.combined);
			shape.begin(ShapeType.Rectangle);
			world.getLevel().renderTilesHitBox(shape);
			world.getPlayer().drawHitBox(shape);
			shape.end();
		}
	}

	public void updateCameraSize(int width, int height) {
		world.camera.setToOrtho(false, width >> 1, height >> 1);
		world.camera.update(true);
	}
}
