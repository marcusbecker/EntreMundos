package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.SpriteTool;

public class BarraStatus extends ElementModel {

	private float damage = 50f;
	private float energy = 50f;

	private final int MAX_DAMAGE = 100;

	public BarraStatus(int positionX, int positionY, int width, int height) {
		super(positionX, positionY, width, height, null);

		setImage(new ImageIcon(Config.PATH + "bar.png"));
	}

	@Override
	public void update() {
		if (damage > getWidth()) {
			damage = getWidth();
		}
	}

	@Override
	public void loadElement() {
	}

	@Override
	public void drawMe(Graphics2D g) {
		if (!isVisible()) {
			return;
		}

		if (getImage() != null) {
			g.setColor(Color.WHITE);

			SpriteTool s = SpriteTool.s(getImage()).matriz(1, 3).invert(false);
			int e = (int) energy;

			s.draw(g, getPx(), getPy(), 0, 0);// image
			g.fillRect(getPx() + 1 + e, getPy(), getWidth() - 1 - e, getHeight());// empty
			s.draw(g, getPx(), getPy(), 0, 2);// border

			e = (int) damage;
			s.draw(g, getPx(), getPy() + 20, 0, 1);// image
			g.fillRect(getPx() + 1 + e, getPy() + 20, getWidth() - 1 - e, getHeight());
			s.draw(g, getPx(), getPy() + 20, 0, 2);// border

		} else {
			drawBorders(g);
		}
	}

	public int getDamage() {
		return (int) damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getEnergy() {
		return (int) energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void incDamage(float inc) {
		if ((damage += inc) > MAX_DAMAGE) {
			damage = MAX_DAMAGE;
		} else if (damage < 0) {
			damage = 0;
		}
	}

	public void incEnergy(float inc) {
		if ((energy += inc) > MAX_DAMAGE) {
			energy = MAX_DAMAGE;
		} else if (energy < 0) {
			energy = 0;
		}
	}

	public boolean fullDamage() {
		return damage >= MAX_DAMAGE;
	}

}
