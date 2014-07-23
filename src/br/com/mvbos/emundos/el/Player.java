package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.jeg.element.ElementModel;

public class Player extends ElementModel {

	int dir = 0; // direction
	private boolean invert;

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
			int tempX = 0;

			if (dir == 0) {
				tempX = 0;
			} else if (dir <= 10) {
				tempX = 20;
			} else if (dir <= 20) {
				tempX = 40;
			} else if (dir <= 30) {
				dir = 0;// jump
			} else if (dir <= 40) {
				dir = 0;
			}

			// drawBorders(g2d);

			AffineTransform old = g2d.getTransform();

			if (invert) {
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-getImage().getIconWidth(), 0);

				g2d.setTransform(tx);

				g2d.drawImage(getImage().getImage(), 
						(getPx() * -1) + getImage().getIconWidth() - getWidth(), getPy(),
						(getPx() * -1) + getImage().getIconWidth(), getPy() + getHeight(), 
						tempX, 0, tempX + getWidth(), getHeight(), null);
			} else {
				g2d.drawImage(getImage().getImage(), getPx(), getPy(), getPx()
						+ getWidth(), getPy() + getHeight(), tempX, 0, tempX
						+ getWidth(), getHeight(), null);
			}

			g2d.setTransform(old);

		} else {
			super.drawMe(g2d);
		}
	}

	protected void drawBorders(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		g2d.drawRect(getPx(), getPy(), getWidth(), getHeight());

		g2d.setColor(Color.GREEN);
		g2d.drawRect(getPx(), getPy(), getImage().getIconWidth(), getImage()
				.getIconHeight());
	}

	public void setDirection(boolean invert, int dir) {
		if (this.invert != invert) {
			this.invert = invert;
			this.dir = 0;
		} else {
			this.dir += 2;
		}
	}

}
