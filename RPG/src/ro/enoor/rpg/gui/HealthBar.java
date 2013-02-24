package ro.enoor.rpg.gui;

import ro.enoor.rpg.entity.MoveableEntity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthBar extends GuiElement {
	private HPUnit unit;
	private MoveableEntity entity;
	
	public HealthBar(MoveableEntity entity) {
		super(entity.getPosition().x, entity.getPosition().y + entity.getHeight(), "healthbar");
		this.entity = entity;
		unit = new HPUnit(this);
	}

	public void update() {
		position.x = entity.getPosition().x;
		position.y = entity.getPosition().y + entity.getHeight();
		unit.update();
	}

	public void draw(SpriteBatch batch) {
		unit.draw(batch);
		batch.draw(texture, position.x, position.y);
	}
	
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
		
		public int getScaleFactor() {
			return (bar.entity.getHealth() * 7) / bar.entity.getTotalHealth();
		}

		public void draw(SpriteBatch batch) {
			batch.draw(texture, position.x, position.y, 0, 0, 2, 2, getScaleFactor(), 1, 0);
		}
	}
}
