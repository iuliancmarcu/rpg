package ro.enoor.rpg.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ro.enoor.rpg.gui.HealthBar;
import ro.enoor.rpg.level.Level;

public abstract class Mob extends MoveableEntity {
	protected HealthBar healthBar;

	public Mob(Level level, float x, float y, float speed, int health, String type) {
		super(level, x, y, speed, type);
		healthBar = new HealthBar(this, health);
	}
	
	public void update() {
		super.update();
		healthBar.update();
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		healthBar.draw(batch);
	}

	public void hurt(int damage) {
		healthBar.subHealth(damage);
		if(healthBar.getHealth() <= 0)
			setRemoved();
	}
	

	public HealthBar getHealthBar() { return healthBar; }
}
