package ro.enoor.rpg.world;

import ro.enoor.rpg.MainGame;
import ro.enoor.rpg.entity.Player;
import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

public class World {
	private long startTime;
	private boolean shouldDisplay;
	private Level level;
	private Player player;
	
	public World() {
		level = new Level(this, 40, 40);
		player = new Player(level, 96, 96);
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

		if(input.isKeyPressed(Keys.S)) player.move(0);
		if(input.isKeyPressed(Keys.A)) player.move(1);
		if(input.isKeyPressed(Keys.W)) player.move(2);
		if(input.isKeyPressed(Keys.D)) player.move(3);
	}
	
	public Level getLevel() { return level; }
	public Player getPlayer() { return player; }
}
