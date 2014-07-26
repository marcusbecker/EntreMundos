package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Graphics2D;

import br.com.mvbos.jeg.element.ElementModel;

public class Menu extends ElementModel {

	private Nave nave;

	@Override
	public void drawMe(Graphics2D g2d) {
		super.drawMe(g2d);

		if (isVisible()) {
			g2d.fillRect(nave.getPx() + 5, nave.getPy() - 150, 100, 100);

			g2d.setColor(Color.WHITE);
			g2d.drawString("Entrar", nave.getPx() + 10, nave.getPy() - 100);

		}
	}

	@Override
	public void update() {
		setPxy(nave.getPx() + 20, nave.getPy() + nave.getHeight() - getHeight());
	}

	public Menu(int positionX, int positionY, int width, int height) {
		setAll(positionX, positionY, width, height);
	}

	public void setNave(Nave nave) {
		this.nave = nave;
	}

}
