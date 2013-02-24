package ro.enoor.rpg.world;

import java.util.Random;

import ro.enoor.rpg.MainGame;
import ro.enoor.rpg.entity.Player;
import ro.enoor.rpg.level.Level;
import ro.enoor.rpg.level.tile.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

public class World {
	private long startTime;
	private boolean shouldDisplay;
	private Level level;
	private Player player;
	private Random random;
	
	public World() {
		level = new Level(this, 50, 50);
		
		random = new Random();
		int x, y;
		do {
			x = random.nextInt(level.getWidth());
			y = random.nextInt(level.getHeight());
		} while(Tile.isSolid(level.map[y][x]));
		player = new Player(level, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);

		level.initLevel();
	}
	
	@SuppressWarnings("unused")
	public void update() {
		if(MainGame.DEBUGGING) {
			if(System.currentTimeMillis() > startTime + 5000) {
				startTime = System.currentTimeMillis();
				shouldDisplay = true;
			} else shouldDisplay = false;
		}
		
		input();

		player.update();
		level.update();
		
		if(MainGame.DEBUGGING && shouldDisplay) {
			System.out.println("Update time: " + (System.currentTimeMillis() - startTime));
		}
	}
	
	public void input() {
		Input input = Gdx.input;

		if(input.isKeyPressed(Keys.W)) player.move(2);
		else if(input.isKeyPressed(Keys.S)) player.move(0);
		else if(input.isKeyPressed(Keys.A)) player.move(1);
		else if(input.isKeyPressed(Keys.D)) player.move(3);
		
		if(input.isKeyPressed(Keys.CONTROL_RIGHT)) player.shoot();
				
		/*if(input.isKeyPressed(Keys.PERIOD)) WorldRenderer.getCamera().zoom-=.1f;
		else if(input.isKeyPressed(Keys.COMMA)) WorldRenderer.getCamera().zoom+=.1f;
		
		if(input.isKeyPressed(Keys.SLASH)) WorldRenderer.getCamera().zoom = 1.0f;*/
	}
	
	public Level getLevel() { return level; }
	public Player getPlayer() { return player; }
}
