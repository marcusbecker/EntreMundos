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

}
