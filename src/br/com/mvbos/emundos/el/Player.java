package br.com.mvbos.emundos.el;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.Engine;
import br.com.mvbos.jeg.engine.KeysMap;
import br.com.mvbos.jeg.engine.SpriteTool;
import br.com.mvbos.jeg.window.Camera;

public class Player extends ElementModel {

	public enum State {
		DEF, IN, IN_CONTROLLER;
	}

	int dir = 0; // direction
	private boolean invert;
	private State state = State.DEF;

	private boolean up;

	private boolean down;

	private boolean lft;

	private boolean rgt;

	private int life;
	private Nave n;

	public int velInc = 2;

	@Override
	public void loadElement() {
		setSize(20, 40);
		setImage(new ImageIcon(Config.PATH + "p.png"));
	}

	@Override
	public void update() {

		if (getState() == State.IN) {

		} else if (getState() == State.IN_CONTROLLER) {

		} else {

			if (lft) {
				moveX(-velInc);

			} else if (rgt) {
				moveX(velInc);
			}

			if (lft || rgt) {
				if (getAllWidth() - getHalfWidth() > Engine.getIWindowGame().getCanvasWidth() / 2) {
					Camera.c().rollX(lft ? -velInc : velInc);
				}
			}
		}

		up = false;
		down = false;
		lft = false;
		rgt = false;

		if (state == State.DEF || state == State.IN) {
			if (dir > 0)
				dir--;
		}

	}

	public void moveX(int inc) {
		setDirection(inc < 0);
		incPx(inc);
	}

	@Override
	public void drawMe(Graphics2D g2d) {

		if (getImage() != null) {
			int col = 0;
			int line = 0;

			if (state == State.DEF || state == State.IN) {
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

			} else if (state == State.IN_CONTROLLER) {
				col = 2;
				line = 1;
			}

			SpriteTool s = SpriteTool.s(getImage()).matriz(5, 2);

			s.invert(invert).draw(g2d, Camera.c().fx(getPx()), Camera.c().fy(getPy()), col, line);

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
		if (getState() == Player.State.IN_CONTROLLER) {
			return;
		}

		switch (direction) {
		case UP:
			break;
		case DOWN:
			break;
		case LEFT:
			lft = true;
			break;
		case RIGHT:
			rgt = true;
			break;
		default:
			break;
		}
	}

	public void action() {

	}

	public void reposition(boolean invert, int px, int py) {
		setDirection(invert);
		setPxy(px, py);
	}

	public void setNave(Nave n) {
		if (n == null) {
			setState(State.DEF);
		}

		this.n = n;
	}

}
