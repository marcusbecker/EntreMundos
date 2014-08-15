package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.emundos.data.Item;
import br.com.mvbos.emundos.sc.Planeta;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.Engine;
import br.com.mvbos.jeg.engine.KeysMap;
import br.com.mvbos.jeg.engine.SpriteTool;
import br.com.mvbos.jeg.window.Camera;

public class Loja extends ElementModel {

	private Item[] itens = new Item[6];
	private Planeta planeta;

	private int iSel;

	private boolean openMenu;

	public Loja(Planeta planeta) {
		this.planeta = planeta;
	}

	@Override
	public void loadElement() {
		setImage(new ImageIcon(Config.PATH + "l_01.png"));
		//setSize(getImage().getIconWidth(), getImage().getIconHeight());

		itens[0] = new Item(Menu.Type.ENERGY);
	}

	@Override
	public void update() {
	}

	@Override
	public void drawMe(Graphics2D g) {
		if (!isVisible()) {
			return;
		}

		if (getImage() != null) {
			SpriteTool s = SpriteTool.s(getImage()).matriz(2, 1);

			s.invert(false).draw(g, Camera.c().fx(getPx()), Camera.c().fy(getPy()), 0, 0);
			//draw atendente
			
			s.invert(false).draw(g, Camera.c().fx(getPx()), Camera.c().fy(getPy()), 1, 0);
			

		} else {
			Camera.c().close(g, this);
		}

		if (openMenu) {
			g.setColor(Color.BLUE);
			drawSelectionMenu(g, itens, iSel);
		}
	}

	public static void drawSelectionMenu(Graphics2D g, Item[] itens, int iSel) {
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

	public void press(KeysMap k) {
		// int l = 0;
		if (openMenu) {
			switch (k) {
			case UP:
				// l++;
				break;
			case DOWN:
				// l--;
				break;
			case LEFT:
				iSel--;
				break;
			case RIGHT:
				iSel++;
				break;
			default:
				break;
			}
		} else {
			iSel = 0;
		}
		Player p = planeta.getPlayer();

		switch (k) {
		case B0:
			openMenu = !openMenu;
			p.setInMenu(!openMenu);
			break;
		case B1:
			if (openMenu) {
				if (getItem(iSel) != null) {
					p.addItem(getItem(iSel));
					removeItem(iSel);
				}
			}
			break;
		default:
			break;
		}

		p.setState(openMenu ? Player.State.IN_PLACE : Player.State.DEF);

		/*
		 * if (l < 0 || l == itens.length) { l = l < 0 ? itens.length - 1 : 0; }
		 */

		if (iSel < 0 || iSel == itens.length) {
			iSel = iSel < 0 ? itens.length - 1 : 0;
		}
	}

}
