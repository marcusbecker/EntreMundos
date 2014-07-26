package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.SpriteTool;

public class Player extends ElementModel {

	int dir = 0; // direction
	private boolean invert;
	private int state;

	@Override
	public void loadElement() {
		setSize(20, 40);
		setImage(new ImageIcon(Config.PATH + "p.png"));
	}

	@Override
	public void update() {
		if (dir > 0)
			dir--;
	}

	@Override
	public void drawMe(Graphics2D g2d) {
		if (getImage() != null) {
			int col = 0;
			int line = 0;

			if (dir == 0) {
				col = 0;

			} else if (dir <= 10) {
				col = 1;

			} else if (dir <= 20) {
				col = 2;

			} else if (dir <= 30) {
				col = 3;

			} else if (dir <= 40) {
				col = 4;

			} else {
				dir = 0;
			}

			if (state == 1) {
				col = 2;
				line = 1;
			}

			SpriteTool s = SpriteTool.s(getImage()).matriz(5, 2);
			// drawBorders(g2d);

			s.invert(invert).draw(g2d, getPx(), getPy(), col, line);

		} else {
			super.drawMe(g2d);
		}
	}

	protected void drawBorders(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		g2d.drawRect(getPx(), getPy(), getWidth(), getHeight());

		g2d.setColor(Color.GREEN);
		g2d.drawRect(getPx(), getPy(), getImage().getIconWidth(), getImage().getIconHeight());
	}

	public void setDirection(boolean invert, int dir) {
		if (this.invert != invert) {
			this.invert = invert;
			this.dir = 0;
		} else {
			this.dir += 2;
		}
	}

	public void stop() {
		this.dir = 0;
	}

	public void setState(int state) {
		this.state = state;
		System.out.println("sjsjsjsjsj");
	}

}
