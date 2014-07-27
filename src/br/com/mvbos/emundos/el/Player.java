package br.com.mvbos.emundos.el;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.KeysMap;
import br.com.mvbos.jeg.engine.SpriteTool;

public class Player extends ElementModel {

	public enum State {
		DEF, IN;
	}

	int dir = 0; // direction
	private boolean invert;
	private State state = State.DEF;

	@Override
	public void loadElement() {
		setSize(20, 40);
		setImage(new ImageIcon(Config.PATH + "p.png"));
	}

	@Override
	public void update() {
		if (state == State.DEF) {
			if (dir > 0)
				dir--;
		}
	}

	@Override
	public void drawMe(Graphics2D g2d) {

		if (getImage() != null) {
			int col = 0;
			int line = 0;

			if (state == State.DEF) {
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

			} else if (state == State.IN) {
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

	public void setDirection(boolean invert) {
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void go(KeysMap direction) {
		if (getState() == Player.State.IN) {
			return;
		}

		switch (direction) {
		case UP:
			break;
		case DOWN:
			break;
		case LEFT:
			setDirection(true);
			incPx(-2);
			break;
		case RIGHT:
			setDirection(false);
			incPx(+2);
			break;
		default:
			break;
		}
	}

	public void action() {

	}

	public void reposition(boolean invert, int px, int py) {
		this.invert = invert;
		setPxy(px, py);
	}

}
