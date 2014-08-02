package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Graphics2D;

import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.window.Camera;

public class Menu extends ElementModel {

	private Nave nave;
	private boolean active;

	@Override
	public void drawMe(Graphics2D g2d) {
		drawBorders(g2d);
		if (isVisible() && !isActive()) {
			g2d.setColor(Color.BLUE);
			
			g2d.fillRect(Camera.c().fx(getPx() + 5), Camera.c().fy(nave.getPy() - 150), 100, 100);

			g2d.setColor(Color.WHITE);
			g2d.drawString("Entrar", Camera.c().fx(getPx() + 10), Camera.c().fy(nave.getPy() - 100));

		}
	}

	@Override
	public void update() {
		setVisible(false);
		if (nave.isInvert()) {
			setPxy(nave.getPx() + 20, nave.getAllHeight() - getHeight());
		} else {
			setPxy(nave.getAllWidth() - 30, nave.getAllHeight() - getHeight());
		}
	}

	public Menu(int positionX, int positionY, int width, int height) {
		setAll(positionX, positionY, width, height);
	}

	public void setNave(Nave nave) {
		this.nave = nave;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
