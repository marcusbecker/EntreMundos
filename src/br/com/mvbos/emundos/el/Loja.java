package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.emundos.data.Item;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.Engine;
import br.com.mvbos.jeg.engine.KeysMap;
import br.com.mvbos.jeg.engine.MathTool;
import br.com.mvbos.jeg.engine.SpriteTool;
import br.com.mvbos.jeg.window.Camera;

public class Loja extends ElementModel {

	private Item[] itens = new Item[5];

	private int iSel;

	private boolean openMenu;

	private ImageIcon atendente;
	private ImageIcon menu;

	private int atPy = 0;
	private int atPx = 0;
	private boolean dir;
	private boolean hold;
	private Player p;

	@Override
	public void loadElement() {
		setImage(new ImageIcon(Config.PATH + "l_01.png"));
		atendente = new ImageIcon(Config.PATH + "p_loja.png");
		menu = new ImageIcon(Config.PATH + "menu.png");

		itens[0] = new Item(Menu.Type.ENERGY);
	}

	@Override
	public void update() {

		if (atPx == 0) {
			atPx = getPx() + 10;
			atPy = getAllHeight() - atendente.getIconHeight();
		}

		if (!hold) {
			hold = MathTool.r.nextInt(100) == 10;

		} else if (MathTool.r.nextInt(100) > 95) {
			hold = false;
		}

		if (p != null) {
			if (p.getPad().first(KeysMap.B0)) {
				openMenu = !openMenu;
				// Clicked.consume(KeysMap.B0);
			}

			if (openMenu) {
				if (p.getPad().first(KeysMap.LEFT)) {
					iSel = Item.next(true, iSel, itens.length);

				} else if (p.getPad().first(KeysMap.RIGHT)) {
					iSel = Item.next(false, iSel, itens.length);

				} else if (p.getPad().is(KeysMap.B1) && getItem(iSel) != null) {
					p.addItem(getItem(iSel));
					removeItem(iSel);
				}

			}

			p.setState(openMenu ? Player.State.IN_PLACE : Player.State.DEF);

			p.hideMenu(false);
			p = null;

		} else {
			iSel = 0;
			openMenu = false;
		}

		/*
		 * if (l < 0 || l == itens.length) { l = l < 0 ? itens.length - 1 : 0; }
		 */

		if (!openMenu && !hold) {
			if (!dir && atPx < (getAllWidth() - 25)) {
				atPx++;
			} else {
				dir = true;
			}

			if (dir && atPx > getPx() + 10) {
				atPx--;
			} else {
				dir = false;
			}
		}

	}

	@Override
	public void drawMe(Graphics2D g) {
		if (!isVisible()) {
			return;
		}

		if (getImage() != null) {
			SpriteTool.s(getImage()).matriz(2, 1).invert(false)
					.draw(g, Camera.c().fx(getPx()), Camera.c().fy(getPy()), 0, 0);

			SpriteTool.s(atendente).matriz(2, 1).invert(dir)
					.draw(g, Camera.c().fx(atPx), Camera.c().fy(atPy), openMenu || hold ? 1 : 0, 0);

			SpriteTool.s(getImage()).matriz(2, 1).invert(false)
					.draw(g, Camera.c().fx(getPx()), Camera.c().fy(getPy()), 1, 0);

		} else {
			Camera.c().close(g, this);
		}

		if (openMenu) {
			g.setColor(Color.BLUE);
			drawSelectionMenu(g, itens, iSel);
		}
	}

	public void drawSelectionMenu(Graphics2D g, Item[] itens, int iSel) {
		final int px = Engine.getIWindowGame().getWindowWidth() / 2 - menu.getIconWidth() / 2;
		final int py = Engine.getIWindowGame().getWindowHeight() / 2 - menu.getIconHeight() / 2;

		// g.fillRect(px, py, 500, 200);
		g.drawImage(menu.getImage(), px, py, null);

		final int s = 40;
		for (int i = 0; i < itens.length; i++) {
			g.setColor(Color.ORANGE);

			if (i == iSel) {
				g.fillRect(px + (i * s) + (20 * (i + 1)) + 5, py + 15 + 5, s - 9, s - 10);

			}

			if (itens[i] != null) {
				g.drawRect(px + (i * s) + (20 * (i + 1)), py + 15, s, s);

			} else {
				g.setColor(Color.GREEN);
				g.drawRect(px + (i * s) + (20 * (i + 1)), py + 15, s, s);
			}

		}

		if (itens[iSel] != null) {
			g.setColor(Color.BLACK);
			g.drawString(itens[iSel].toString(), px + 20, py + 200 - 20);

		} else {
			g.setColor(Color.BLACK);
			g.drawString("---", px + 20, py + 200 - 20);
		}
	}

	public static void _drawSelectionMenu(Graphics2D g, Item[] itens, int iSel) {
		final int px = Engine.getIWindowGame().getWindowWidth() / 2 - 500 / 2;
		final int py = Engine.getIWindowGame().getWindowHeight() / 2 - 200 / 2;

		g.fillRect(px, py, 500, 200);

		final int s = 60;
		// g.drawRect(px + 0 + 20, py + 5, 40, 40);
		// g.drawRect(px + 40 + 40, py + 5, 40, 40);
		for (int i = 0; i < itens.length; i++) {
			g.setColor(Color.ORANGE);

			if (i == iSel) {
				g.fillRect(px + (i * s) + (20 * (i + 1)) + 5, py + 15 + 5, s - 9, s - 10);

			}

			if (itens[i] != null) {
				g.drawRect(px + (i * s) + (20 * (i + 1)), py + 15, s, s);

			} else {
				g.setColor(Color.GREEN);
				g.drawRect(px + (i * s) + (20 * (i + 1)), py + 15, s, s);
			}

		}

		if (itens[iSel] != null) {
			g.setColor(Color.BLACK);
			g.drawString(itens[iSel].toString(), px + 20, py + 200 - 20);

		} else {
			g.setColor(Color.BLACK);
			g.drawString("---", px + 20, py + 200 - 20);
		}
	}

	public Item getItem(int i) {
		return itens[i];
	}

	public Item[] getItens() {
		return itens;
	}

	public void setItens(Item[] itens) {
		this.itens = itens;
	}

	public void removeItem(int i) {
		itens[i] = null;
	}

	public void setPlayer(Player p) {
		this.p = p;
		p.hideMenu(true);
	}
}
