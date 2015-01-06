package br.com.mvbos.emundos.data;

import br.com.mvbos.emundos.el.Menu;
import br.com.mvbos.emundos.el.Menu.Type;

public class Item {

	private Menu.Type type;

	public Item(Type type) {
		this.type = type;
	}

	public Menu.Type getType() {
		return type;
	}

	public void setType(Menu.Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Item [type=" + type + "]";
	}

	public static int next(boolean left, int iSel, int length) {
		iSel += left ? -1 : +1;

		if (iSel < 0 || iSel == length) {
			iSel = iSel < 0 ? length - 1 : 0;
		}

		return iSel;
	}

}
