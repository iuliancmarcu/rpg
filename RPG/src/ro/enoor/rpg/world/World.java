package ro.enoor.rpg.world;

import ro.enoor.rpg.entity.Player;
import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class World {	
	public OrthographicCamera camera;
	private Level level;
	private Player player;
	
	public World() {
		level = new Level(this, 40, 40);
		player = new Player(level, 96, 96);
		level.initLevel();
	}
	
	public void update() {
		input();
		
		player.update();
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
