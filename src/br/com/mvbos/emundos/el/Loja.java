package br.com.mvbos.emundos.el;

import java.awt.Graphics2D;

import br.com.mvbos.emundos.data.Item;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.window.Camera;

public class Loja extends ElementModel {

	private Item[] itens = new Item[6];

	@Override
	public void loadElement() {
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

		Camera.c().close(g, this);
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

}
