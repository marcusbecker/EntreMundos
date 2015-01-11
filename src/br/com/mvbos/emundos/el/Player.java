package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.emundos.data.Item;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.JoyPad;
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

	private int life;

	public int velInc = 2;

	private int iSel;
	private boolean inMenu;
	private boolean itemActive;
	private Item[] itens = new Item[6];

	@Override
	public void loadElement() {
		setSize(20, 40);
		setImage(new ImageIcon(Config.PATH + "p.png"));
	}

	State old;
	private boolean hideMenu;
	private JoyPad pad;

	@Override
	public void update() {

		if (state != old) {
			old = state;
			System.out.println("old " + state);
		}

		/*
		 * State.DEF = Pode se mover esq, dir e abrir menu State.IN = Pode abrir
		 * menu e interagir com a nave, o movimento fica por conta da nave
		 * devido aos ajustes de posicao State.IN_PLACE = ignora movimentos
		 */

		if (state == State.DEF || state == State.IN) {
			if (pad.is(KeysMap.B0) && !hideMenu) {
				inMenu = !inMenu;
				pad.consume(KeysMap.B0);
			}

		}

		if (itemActive && itens[iSel] == null) {
			itemActive = false;
		}

		if (inMenu) {
			if (pad.is(KeysMap.LEFT) || pad.first(KeysMap.RIGHT)) {
				iSel = Item.next(pad.first(KeysMap.LEFT), iSel, itens.length);

			} else if (pad.first(KeysMap.B1) && itens[iSel] != null) {
				itemActive = !itemActive;
			}

			// Clicked.consumeAll();

		} else if (state == State.DEF) {
			if (pad.is(KeysMap.LEFT)) {
				moveX(-velInc);

			} else if (pad.is(KeysMap.RIGHT)) {
				moveX(velInc);
			}
		}

		/*
		 * if (state == State.DEF || state == State.IN) { if (dir > 0) dir--; }
		 */
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

		if (inMenu) {
			Loja._drawSelectionMenu(g, itens, iSel);

		}

		if (itemActive) {
			g.fillRect(Camera.c().fx(getPx() - 10), Camera.c().fy(getPy()), 10, 10);
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

	public int getLife() {
		return life;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
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

	@Override
	public int getHitWidth() {
		return getWidth() - 10;
	}

	@Override
	public float getHitX() {
		return getPx() + 5;
	}

	public boolean isInMenu() {
		return inMenu;
	}

	public void setInMenu(boolean inMenu) {
		this.inMenu = inMenu;
	}

	public Item getItemActive() {
		return itens[iSel];
	}

	public void removeItemActive() {
		itens[iSel] = null;
	}

	public void hideMenu(boolean hideMenu) {
		this.hideMenu = hideMenu;
	}

	public JoyPad getPad() {
		return pad;
	}

	public void setControl(JoyPad pad) {
		this.pad = pad;

	}

}
