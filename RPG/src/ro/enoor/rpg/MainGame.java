package ro.enoor.rpg;

import ro.enoor.rpg.screen.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;

public class MainGame extends Game {	
	public static FPSLogger fpslogger = new FPSLogger();
	
	public void create() {
		this.setScreen(new GameScreen());
	}
}
