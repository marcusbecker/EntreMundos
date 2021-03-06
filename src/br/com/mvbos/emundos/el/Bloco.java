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
	public void drawMe(Graphics2D g) {
		if (!isVisible()) {
			return;
		}

		Camera.c().close(g, this);
	}

}
