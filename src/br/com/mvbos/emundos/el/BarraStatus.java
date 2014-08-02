package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.SpriteTool;

public class BarraStatus extends ElementModel {

	private int damage;

	public BarraStatus(int positionX, int positionY, int width, int height) {
		super(positionX, positionY, width, height, null);
		setImage(new ImageIcon(Config.PATH + "br_dano.png"));
	}

	@Override
	public void update() {
		if (damage < 0) {
			damage = 0;
		} else if (damage > getWidth()) {
			damage = getWidth();
		}
	}

	@Override
	public void loadElement() {
	}

	@Override
	public void drawMe(Graphics2D g2d) {
		if (!isVisible()) {
			return;
		}

		if (getImage() != null) {

			SpriteTool s = SpriteTool.s(getImage()).matriz(1, 2);

			s.invert(false).draw(g2d, getPx(), getPy(), 0, 0);
			g2d.setColor(Color.WHITE);
			g2d.fillRect(getPx() + 1 + damage, getPy(), getWidth() - 1 - damage, getHeight());

			s.invert(false).draw(g2d, getPx(), getPy(), 0, 1);// border

			// g2d.drawImage(getImage().getImage(), getPx(), getPy(), null);

		} else {
			drawBorders(g2d);
		}
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
