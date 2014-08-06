package br.com.mvbos.emundos.el;

import java.awt.Graphics2D;

import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.window.Camera;

public class Loja extends ElementModel {

	@Override
	public void drawMe(Graphics2D g) {
		if (!isVisible()) {
			return;
		}
		
		Camera.c().close(g, this);
	}

}
