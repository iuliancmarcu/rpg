package ro.enoor.rpg;

import ro.enoor.rpg.screen.GameScreen;

import com.badlogic.gdx.Game;

public class MainGame extends Game {	
	public void create() {
		this.setScreen(new GameScreen());
	}
}
