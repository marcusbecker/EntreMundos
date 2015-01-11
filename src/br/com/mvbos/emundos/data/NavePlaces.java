package br.com.mvbos.emundos.data;

import br.com.mvbos.emundos.el.Menu;
import br.com.mvbos.emundos.el.Menu.Type;
import br.com.mvbos.emundos.el.Nave;

public class NavePlaces {

	public static final NavePlaces blank = new NavePlaces(true, 0, 0, null);

	private final boolean fixed;
	private final int px;
	private final int py;
	private final Menu.Type type;

	private Menu menu;

	public NavePlaces(boolean fixed, int px, int py, Type type) {
		this.fixed = fixed;
		this.px = px;
		this.py = py;
		this.type = type;
	}

	public Menu getMenu() {
		if (menu == null)
			System.out.println("erro");

		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
		this.menu.loadElement();
	}

	public boolean isFixed() {
		return fixed;
	}

	public int getPx() {
		return px;
	}

	public int getPy() {
		return py;
	}

	public Menu.Type getType() {
		return type;
	}

	public void update(Nave n) {

		if (!n.isInvert()) {
			menu.setPxy(n.getPx() + getPx(), n.getPy() + getPy());

		} else {
			menu.setPxy(n.getPx() + (n.getWidth() - getPx() - menu.getWidth() / 2), n.getPy() + getPy());
		}
	}

	public boolean haveActiveMenu() {
		return menu != null && menu.isActive();
	}
}
