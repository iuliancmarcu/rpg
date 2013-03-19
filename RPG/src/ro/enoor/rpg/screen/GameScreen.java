package ro.enoor.rpg.screen;

import ro.enoor.rpg.world.World;
import ro.enoor.rpg.world.WorldRenderer;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	private World world;
	private WorldRenderer renderer;

	public GameScreen() {
		world = new World();
		renderer = new WorldRenderer(world);
	}

	public void render(float delta) {
		world.update(1f);
		renderer.render();
	}

	public void resize(int width, int height) {
		
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
