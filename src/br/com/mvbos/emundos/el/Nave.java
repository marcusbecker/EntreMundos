package br.com.mvbos.emundos.el;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.jeg.element.ElementModel;

public class Nave extends ElementModel {

	private boolean inside;

	@Override
	public void loadElement() {
		setSize(80, 60);
		setImage(new ImageIcon(Config.PATH + "n.png"));
	}

	@Override
	public void drawMe(Graphics2D g2d) {
		if (inside) {
			g2d.drawImage(getImage().getImage(), getPx(), getPy(),
					getPx() + getWidth(), getPy() + getHeight(), getWidth(), 0, getWidth() * 2, getHeight(), null);
		} else {
			g2d.drawImage(getImage().getImage(), getPx(), getPy(),
					getPx() + getWidth(), getPy() + getHeight(), 0, 0, getWidth(), getHeight(), null);
		}
	}

	public boolean isInside() {
		return inside;
	}

	public void setInside(boolean inside) {
		this.inside = inside;
	}

}
