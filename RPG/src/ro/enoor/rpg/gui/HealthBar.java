package ro.enoor.rpg.gui;

import ro.enoor.rpg.entity.MoveableEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthBar extends GuiElement {
	private HPUnit unit;
	private MoveableEntity entity;
	private int health, totalHealth;
	
	public HealthBar(MoveableEntity entity, int health) {
		super(entity.getPosition().x, entity.getPosition().y + entity.getHeight() + .5f, "healthbar");
		this.entity = entity;
		this.health = totalHealth = health;
		unit = new HPUnit(this);
	}

	public void update() {
		position.x = entity.getPosition().x;
		position.y = entity.getPosition().y + entity.getHeight() + .5f;
		unit.update();
	}

	public void draw(SpriteBatch batch) {
		unit.draw(batch);
		batch.draw(texture, position.x, position.y);
	}
	
	public void addHealth(int healValue) { 
		health += healValue;
		health = (health > totalHealth) ? totalHealth : health;
	}
	public void subHealth(int damage) { health -= damage; }
	public int getHealth() { return health; }
	public int getTotalHealth() { return totalHealth; }
	
	private class HPUnit extends GuiElement {
		private HealthBar bar;
		
		public HPUnit(HealthBar bar) {
			super(bar.position.x + 1, bar.position.y + 1, "healthunit");
			this.bar = bar;
		}
		
		public void update() {
			position.x = bar.position.x + 1;
			position.y = bar.position.y + 1;
		}

		public void draw(SpriteBatch batch) {
			batch.draw(texture, position.x, position.y, 0, 0, 1, 2, (bar.health * 14) / bar.totalHealth, 1, 0);
		}
	}
}
