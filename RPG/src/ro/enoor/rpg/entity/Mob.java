package ro.enoor.rpg.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ro.enoor.rpg.gui.HealthBar;
import ro.enoor.rpg.level.Level;

public abstract class Mob extends MoveableEntity {
	protected HealthBar healthBar;
	protected int attackSpeedMS;
	protected long lastAttackMS;

	public Mob(Level level, float x, float y, float speed, int a_speedMS, int health, String type) {
		super(level, x, y, speed, type);
		healthBar = new HealthBar(this, health);
		attackSpeedMS = a_speedMS;
	}
	
	public void attack() {
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastAttackMS > attackSpeedMS) {
			mobAttack();			
			lastAttackMS = currentTime;
		}
	}
	
	public void update(float delta) {
		super.update(delta);
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
	
	protected abstract void mobAttack();

	public HealthBar getHealthBar() { return healthBar; }
}
