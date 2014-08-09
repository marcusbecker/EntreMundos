package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.emundos.data.Item;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.KeysMap;
import br.com.mvbos.jeg.engine.SpriteTool;
import br.com.mvbos.jeg.window.Camera;

public class Player extends ElementModel {

	public enum State {
		DEF, IN, IN_CONTROLLER, IN_PLACE, IN_FOCUS;
	}

	int dir = 0; // direction
	private boolean invert;
	private State state = State.DEF;

	private boolean up;

	private boolean down;

	private boolean lft;

	private boolean rgt;

	private int life;

	public int velInc = 2;

	private Item[] itens = new Item[6];

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
	public void drawMe(Graphics2D g) {

		g.setColor(Color.BLUE);
		for (int i = 0; i < itens.length; i++) {
			if (itens[i] == null) {
				g.drawRect((20 * i) + 5, 30, 20, 20);

			} else {
				g.fillRect((20 * i) + 5, 30, 20, 20);
			}
		}

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

			s.invert(invert).draw(g, Camera.c().fx(getPx()), Camera.c().fy(getPy()), col, line);

		} else {
			super.drawMe(g);
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

	public void press(KeysMap direction) {
		if (getState() == Player.State.IN_CONTROLLER || getState() == Player.State.IN_PLACE) {
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

	public void reposition(boolean invert, int px, int py) {
		setDirection(invert);
		setPxy(px, py);
	}

	public Item[] getItens() {
		return itens;
	}

	public boolean addItem(Item item) {
		for (int i = 0; i < itens.length; i++) {
			if (itens[i] == null) {
				itens[i] = item;
				return true;
			}
		}

		return false;
	}

	public void inShip() {
		if (getState() == State.DEF) {
			setState(State.IN);
		}
	}

	public void exitShip() {
		if (getState() == State.IN || getState() == State.IN_CONTROLLER) {
			setState(State.DEF);
		}
	}

}
