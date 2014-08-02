package br.com.mvbos.emundos.el;

import java.awt.Graphics2D;

import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.window.Camera;

public class Bloco extends ElementModel {

	public Bloco(int positionX, int positionY, int width, int height) {
		super(positionX, positionY, width, height, null);
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
			g2d.drawImage(getImage().getImage(), getPx(), getPy(), null);

		} else {
			g2d.setColor(getDefaultColor());
			g2d.drawRect(Camera.c().fx(getPx()), Camera.c().fy(getPy()), getWidth(), getHeight());
		}
	}

}
