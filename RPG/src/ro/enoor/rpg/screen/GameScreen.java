package ro.enoor.rpg.screen;

import ro.enoor.rpg.world.GameWorld;
import ro.enoor.rpg.world.WorldRenderer;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	private GameWorld world;
	private WorldRenderer renderer;

	public GameScreen() {
		world = new GameWorld();
		renderer = new WorldRenderer(world);
	}

	public void render(float delta) {
		world.update();
		renderer.render();
	}

	public void resize(int width, int height) {
		renderer.updateCameraSize(width, height);
	}

	public void show() {

	}

	public void hide() {

	}

	public void pause() {

	}

	public void resume() {

	}

	public void dispose() {

	}
}
